package com.ace.springBoot.restControllers.controllers;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class PersonRestController
{
    @Autowired
    @SuppressWarnings("unused")
    private PersonService personService;

    @GetMapping("/getPerson/{personId}")
    @SuppressWarnings("unused")
    public ResponseEntity<Person> getPerson(@PathVariable int personId)
    {
        Person person = personService.getPerson(personId);
        return new ResponseEntity<>(person, (person == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping("/addPerson")
    @SuppressWarnings("unused")
    public ResponseEntity addPerson(@RequestBody Person person)
    {
        if (person.getAddress() == null)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        try
        {
            personService.addPerson(person);
        }
        catch (DataIntegrityViolationException dive)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok().build();
    }
}
