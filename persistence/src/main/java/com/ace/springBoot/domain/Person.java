package com.ace.springBoot.domain;

import com.ace.springBoot.persistence.dataObjects.AddressDataObject;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;
import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;

import java.util.HashSet;
import java.util.Set;

public class Person
{
    private final int personId;
    private final String title;
    private final String gender;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final Set<Phone> phones;

    @SuppressWarnings("unused")
    Person()
    {
        this(0, null, null, null, null, null, null);
    }

    public Person(int personId,
                  String title,
                  String gender,
                  String firstName,
                  String lastName,
                  Address address,
                  Set<Phone> phones)
    {
        this.personId = personId;
        this.title = title;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phones = phones;
    }

    public static Person create(PersonDataObject personDataObject)
    {
        return new Person(personDataObject.getPersonId(),
                          personDataObject.getTitle(),
                          personDataObject.getGender(),
                          personDataObject.getFirstName(),
                          personDataObject.getLastName(),
                          Address.create(personDataObject.getAddress()),
                          convertToPhones(personDataObject.getPhones()));
    }

    private static Set<Phone> convertToPhones(Set<PhoneDataObject> phonesDataObjects)
    {
        Set<Phone> phones = new HashSet<>();
        for (PhoneDataObject phoneDataObject : phonesDataObjects)
        {
            phones.add(Phone.create(phoneDataObject));
        }
        return phones;
    }

    public int getPersonId()
    {
        return personId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getGender()
    {
        return gender;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Address getAddress()
    {
        return address;
    }

    public Set<Phone> getPhones()
    {
        return phones;
    }

    public PersonDataObject getPersonDataObject()
    {
        return new PersonDataObject(personId, title, gender, firstName, lastName, address.getUniquePropertyReferenceNumber());
    }

    public AddressDataObject getAddressDataObject()
    {
        return new AddressDataObject(address.getUniquePropertyReferenceNumber(), address.getPropertyNumber(), address.getMainThoroughfare(), address.getCity(), address.getPostCode());
    }

    public Set<PhoneDataObject> getPhoneDataObjects()
    {
        Set<PhoneDataObject> phoneDataObjects = new HashSet<>();
        for (Phone phone : phones)
        {
            phoneDataObjects.add(new PhoneDataObject(phone.getPersonId(), phone.getType().name(), phone.getNumber()));
        }
        return phoneDataObjects;
    }

    public String getUniquePropertyReferenceNumber()
    {
        return address.getUniquePropertyReferenceNumber();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Person that = (Person) o;
        return this.personId == that.personId;
    }

    @Override
    public int hashCode()
    {
        return 31 * personId;
    }
}
