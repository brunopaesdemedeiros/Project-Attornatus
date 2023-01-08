package com.example.attornatus.util;

import com.example.attornatus.dtos.PersonDto;
import com.example.attornatus.model.Person;

import java.time.LocalDate;

public class PersonDtoCreator {

    public static PersonDto createToBeSavedPerson() {
        return PersonDto.builder().name("Bruno").
                birthDate(LocalDate.of(1989, 12, 18)).build();
    }

}


