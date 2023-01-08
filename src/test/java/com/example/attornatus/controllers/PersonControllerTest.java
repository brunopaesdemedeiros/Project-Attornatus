package com.example.attornatus.controllers;

import com.example.attornatus.dtos.PersonDto;
import com.example.attornatus.model.Person;
import com.example.attornatus.services.PersonService;
import com.example.attornatus.util.PersonCreator;
import com.example.attornatus.util.PersonDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @InjectMocks
    private PersonController personController;
    @Mock
    private PersonService personServiceMock;

    @BeforeEach
    void setUp(){
        List<Person> persons = new ArrayList<>(List.of(PersonCreator.createValidPerson()));

        BDDMockito.when(personServiceMock.listAll()).thenReturn(persons);

        BDDMockito.when(personServiceMock.listAll())
                .thenReturn(List.of(PersonCreator.createValidPerson()));

        BDDMockito.when(personServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of((PersonCreator.createValidPerson())));

        BDDMockito.when(personServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PersonCreator.createValidPerson()));

        BDDMockito.when(personServiceMock.saveOrUpdate(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreator.createValidPerson());

        BDDMockito.doNothing().when(personServiceMock).removeById(ArgumentMatchers.any(Person.class));
    }

    @Test
    @DisplayName("ListAll returns list of person when successful")
    void listAll_ReturnsListOfPerson_WhenSuccessful(){
        String expectedName = PersonDtoCreator.createToBeSavedPerson().getName();
        List<Person> persons = personController.findAllPerson().getBody();

        Assertions.assertThat(persons)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(persons.get(0).getName()).isEqualTo(expectedName);
     }

    @Test
    @DisplayName("findById returns list of person when successful")
    void findById_ReturnsPerson_WhenSuccessful(){
        Long expectedId = PersonDtoCreator.createToBeSavedPerson().getId();

        PersonDto personToBeSaved = PersonDtoCreator.createToBeSavedPerson();

        ResponseEntity<Object> entity= personController.findPersonById(personToBeSaved.getId());

        Assertions.assertThat(entity).isNotNull()
                .isEqualTo(expectedId);

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("FindByName returns list of person when successful")
    void findByName_ReturnsPerson_WhenSuccessful() {
        String expectedName = PersonDtoCreator.createToBeSavedPerson().getName();

        PersonDto person = PersonDtoCreator.createToBeSavedPerson();

        Assertions.assertThat(person)
                .isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns person when successful")
    void save_ReturnsPerson_WhenSuccessful(){
        PersonDto personToBeSaved = PersonDtoCreator.createToBeSavedPerson();

        ResponseEntity<Object> entity= personController.saveOrUpdate(personToBeSaved);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("delete person when successful")
    void delete_RemovePerson_WhenSuccessful() {

        Assertions.assertThatCode(() -> personController.removePerson(1l)).doesNotThrowAnyException();

        ResponseEntity<Object> entity = personController.removePerson(1l);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update returns person when successful")
    void update_Person_WhenSuccessful() {

        PersonDto personToBeSaved = PersonDtoCreator.createToBeSavedPerson();
        personToBeSaved.setName("paulo");

        ResponseEntity<Object> entity= personController.saveOrUpdate(personToBeSaved);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}