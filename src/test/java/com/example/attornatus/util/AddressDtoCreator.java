package com.example.attornatus.util;

import com.example.attornatus.dtos.AddressDto;
import com.example.attornatus.model.Address;

public class AddressDtoCreator {

    public static AddressDto createToBeSavedAddress() {
        return AddressDto.builder().id(1l).address("Doraci de Medeiros").
                city("Capivari").mainAddress(true).number(88).zipCode("88745-000").build();
    }

}
