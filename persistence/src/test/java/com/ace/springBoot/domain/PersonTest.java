package com.ace.springBoot.domain;

import com.ace.springBoot.domain.fixtures.PersonFixture;
import com.ace.springBoot.persistence.dataObjects.AddressDataObject;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;
import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest
{
    private Person person1;
    private Person person2;
    private Person person3;

    @BeforeEach
    public void setup()
    {
        person1 = new PersonFixture().build();
        person2 = new PersonFixture().build();
        person3 = new PersonFixture().withPersonId(602194884).build();
    }

    @Test
    public void shouldReturnEqualsAndHashCode()
    {
        assertEquals(person1, person1);
        assertEquals(person1, person2);
        assertNotEquals(person1, person3);
        assertNotEquals(null, person1);

        assertEquals(person1.hashCode(), person1.hashCode());
        assertEquals(person1.hashCode(), person2.hashCode());
        assertNotEquals(person1.hashCode(), person3.hashCode());
    }

    @Test
    public void shouldCreatePersonWithAllValues()
    {
        assertEquals(802194884, person1.getPersonId());
        assertEquals("Mr", person1.getTitle());
        assertEquals("Male", person1.getGender());
        assertEquals("Joe", person1.getFirstName());
        assertEquals("Bloggs", person1.getLastName());

        Address address = person1.getAddress();
        assertEquals("100023336956", address.getUniquePropertyReferenceNumber());
        assertEquals(10, address.getPropertyNumber());
        assertEquals("Downing Street", address.getMainThoroughfare());
        assertEquals("London", address.getCity());
        assertEquals("SW1A 2AA", address.getPostCode());

        List<Phone> phones = sortPhones(person1.getPhones());
        assertEquals(3, phones.size());
        assertPhone(phones.get(0), 802194884, Phone.Type.Home, "01727 8335634");
        assertPhone(phones.get(1), 802194884, Phone.Type.Mobile, "07730012333");
        assertPhone(phones.get(2), 802194884, Phone.Type.Work, "01727 7777777");
    }

    @Test
    public void shouldCreatePersonWithPersonDataObject()
    {
        int personId = 702194884;
        Person person = Person.create(createPersonDataObject(personId));

        assertEquals(personId, person.getPersonId());
        assertEquals("Miss", person.getTitle());
        assertEquals("Female", person.getGender());
        assertEquals("Jane", person.getFirstName());
        assertEquals("Doe", person.getLastName());

        Address address = person.getAddress();
        assertEquals("300023336956", address.getUniquePropertyReferenceNumber());
        assertEquals(10, address.getPropertyNumber());
        assertEquals("Downing Street", address.getMainThoroughfare());
        assertEquals("London", address.getCity());
        assertEquals("SW1A 2AA", address.getPostCode());

        List<Phone> phones = sortPhones(person.getPhones());
        assertEquals(3, phones.size());
        assertPhone(phones.get(0), personId, Phone.Type.Home, "01727 8335634");
        assertPhone(phones.get(1), personId, Phone.Type.Mobile, "07730012333");
        assertPhone(phones.get(2), personId, Phone.Type.Work, "01727 7777777");
    }

    @Test
    public void shouldConvertToDataObject()
    {
        Person person1 = new PersonFixture().build();

        PersonDataObject personDataObject = person1.getPersonDataObject();
        assertEquals(802194884, personDataObject.getPersonId());
        assertEquals("Mr", personDataObject.getTitle());
        assertEquals("Male", personDataObject.getGender());
        assertEquals("Joe", personDataObject.getFirstName());
        assertEquals("Bloggs", personDataObject.getLastName());

        AddressDataObject addressDataObject = person1.getAddressDataObject();
        assertEquals("100023336956", addressDataObject.getUniquePropertyReferenceNumber());
        assertEquals(10, addressDataObject.getPropertyNumber());
        assertEquals("Downing Street", addressDataObject.getMainThoroughfare());
        assertEquals("London", addressDataObject.getCity());
        assertEquals("SW1A 2AA", addressDataObject.getPostCode());

        Set<PhoneDataObject> phoneDataObjects = person1.getPhoneDataObjects();
        assertEquals(3, phoneDataObjects.size());
        Iterator<PhoneDataObject> phonesIter = phoneDataObjects.iterator();
        assertPhone(phonesIter.next(), Phone.Type.Work.name(), "01727 7777777");
        assertPhone(phonesIter.next(), Phone.Type.Home.name(), "01727 8335634");
        assertPhone(phonesIter.next(), Phone.Type.Mobile.name(), "07730012333");
    }

    private void assertPhone(PhoneDataObject phoneDataObject, String type, String number)
    {
        assertEquals(802194884, phoneDataObject.getPersonId());
        assertEquals(type, phoneDataObject.getType());
        assertEquals(number, phoneDataObject.getNumber());
    }

    private void assertPhone(Phone phone, int personId, Phone.Type type, String number)
    {
        assertEquals(personId, phone.getPersonId());
        assertEquals(type, phone.getType());
        assertEquals(number, phone.getNumber());
    }

    private PersonDataObject createPersonDataObject(int personId)
    {
        return new PersonDataObject(personId,
                                    "Miss",
                                    "Female",
                                    "Jane",
                                    "Doe",
                                    new AddressDataObject("300023336956",
                                                          10,
                                                          "Downing Street",
                                                          "London",
                                                          "SW1A 2AA"),
                                    new HashSet<>()
                                    {
                                        {
                                            add(new PhoneDataObject(personId, Phone.Type.Home.name(), "01727 8335634"));
                                            add(new PhoneDataObject(personId, Phone.Type.Work.name(), "01727 7777777"));
                                            add(new PhoneDataObject(personId, Phone.Type.Mobile.name(), "07730012333"));
                                        }
                                    });
    }

    private List<Phone> sortPhones(Set<Phone> phones)
    {
        List<Phone> sortedPhones = new ArrayList<>(phones);
        Collections.sort(sortedPhones);
        return sortedPhones;
    }
}