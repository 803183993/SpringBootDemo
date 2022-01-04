package com.ace.springBoot.persistence.dataObjects;

import com.ace.springBoot.domain.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneDataObjectTest
{
    private PhoneDataObject phoneDataObject1;
    private PhoneDataObject phoneDataObject2;
    private PhoneDataObject phoneDataObject3;

    @BeforeEach
    public void setup()
    {
        phoneDataObject1 = new PhoneDataObject(802194884, Phone.Type.Home.name(), "01727 8335634");
        phoneDataObject2 = new PhoneDataObject(802194884, Phone.Type.Work.name(), "01727 8335634");
        phoneDataObject3 = new PhoneDataObject(802194884, Phone.Type.Home.name(), "01727 8335634");
    }

    @Test
    public void shouldCreateAddressWithAllValues()
    {
        assertEquals(802194884, phoneDataObject1.getPersonId());
        assertEquals(Phone.Type.Home.name(), phoneDataObject1.getType());
        assertEquals("01727 8335634", phoneDataObject1.getNumber());
    }

    @Test
    public void shouldReturnEqualsAndHashCode()
    {
        assertEquals(phoneDataObject1, phoneDataObject3);
        assertNotEquals(phoneDataObject1, phoneDataObject2);
        assertNotEquals(phoneDataObject3, phoneDataObject2);
        assertNotEquals(null, phoneDataObject2);

        assertEquals(phoneDataObject1.hashCode(), phoneDataObject3.hashCode());
        assertNotEquals(phoneDataObject1.hashCode(), phoneDataObject2.hashCode());
        assertNotEquals(phoneDataObject3.hashCode(), phoneDataObject2.hashCode());

        assertEquals(-601045510, phoneDataObject1.hashCode());
        assertNotEquals(Phone.Type.Work.name().hashCode(), phoneDataObject2.hashCode());
        assertEquals(phoneDataObject1, phoneDataObject3);
        assertEquals(-587187704, phoneDataObject2.hashCode());
    }
}
