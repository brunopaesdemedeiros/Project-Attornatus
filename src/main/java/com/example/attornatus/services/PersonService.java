package com.example.attornatus.services;

import com.example.attornatus.model.Person;
import com.example.attornatus.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Person saveOrUpdate(Person person) {

        return personRepository.save(person);
    }
    public List<Person> listAll() {

        return personRepository.findAll();
    }
    public List<Person> findByName(String name) {

        return personRepository.findByName(name);
    }

    public Optional<Person> findById(Long id) {

        return personRepository.findById(id);
    }

    @Transactional
    public void removeById(Person person) {

        personRepository.delete(person);
    }
}
