package com.ace.springBoot.persistence;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.fixtures.PersonFixture;
import com.ace.springBoot.persistence.dataObjects.AddressDataObject;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;
import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = OraclePersonRepository.class)
@AutoConfigurationPackage(basePackages = "com.ace.springBoot.persistence")
@TestPropertySource(locations = "/com/ace/springBoot/persistence/spring-h2-application-context-test.properties")
@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OraclePersonRepositoryTest
{
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private TransactionTemplate transactionTemplate;
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private PersonRepository personRepository;
    private Person expectedPerson1;
    private Person expectedPerson2;

    @BeforeEach
    public void setUp()
    {
        expectedPerson1 = new PersonFixture().build();
        expectedPerson2 = new PersonFixture().withPersonId(902194884).build();

        transactionTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);

        transactionTemplate.execute(new TransactionCallbackWithoutResult()
        {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus)
            {
                personRepository.addPerson(expectedPerson2);
                personRepository.addPerson(expectedPerson1);
            }
        });
    }

    @Test
    @Transactional
    public void canAddAndRetrievePerson()
    {
        PersonDataObject actualPersonDataObject = transactionTemplate.execute(transactionStatus -> personRepository.findPersonById(expectedPerson1.getPersonId()));
        assertNotNull(actualPersonDataObject);
        assertEquals(expectedPerson1.getPersonId(), actualPersonDataObject.getPersonId());
        assertEquals(expectedPerson1.getTitle(), actualPersonDataObject.getTitle());
        assertEquals(expectedPerson1.getGender(), actualPersonDataObject.getGender());
        assertEquals(expectedPerson1.getFirstName(), actualPersonDataObject.getFirstName());
        assertEquals(expectedPerson1.getLastName(), actualPersonDataObject.getLastName());
        assertEquals(expectedPerson1.getUniquePropertyReferenceNumber(), actualPersonDataObject.getUniquePropertyReferenceNumber());

        AddressDataObject expectedAddressDataObject = expectedPerson1.getAddressDataObject();
        AddressDataObject actualAddressDataObject = actualPersonDataObject.getAddress();
        assertEquals(expectedAddressDataObject.getUniquePropertyReferenceNumber(), actualAddressDataObject.getUniquePropertyReferenceNumber());
        assertEquals(expectedAddressDataObject.getMainThoroughfare(), actualAddressDataObject.getMainThoroughfare());
        assertEquals(expectedAddressDataObject.getPropertyNumber(), actualAddressDataObject.getPropertyNumber());
        assertEquals(expectedAddressDataObject.getCity(), actualAddressDataObject.getCity());
        assertEquals(expectedAddressDataObject.getPostCode(), actualAddressDataObject.getPostCode());

        Set<PhoneDataObject> phoneDataObjects = actualPersonDataObject.getPhones();
        assertEquals(3, phoneDataObjects.size());
        List<PhoneDataObject> sortedPhoneDataObjects = new ArrayList<>(phoneDataObjects);
        sortedPhoneDataObjects.sort(Comparator.comparing(PhoneDataObject::getType));

        assertPhone(sortedPhoneDataObjects.get(0), "Home", "01727 8335634");
        assertPhone(sortedPhoneDataObjects.get(1), "Mobile", "07730012333");
        assertPhone(sortedPhoneDataObjects.get(2), "Work", "01727 7777777");
    }

    @Test
    public void shouldReturnAllPersons()
    {
        List<PersonDataObject> personDataObjects = transactionTemplate.execute(transactionStatus -> personRepository.getAllPersons());
        assertNotNull(personDataObjects);
        assertEquals(2, personDataObjects.size());

        assertEquals(expectedPerson1.getPersonId(), personDataObjects.get(0).getPersonId());
        assertEquals(expectedPerson2.getPersonId(), personDataObjects.get(1).getPersonId());
    }

    private void assertPhone(PhoneDataObject phoneDataObject, String type, String number)
    {
        assertEquals(802194884, phoneDataObject.getPersonId());
        assertEquals(type, phoneDataObject.getType());
        assertEquals(number, phoneDataObject.getNumber());
    }
}