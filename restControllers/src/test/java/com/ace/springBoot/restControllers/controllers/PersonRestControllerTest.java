package com.ace.springBoot.restControllers.controllers;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.fixtures.PersonFixture;
import com.ace.springBoot.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static java.lang.String.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonRestControllerTest
{
    private MockMvc mockMvc;
    @Mock
    private PersonService mockPersonService;
    @InjectMocks
    private PersonRestController mockPersonRestController;
    private Person person;

    @BeforeEach
    public void setup()
    {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(mockPersonRestController).build();

        person = new PersonFixture().build();
    }

    @Test
    public void shouldAddPerson() throws Exception
    {
        mockPersonService.addPerson(person);

        mockMvc
            .perform(MockMvcRequestBuilders.post("/rest/addPerson")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(new Gson().toJson(person)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldThrowInternalServerErrorIfAddressIsNull() throws Exception
    {
        Person personWithoutAddress = new Person(1,
                                                 "Mr",
                                                 "Male",
                                                 "Joe",
                                                 "Bloggs",
                                                 null,
                                                 Collections.emptySet());
        mockPersonService.addPerson(personWithoutAddress);
        mockMvc
            .perform(MockMvcRequestBuilders.post("/rest/addPerson")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(new Gson().toJson(personWithoutAddress)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    public void shouldReturnAddPerson() throws Exception
    {
        doThrow(new DataIntegrityViolationException("")).when(mockPersonService).addPerson(person);

        mockMvc
            .perform(MockMvcRequestBuilders.post("/rest/addPerson")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(new Gson().toJson(person)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    public void shouldReturnPersonByPersonId() throws Exception
    {
        when(mockPersonService.getPerson(person.getPersonId())).thenReturn(person);

        mockMvc
            .perform(MockMvcRequestBuilders.get(format("/rest/getPerson/%s", person.getPersonId())))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.personId").value(person.getPersonId()));
    }

    @Test
    public void shouldReturnNullIfSpecifiedPersonIsNotFound() throws Exception
    {
        when(mockPersonService.getPerson(person.getPersonId())).thenReturn(null);

        mockMvc
            .perform(MockMvcRequestBuilders.get(format("/rest/getPerson/%s", person.getPersonId())))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.personId").doesNotHaveJsonPath());
    }
}