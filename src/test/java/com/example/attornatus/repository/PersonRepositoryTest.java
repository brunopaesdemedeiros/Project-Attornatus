package com.example.attornatus.repository;

import com.example.attornatus.model.Person;
import com.example.attornatus.util.PersonCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Person Repository")
@Log4j2
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Save persists creates person when Successful")
    void save_PersistPerson_WhenSuccessful() {
        Person personToBeSaved = PersonCreator.createToBeSavedPerson();
        Person personSaved = this.personRepository.save(personToBeSaved);
        personRepository.save(personSaved);

        Assertions.assertThat(personSaved).isNotNull();
        Assertions.assertThat(personSaved.getId()).isNotNull();
        Assertions.assertThat(personSaved.getName()).isEqualTo(personSaved.getName());
    }

    @Test
    @DisplayName("Save updates persists person when Successful")
    void save_updatesPerson_WhenSuccessful() {
        Person personToBeUpdated = PersonCreator.createValidUpdatedPerson();
        Person personUpdated = this.personRepository.save(personToBeUpdated);
        personUpdated.setName("Maria");
        Person personUpdate = this.personRepository.save(personUpdated);

        Assertions.assertThat(personUpdate).isNotNull();
        Assertions.assertThat(personUpdate.getId()).isNotNull();
        Assertions.assertThat(personUpdate.getName()).isEqualTo(personUpdated.getName());
    }

    @Test
    @DisplayName("Delete person when Successful")
    void delete_Person_WhenSuccessful() {
        Person personToBeSaved = PersonCreator.createToBeSavedPerson();
        Person personSaved = this.personRepository.save(personToBeSaved);
        this.personRepository.delete(personSaved);
        Optional<Person> personOptional = this.personRepository.findById(personSaved.getId());

        Assertions.assertThat(personOptional.isEmpty());
    }

    @Test
    @DisplayName("Find by id return list of person")
    void findByIdPerson() {
        Person personToBeSaved = PersonCreator.createToBeSavedPerson();
        Person personSaved = this.personRepository.save(personToBeSaved);
        Optional<Person> personOptional = this.personRepository.findById(personSaved.getId());

        Assertions.assertThat(personOptional.isEmpty());
    }

    @Test
    @DisplayName("Find by name return list of person when Successful")
    void findByNamePerson_WhenSuccessful() {
        Person personToBeSaved = PersonCreator.createToBeSavedPerson();
        Person personSaved = this.personRepository.save(personToBeSaved);
        String name = personSaved.getName();
        List<Person> persons = this.personRepository.findByName(name);

        Assertions.assertThat(persons).isNotEmpty();
        Assertions.assertThat(persons).contains(personSaved);
    }
}