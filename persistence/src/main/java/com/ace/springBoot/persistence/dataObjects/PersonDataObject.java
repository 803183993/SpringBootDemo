package com.ace.springBoot.persistence.dataObjects;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Persons")
@NamedQuery(name = "getAllPersons", query = "select persons from PersonDataObject persons")
public class PersonDataObject
{
    @Id
    @Column(name = "person_id")
    private int personId;
    @Column(name = "title")
    private String title;
    @Column(name = "gender")
    private String gender;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "uprn", nullable = false)
    private String uniquePropertyReferenceNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uprn", referencedColumnName = "uprn", insertable = false, updatable = false, nullable = false)
    @SuppressWarnings({"UnusedDeclaration"})
    private AddressDataObject addressDataObject;
    @OneToMany(mappedBy = "personDataObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @SuppressWarnings({"UnusedDeclaration"})
    private Set<PhoneDataObject> phoneDataObjects;

    @SuppressWarnings("unused")
    public PersonDataObject()
    {

    }

    public PersonDataObject(int personId,
                            String title,
                            String gender,
                            String firstName,
                            String lastName,
                            String uniquePropertyReferenceNumber)
    {
        this(personId, title, gender, firstName, lastName, uniquePropertyReferenceNumber, null, null);
    }

    public PersonDataObject(int personId,
                            String title,
                            String gender,
                            String firstName,
                            String lastName,
                            AddressDataObject addressDataObject,
                            Set<PhoneDataObject> phoneDataObjects)
    {
        this(personId, title, gender, firstName, lastName, addressDataObject.getUniquePropertyReferenceNumber(), addressDataObject, phoneDataObjects);
    }

    private PersonDataObject(int personId,
                             String title,
                             String gender,
                             String firstName,
                             String lastName,
                             String uniquePropertyReferenceNumber,
                             AddressDataObject addressDataObject,
                             Set<PhoneDataObject> phoneDataObjects)
    {
        this.personId = personId;
        this.title = title;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniquePropertyReferenceNumber = uniquePropertyReferenceNumber;
        this.addressDataObject = addressDataObject;
        this.phoneDataObjects = phoneDataObjects;
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

    public String getUniquePropertyReferenceNumber()
    {
        return uniquePropertyReferenceNumber;
    }

    @Transactional
    public AddressDataObject getAddress()
    {
        return addressDataObject;
    }

    @Transactional
    public Set<PhoneDataObject> getPhones()
    {
        return phoneDataObjects;
    }
}
