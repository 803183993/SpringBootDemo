package com.ace.springBoot.persistence.dataObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressDataObjectTest
{
    private AddressDataObject addressDataObject1;
    private AddressDataObject addressDataObject2;
    private AddressDataObject addressDataObject3;

    @BeforeEach
    public void setup()
    {
        addressDataObject1 = new AddressDataObject("100023336956",
                                                   10,
                                                   "Downing Street",
                                                   "London",
                                                   "SW1A 2AA");

        addressDataObject2 = new AddressDataObject("100023336956",
                                                   10,
                                                   "Downing Street",
                                                   "London",
                                                   "SW1A 2AA");

        addressDataObject3 = new AddressDataObject("300023336956",
                                                   13,
                                                   "Downing Street",
                                                   "London",
                                                   "SW1A 2AA");
    }

    @Test
    public void shouldCreateAddressWithAllValues()
    {
        assertEquals("100023336956", addressDataObject1.getUniquePropertyReferenceNumber());
        assertEquals(10, addressDataObject1.getPropertyNumber());
        assertEquals("Downing Street", addressDataObject1.getMainThoroughfare());
        assertEquals("London", addressDataObject1.getCity());
        assertEquals("SW1A 2AA", addressDataObject1.getPostCode());
    }

    @Test
    public void shouldReturnEqualsAndHashCode()
    {
        assertEquals(addressDataObject1, addressDataObject2);
        assertNotEquals(addressDataObject1, addressDataObject3);
        assertNotEquals(addressDataObject2, addressDataObject3);

        assertEquals(addressDataObject1.hashCode(), addressDataObject2.hashCode());
        assertNotEquals(addressDataObject1.hashCode(), addressDataObject3.hashCode());
        assertNotEquals(addressDataObject2.hashCode(), addressDataObject3.hashCode());
    }
}