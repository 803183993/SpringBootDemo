package com.ace.springBoot.domain;

import com.ace.springBoot.persistence.dataObjects.AddressDataObject;

public class Address
{
    private final String uniquePropertyReferenceNumber;
    private final int propertyNumber;
    private final String mainThoroughfare;
    private final String city;
    private final String postCode;

    @SuppressWarnings("unused")
    Address()
    {
        this(null, 0, null, null, null);
    }

    public Address(String uniquePropertyReferenceNumber, int propertyNumber, String mainThoroughfare, String city, String postCode)
    {
        this.uniquePropertyReferenceNumber = uniquePropertyReferenceNumber;
        this.propertyNumber = propertyNumber;
        this.mainThoroughfare = mainThoroughfare;
        this.city = city;
        this.postCode = postCode;
    }

    public static Address create(AddressDataObject addressDataObject)
    {
        return new Address(addressDataObject.getUniquePropertyReferenceNumber(), addressDataObject.getPropertyNumber(), addressDataObject.getMainThoroughfare(), addressDataObject.getCity(), addressDataObject.getPostCode());
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
}
