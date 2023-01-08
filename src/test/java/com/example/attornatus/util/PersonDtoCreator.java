package com.example.attornatus.util;

import com.example.attornatus.dtos.AddressDto;
import com.example.attornatus.dtos.PersonDto;

import java.time.LocalDate;

public class PersonDtoCreator {

    public static PersonDto createToBeSavedPerson() {
        return PersonDto.builder().id(1l).name("Bruno")
                .birthDate(LocalDate.of(1989, 12, 18)).build();
    }
}
