package com.ace.springBoot.persistence.dataObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Phones")
public class PhoneDataObject
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused")
    private long id;
    @Column(name = "person_id")
    private int personId;
    @Column(name = "type")
    private String type;
    @Column(name = "phone_number")
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @SuppressWarnings("unused")
    private PersonDataObject personDataObject;

    @SuppressWarnings("unused")
    public PhoneDataObject()
    {

    }

    public PhoneDataObject(int personId, String type, String number)
    {
        this.personId = personId;
        this.type = type;
        this.number = number;
    }

    public int getPersonId()
    {
        return personId;
    }

    public String getType()
    {
        return type;
    }

    public String getNumber()
    {
        return number;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        PhoneDataObject that = (PhoneDataObject) o;
        return this.type.equals(that.type) && this.personId == that.personId && this.number.equals(that.number);
    }

    @Override
    public int hashCode()
    {
        return 31 * type.hashCode() + personId + number.hashCode();
    }
}
