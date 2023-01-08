package com.example.attornatus.util;

import com.example.attornatus.model.Address;

public class AddressCreator {

    public static Address createToBeSavedAddress() {
        return Address.builder().id(1l).address("Doraci de Medeiros").
                city("Capivari").mainAddress(true).number(88).zipCode("88745-000").build();
    }

    public static Address createValidAddress() {
        return Address.builder().id(1l).address("Doraci de Medeiros").
                city("Capivari").mainAddress(true).number(88).zipCode("88745-000").build();
    }

    public static Address createUpdateAddress() {
        return Address.builder().id(1l).address("Doraci de Medeiros").
                city("Capivari").mainAddress(true).number(88).zipCode("88745-000").build();
    }
}
