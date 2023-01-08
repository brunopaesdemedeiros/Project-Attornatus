package com.example.attornatus.controllers;


import com.example.attornatus.dtos.PersonDto;
import com.example.attornatus.model.Person;
import com.example.attornatus.services.PersonService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Object> saveOrUpdate(@RequestBody @Valid PersonDto personDto) {
        var person = new Person();
        BeanUtils.copyProperties(personDto, person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.saveOrUpdate(person));
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAllPerson() {
        return ResponseEntity.status(HttpStatus.OK).body(personService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findPersonById(@PathVariable(value = "id") Long id) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personOptional.get());
    }

    @GetMapping("/{find}")
    public ResponseEntity<Object> findPersonByName(@RequestParam String name) {
        List<Person> persons = personService.findByName(name);
        if (!persons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removePerson(@PathVariable(value = "id") Long id) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        personService.removeById(personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Person removed successfully.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") Long id, @RequestBody @Valid PersonDto personDto) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        var person = new Person();
        BeanUtils.copyProperties(personDto, person);
        person.setId(personOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(personService.saveOrUpdate(person));
    }

}
