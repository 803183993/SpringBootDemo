package com.ace.springBoot.persistence.dataObjects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Addresses")
public class AddressDataObject
{
    @Id
    @Column(name = "uprn", nullable = false)
    private String uniquePropertyReferenceNumber;
    @Column(name = "property_number")
    private int propertyNumber;
    @Column(name = "main_thoroughfare")
    private String mainThoroughfare;
    @Column(name = "city")
    private String city;
    @Column(name = "post_code")
    private String postCode;
    @OneToMany(mappedBy = "addressDataObject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @SuppressWarnings("unused")
    private Set<PersonDataObject> personDataObject;

    @SuppressWarnings("unused")
    public AddressDataObject()
    {

    }

    public AddressDataObject(String uniquePropertyReferenceNumber, int propertyNumber, String mainThoroughfare, String city, String postCode)
    {
        this.uniquePropertyReferenceNumber = uniquePropertyReferenceNumber;
        this.propertyNumber = propertyNumber;
        this.mainThoroughfare = mainThoroughfare;
        this.city = city;
        this.postCode = postCode;
    }

    public String getUniquePropertyReferenceNumber()
    {
        return uniquePropertyReferenceNumber;
    }

    public int getPropertyNumber()
    {
        return propertyNumber;
    }

    public String getMainThoroughfare()
    {
        return mainThoroughfare;
    }

    public String getCity()
    {
        return city;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        AddressDataObject that = (AddressDataObject) o;
        return this.uniquePropertyReferenceNumber.equals(that.uniquePropertyReferenceNumber);
    }

    @Override
    public int hashCode()
    {
        return uniquePropertyReferenceNumber.hashCode();
    }
}
