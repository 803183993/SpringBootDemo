package com.ace.springBoot.services;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.Phone;
import com.ace.springBoot.domain.fixtures.PersonFixture;
import com.ace.springBoot.persistence.PersonRepository;
import com.ace.springBoot.persistence.dataObjects.AddressDataObject;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;
import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = PersonServiceImpl.class)
public class PersonServiceImplTest
{
    @MockBean
    @SuppressWarnings({"UnusedDeclaration"})
    private PersonRepository personRepository;
    @MockBean
    @SuppressWarnings({"UnusedDeclaration"})
    private TransactionTemplate transactionTemplate;
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private PersonService personService;
    @SuppressWarnings({"UnusedDeclaration"})
    private TransactionStatus transactionStatus;
    private PersonDataObject personDataObject;
    private Person person;
    private int personId;

    @BeforeEach
    public void setup()
    {
        personId = 802194884;
        personDataObject = new PersonDataObject(personId,
                                                "Mr",
                                                "Male",
                                                "Joe",
                                                "Bloggs",
                                                new AddressDataObject("100023336956",
                                                                      10,
                                                                      "Downing Street",
                                                                      "London",
                                                                      "SW1A 2AA"),
                                                Collections.singleton(new PhoneDataObject(personId, Phone.Type.Home.name(), "01727 8335634")));
        person = Person.create(personDataObject);
    }

    @Test
    public void shouldReturnSpecifiedPerson()
    {
        when(personRepository.findPersonById(eq(personId))).thenReturn(personDataObject);

        Person person = personService.getPerson(personId);
        assertNotNull(person);
    }

    @Test
    public void shouldReturnNullIfSpecifiedPersonIsNotFound()
    {
        when(personRepository.findPersonById(eq(personId))).thenReturn(null);

        Person person = personService.getPerson(personId);
        assertNull(person);
    }

    @Test
    public void shouldAddPerson()
    {
        doNothing().when(personRepository).addPerson(argThat(PersonDataObject -> {
            assertNotNull(PersonDataObject);
            assertEquals(personId, PersonDataObject.getPersonId());
            return true;
        }));

        personService.addPerson(new PersonFixture().build());
    }

    @Test
    public void shouldReturnDataIntegrityViolationExceptionIfDuplicateKeyFound()
    {
        doThrow(new DataIntegrityViolationException("DuplicateKey")).when(personRepository).addPerson(argThat(personDataObject -> {
            assertNotNull(personDataObject);
            assertEquals(personId, personDataObject.getPersonId());
            return true;
        }));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> personService.addPerson(person));
        assertEquals("DuplicateKey", exception.getMessage());
    }

    @Test
    public void shouldReturnExceptionIfUnexpectedErrorEncountered()
    {
        doThrow(new IllegalStateException("UnexpectedError")).when(personRepository).addPerson(argThat(personDataObject -> {
            assertNotNull(personDataObject);
            assertEquals(personId, personDataObject.getPersonId());
            return true;
        }));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> personService.addPerson(person));
        assertEquals("UnexpectedError", exception.getMessage());
    }
}