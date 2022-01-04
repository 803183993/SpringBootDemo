package com.ace.springBoot.persistence.dataObjects;

import com.ace.springBoot.domain.Phone;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDataObjectTest
{
    @Test
    public void shouldCreatePersonWithMandatoryValues()
    {
        PersonDataObject personDataObject = new PersonDataObject(902194884, "Mr", "Male", "Joe", "Bloggs", "200023336956");
        assertEquals(902194884, personDataObject.getPersonId());
        assertEquals("Mr", personDataObject.getTitle());
        assertEquals("Male", personDataObject.getGender());
        assertEquals("Joe", personDataObject.getFirstName());
        assertEquals("Bloggs", personDataObject.getLastName());
        assertEquals("200023336956", personDataObject.getUniquePropertyReferenceNumber());
    }

    @Test
    public void shouldCreatePersonWithAllValues()
    {
        int personId = 802194884;
        PersonDataObject personDataObject = new PersonDataObject(personId,
                                                                 "Mr",
                                                                 "Male",
                                                                 "Joe",
                                                                 "Bloggs",
                                                                 new AddressDataObject("100023336956",
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

        assertEquals(802194884, personDataObject.getPersonId());
        assertEquals("Mr", personDataObject.getTitle());
        assertEquals("Male", personDataObject.getGender());
        assertEquals("Joe", personDataObject.getFirstName());
        assertEquals("Bloggs", personDataObject.getLastName());
        assertEquals("100023336956", personDataObject.getUniquePropertyReferenceNumber());

        AddressDataObject addressDataObject = personDataObject.getAddress();
        assertEquals("100023336956", addressDataObject.getUniquePropertyReferenceNumber());
        assertEquals(10, addressDataObject.getPropertyNumber());
        assertEquals("Downing Street", addressDataObject.getMainThoroughfare());
        assertEquals("London", addressDataObject.getCity());
        assertEquals("SW1A 2AA", addressDataObject.getPostCode());

        Set<PhoneDataObject> phoneDataObjects = personDataObject.getPhones();
        assertEquals(3, phoneDataObjects.size());
        Iterator<PhoneDataObject> phoneDataObjectsIter = phoneDataObjects.iterator();
        assertPhone(phoneDataObjectsIter.next(), "Work", "01727 7777777");
        assertPhone(phoneDataObjectsIter.next(), "Home", "01727 8335634");
        assertPhone(phoneDataObjectsIter.next(), "Mobile", "07730012333");
    }

    private void assertPhone(PhoneDataObject phoneDataObject, String type, String number)
    {
        assertEquals(802194884, phoneDataObject.getPersonId());
        assertEquals(type, phoneDataObject.getType());
        assertEquals(number, phoneDataObject.getNumber());
    }
}