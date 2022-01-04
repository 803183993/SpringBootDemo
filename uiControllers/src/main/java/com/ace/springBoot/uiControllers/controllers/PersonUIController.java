package com.ace.springBoot.uiControllers.controllers;

import com.ace.springBoot.domain.Address;
import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.Phone;
import com.ace.springBoot.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.*;

@Controller
@RequestMapping("/ui")
public class PersonUIController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonUIController.class);
    @Autowired
    @SuppressWarnings("unused")
    private PersonService personService;

    @GetMapping("/getPerson/")
    @SuppressWarnings("UnusedDeclaration")
    public String getPersonById(@RequestParam(name = "personId") int personId, Model model)
    {
        Person person = personService.getPerson(personId);
        if (person != null)
        {
            LOGGER.info(format("Found Person with Id [%s]", person.getPersonId()));

            mapPerson(model, person);
            return "person";
        }
        else
        {
            LOGGER.info(format("Not found Person with Id [%s]", personId));

            model.addAttribute("errorText", format("Person [%s] not found!", personId));
            return "error";
        }
    }

    private void mapPerson(Model model, Person person)
    {
        model.addAttribute("id", person.getPersonId());
        model.addAttribute("title", person.getTitle());
        model.addAttribute("gender", person.getGender());
        model.addAttribute("firstName", person.getFirstName());
        model.addAttribute("lastName", person.getLastName());

        Address address = person.getAddress();
        model.addAttribute("uprn", address.getUniquePropertyReferenceNumber());
        model.addAttribute("propertyNumber", address.getPropertyNumber());
        model.addAttribute("thoroughfare", address.getMainThoroughfare());
        model.addAttribute("city", address.getCity());
        model.addAttribute("postcode", address.getPostCode());

        List<Phone> sortedPhones = new ArrayList<>(person.getPhones());
        Collections.sort(sortedPhones);
        model.addAttribute("homeTel", sortedPhones.get(0).getNumber());
        model.addAttribute("mobileTel", sortedPhones.get(1).getNumber());
        model.addAttribute("workTel", sortedPhones.get(2).getNumber());
    }
}
