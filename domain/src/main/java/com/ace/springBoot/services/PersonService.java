package com.ace.springBoot.services;

import com.ace.springBoot.domain.Person;
import org.springframework.dao.DataIntegrityViolationException;

public interface PersonService
{
    Person getPerson(int personId);

    void addPerson(Person person) throws DataIntegrityViolationException;
}
