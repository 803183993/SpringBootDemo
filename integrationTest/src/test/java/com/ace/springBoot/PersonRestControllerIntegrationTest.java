package com.ace.springBoot;

import com.ace.springBoot.domain.Person;
import com.ace.springBoot.domain.fixtures.PersonFixture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.jpa.hibernate.ddl-auto=create"})
public class PersonRestControllerIntegrationTest
{
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private TestRestTemplate restTemplate;
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private ObjectMapper mapper;
    private Person person1;
    private Person person2;

    @BeforeEach
    public void setup()
    {
        person1 = new PersonFixture().build();
        person2 = new PersonFixture().withPersonId(902194884).build();
    }

    @Test
    public void shouldAddAndReturnPerson() throws JsonProcessingException
    {
        addPerson(person1);
        addPerson(person2);

        Person actualPerson1 = getPerson(person1.getPersonId());
        assertThat(actualPerson1.getPersonId()).isEqualTo(person1.getPersonId());

        Person actualPerson2 = getPerson(person2.getPersonId());
        assertThat(actualPerson2.getPersonId()).isEqualTo(person2.getPersonId());
    }

    private void addPerson(Person person) throws JsonProcessingException
    {
        HttpEntity<String> entity = getStringHttpEntity(person);
        ResponseEntity<String> response = restTemplate.postForEntity("/rest/addPerson", entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private Person getPerson(int personId)
    {
        ResponseEntity<Person> response = restTemplate.getForEntity("/rest/getPerson/{personId}", Person.class, personId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private HttpEntity<String> getStringHttpEntity(Object object) throws JsonProcessingException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonMeterData = mapper.writeValueAsString(object);
        // noinspection unchecked, raw use
        return (HttpEntity<String>) new HttpEntity(jsonMeterData, headers);
    }
}
