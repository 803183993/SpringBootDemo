package com.ace.springBoot.uiControllers.controllers;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.fixtures.PersonFixture;
import com.ace.springBoot.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PersonUIControllerTest
{
    private MockMvc mockMvc;
    @Mock
    private PersonService mockPersonService;
    @InjectMocks
    private PersonUIController mockPersonUIController;
    private Person person;

    @BeforeEach
    public void setup()
    {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(mockPersonUIController).build();

        person = new PersonFixture().build();
    }

    @Test
    public void shouldReturnSpecifiedPerson() throws Exception
    {
        when(mockPersonService.getPerson(person.getPersonId())).thenReturn(person);

        this.mockMvc.perform(get(format("/ui/getPerson/?personId=%s", person.getPersonId())))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attribute("id", person.getPersonId()))
            .andExpect(model().attribute("firstName", person.getFirstName()))
            .andExpect(model().attribute("lastName", person.getLastName()));
    }

    @Test
    public void shouldReturnErrorPageIfPersonNotFound() throws Exception
    {
        when(mockPersonService.getPerson(person.getPersonId())).thenReturn(null);

        this.mockMvc.perform(get(format("/ui/getPerson/?personId=%s", person.getPersonId())))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attribute("errorText", (format("Person [%s] not found!", person.getPersonId()))));
    }
}