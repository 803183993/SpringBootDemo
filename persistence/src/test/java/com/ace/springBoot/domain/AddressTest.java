package com.ace.springBoot.domain;

import com.ace.springBoot.domain.fixtures.AddressFixture;
import com.ace.springBoot.persistence.dataObjects.AddressDataObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest
{
    private AddressDataObject addressDataObject;

    @BeforeEach
    public void setup()
    {
        addressDataObject = new AddressDataObject("300023336956",
                                                  11,
                                                  "Downing Street",
                                                  "London",
                                                  "SW1A 2AA");
    }

    @Test
    public void shouldCreateAddressFromAddressDataObject()
    {
        Address address = Address.create(addressDataObject);

        assertEquals("300023336956", address.getUniquePropertyReferenceNumber());
        assertEquals(11, address.getPropertyNumber());
        assertEquals("Downing Street", address.getMainThoroughfare());
        assertEquals("London", address.getCity());
        assertEquals("SW1A 2AA", address.getPostCode());
    }

    @Test
    public void shouldCreateAddressWithAllValues()
    {
        Address address = new AddressFixture().build();
        assertEquals("100023336956", address.getUniquePropertyReferenceNumber());
        assertEquals(10, address.getPropertyNumber());
        assertEquals("Downing Street", address.getMainThoroughfare());
        assertEquals("London", address.getCity());
        assertEquals("SW1A 2AA", address.getPostCode());
    }
}