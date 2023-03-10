package com.example.attornatus.repository;

import com.example.attornatus.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);
}
