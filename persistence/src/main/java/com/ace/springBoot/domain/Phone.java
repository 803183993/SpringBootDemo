package com.ace.springBoot.domain;

import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;

public class Phone implements Comparable<Phone>
{
    private final int personId;
    private final Phone.Type type;
    private final String number;

    @SuppressWarnings("unused")
    Phone()
    {
        this(0, null, null);
    }

    public Phone(int personId, Phone.Type type, String number)
    {
        this.personId = personId;
        this.type = type;
        this.number = number;
    }

    public static Phone create(PhoneDataObject phoneDataObject)
    {
        return new Phone(phoneDataObject.getPersonId(), Type.valueOf(phoneDataObject.getType()), phoneDataObject.getNumber());
    }

    public int getPersonId()
    {
        return personId;
    }

    public Type getType()
    {
        return type;
    }

    public String getNumber()
    {
        return number;
    }

    @Override
    public int compareTo(Phone other)
    {
        return this.type.name().compareTo(other.type.name());
    }

    public enum Type
    {
        Home,
        Work,
        Mobile
    }
}
