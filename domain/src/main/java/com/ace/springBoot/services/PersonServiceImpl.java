package com.ace.springBoot.services;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.persistence.PersonRepository;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class PersonServiceImpl implements PersonService
{
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private PersonRepository personRepository;
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private TransactionTemplate transactionTemplate;

    @Override
    public Person getPerson(int personId)
    {
        PersonDataObject personDataObject = personRepository.findPersonById(personId);
        return personDataObject != null ? Person.create(personDataObject) : null;
    }

    @Override
    public void addPerson(Person person) throws DataIntegrityViolationException
    {
        personRepository.addPerson(person);
    }
}
