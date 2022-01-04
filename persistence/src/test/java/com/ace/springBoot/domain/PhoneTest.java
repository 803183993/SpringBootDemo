package com.ace.springBoot.domain;

import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTest
{
    @Test
    public void shouldCreatePhoneFromPhoneDataObject()
    {
        Phone phone = Phone.create(new PhoneDataObject(802194884, "Home", "01727 8335634"));
        assertEquals(802194884, phone.getPersonId());
        assertEquals("Home", phone.getType().name());
        assertEquals("01727 8335634", phone.getNumber());
    }

    @Test
    public void shouldCreatePhoneWithAllValues()
    {
        Phone phone = new Phone(902194884, Phone.Type.Work, "01727 9335634");
        assertEquals(902194884, phone.getPersonId());
        assertEquals(Phone.Type.Work, phone.getType());
        assertEquals("01727 9335634", phone.getNumber());
    }
}