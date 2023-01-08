package com.example.attornatus.repository;

import com.example.attornatus.model.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.example.attornatus.util.AddressCreator.createToBeSavedAddress;

@DataJpaTest
@DisplayName("Tests for Address Repository")
class AddressRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Save persists creates address")
    void saveAddress() {
        Address addressToBesaved = createToBeSavedAddress();
        Address addressSaved = this.addressRepository.save(addressToBesaved);

        Assertions.assertThat(addressSaved).isNotNull();
        Assertions.assertThat(addressSaved.getId()).isNotNull();
        Assertions.assertThat(addressSaved.getAddress()).isEqualTo(addressSaved.getAddress());
    }

    @Test
    @DisplayName("Update persists address")
    void updateAdress() {
        Address addressToBesaved = createToBeSavedAddress();
        Address addressSaved = this.addressRepository.save(addressToBesaved);
        addressSaved.setAddress("José João Correa");
        Address addressUpdate = this.addressRepository.save(addressSaved);

        Assertions.assertThat(addressUpdate).isNotNull();
        Assertions.assertThat(addressUpdate.getId()).isNotNull();
        Assertions.assertThat(addressUpdate.getAddress()).isEqualTo(addressSaved.getAddress());
    }

    @Test
    @DisplayName("Delete updates address")
    void deleteAddress() {
        Address addressToBesaved = createToBeSavedAddress();
        Address addressSaved = this.addressRepository.save(addressToBesaved);
        this.addressRepository.delete(addressSaved);
        Optional<Address> addressOptional = this.addressRepository.findById(addressSaved.getId());

        Assertions.assertThat(addressOptional.isEmpty());
    }

    @Test
    @DisplayName("Find by id return list of address")
    void findByIdAddress() {
        Address addressToBeSaved = createToBeSavedAddress();
        Address addressSaved = this.addressRepository.save(addressToBeSaved);
        Optional<Address> AddressOptional = this.addressRepository.findById(addressSaved.getId());

        Assertions.assertThat(AddressOptional.isEmpty());
    }
}