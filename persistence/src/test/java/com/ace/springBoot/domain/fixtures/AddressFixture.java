package com.ace.springBoot.domain.fixtures;

import com.ace.springBoot.domain.Address;

public class AddressFixture
{
    public Address build()
    {
        return new Address("100023336956",
                           10,
                           "Downing Street",
                           "London",
                           "SW1A 2AA");
    }
}
