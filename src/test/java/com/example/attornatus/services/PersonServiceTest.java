package com.example.attornatus.services;

import com.example.attornatus.model.Person;
import com.example.attornatus.repository.PersonRepository;
import com.example.attornatus.util.PersonCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepositoryMock;

    @BeforeEach
    void setUp(){
        List<Person> persons = new ArrayList<>(List.of(PersonCreator.createValidPerson()));

        BDDMockito.when(personRepositoryMock.findAll()).thenReturn(persons);

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(PersonCreator.createValidPerson()));

        BDDMockito.when(personRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PersonCreator.createValidPerson()));

        BDDMockito.when(personRepositoryMock.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreator.createValidPerson());

        BDDMockito.doNothing().when(personRepositoryMock).delete(ArgumentMatchers.any(Person.class));

    }

    @Test
    @DisplayName("ListAll returns list of person when successful")
    void listAll_ReturnsListOfPerson_WhenSuccessful(){
        String expectedName = PersonCreator.createValidPerson().getName();
        List<Person> persons = personService.listAll();

        Assertions.assertThat(persons)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(persons.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns list of person when successful")
    void findById_ReturnsPerson_WhenSuccessful(){
        Long expectedId = PersonCreator.createValidPerson().getId();
        Optional<Person> person = personService.findById(1l);

        Assertions.assertThat(person)
                .isNotNull();
        Assertions.assertThat(person.get().getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("FindByName returns list of person when successful")
    void findByName_ReturnsPerson_WhenSuccessful() {
        String expectedName = PersonCreator.createValidPerson().getName();
        List<Person> persons = personService.findByName("bruno");

        Assertions.assertThat(persons)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(persons.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns person when successful")
    void save_Person_WhenSuccessful(){
        Person personToBeSaved = PersonCreator.createToBeSavedPerson();
        personToBeSaved.setId(1l);
        Person personSaved = personService.saveOrUpdate(personToBeSaved);
        personService.saveOrUpdate(personSaved);

        Assertions.assertThat(personSaved)
                .isNotNull().isEqualTo(personToBeSaved);
    }

    @Test
    @DisplayName("delete person when successful")
    void delete_RemovePerson_WhenSuccessful() {
        Person personToBeSaved = PersonCreator.createToBeSavedPerson();
        Person personSaved = personRepositoryMock.save(personToBeSaved);

        personService.removeById(personToBeSaved);
        Optional<Person> personOptional = personService.findById(personSaved.getId());

        Assertions.assertThat(personOptional.isEmpty());

    }

    @Test
    @DisplayName("update returns person when successful")
    void update_Person_WhenSuccessful() {

        Assertions.assertThatCode(() -> personService.saveOrUpdate(PersonCreator.createToBeSavedPerson()))
                .doesNotThrowAnyException();

    }

}