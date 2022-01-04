package com.ace.springBoot.persistence;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;

import java.util.List;

public interface PersonRepository
{
    PersonDataObject findPersonById(int personById);

    void addPerson(Person Person);

    List<PersonDataObject> getAllPersons();
}
