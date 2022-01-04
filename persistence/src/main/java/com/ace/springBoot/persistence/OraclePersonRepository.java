package com.ace.springBoot.persistence;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.persistence.dataObjects.PersonDataObject;
import com.ace.springBoot.persistence.dataObjects.PhoneDataObject;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OraclePersonRepository implements PersonRepository
{
    @Autowired
    @SuppressWarnings("unused")
    private EntityManager entityManager;

    @Override
    public PersonDataObject findPersonById(int personById)
    {
        PersonDataObject personDataObject = entityManager.find(PersonDataObject.class, personById);
        if (personDataObject != null)
        {
            Hibernate.initialize(personDataObject.getAddress());
            Hibernate.initialize(personDataObject.getPhones());
        }

        return personDataObject;
    }

    @Override
    @Transactional
    public void addPerson(Person person)
    {
        entityManager.merge(person.getAddressDataObject());
        entityManager.persist(person.getPersonDataObject());

        Set<PhoneDataObject> phoneDataObjects = person.getPhoneDataObjects();
        for (PhoneDataObject phoneDataObject : phoneDataObjects)
        {
            entityManager.persist(phoneDataObject);
        }
    }

    @Override
    @Transactional
    public List<PersonDataObject> getAllPersons()
    {
        TypedQuery<PersonDataObject> persons = entityManager.createNamedQuery("getAllPersons", PersonDataObject.class);
        return persons.getResultList();
    }
}
