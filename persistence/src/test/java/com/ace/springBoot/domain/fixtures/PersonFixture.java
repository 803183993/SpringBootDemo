package com.ace.springBoot.domain.fixtures;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.Phone;

import java.util.HashSet;

public class PersonFixture
{
    private int personId;

    public PersonFixture withPersonId(int personId)
    {
        this.personId = personId;
        return this;
    }

    public Person build()
    {
        int specifiedPersonId = personId > 0 ? personId : 802194884;
        return new Person(specifiedPersonId,
                          "Mr",
                          "Male",
                          "Joe",
                          "Bloggs",
                          new AddressFixture().build(),
                          new HashSet<>()
                          {
                              {
                                  add(new Phone(specifiedPersonId, Phone.Type.Home, "01727 8335634"));
                                  add(new Phone(specifiedPersonId, Phone.Type.Work, "01727 7777777"));
                                  add(new Phone(specifiedPersonId, Phone.Type.Mobile, "07730012333"));
                              }
                          });
    }
}
