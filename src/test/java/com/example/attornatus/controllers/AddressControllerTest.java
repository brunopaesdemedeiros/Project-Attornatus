package com.example.attornatus.controllers;

import com.example.attornatus.dtos.AddressDto;
import com.example.attornatus.model.Address;
import com.example.attornatus.services.AddressService;
import com.example.attornatus.util.AddressCreator;
import com.example.attornatus.util.AddressDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;
    @Mock
    private AddressService addressServiceMock;

    @BeforeEach
    void setUp(){
        List<Address> addresses = new ArrayList<>(List.of(AddressCreator.createValidAddress()));

        BDDMockito.when(addressServiceMock.findAll()).thenReturn(addresses);

        BDDMockito.when(addressServiceMock.findAll())
                .thenReturn(List.of(AddressCreator.createToBeSavedAddress()));

        BDDMockito.when(addressServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of((AddressCreator.createToBeSavedAddress())));

        BDDMockito.when(addressServiceMock.saveOrUpdate(ArgumentMatchers.any(Address.class)))
                .thenReturn(AddressCreator.createToBeSavedAddress());

        BDDMockito.doNothing().when(addressServiceMock).removeById(ArgumentMatchers.any(Address.class));
    }

    @Test
    @DisplayName("ListAll returns list of address when successful")
    void listAll_ReturnsListOfAddress_WhenSuccessful(){
        String expectedName = AddressCreator.createValidAddress().getAddress();
        List<Address> addresss = addressController.findAllAddress().getBody();

        Assertions.assertThat(addresss)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(addresss.get(0).getAddress()).isEqualTo(expectedName);
     }

    @Test
    @DisplayName("findById returns list of address when successful")
    void findById_ReturnsAddress_WhenSuccessful(){

        Long expectedId = AddressDtoCreator.createToBeSavedAddress().getId();

        AddressDto personToBeSaved = AddressDtoCreator.createToBeSavedAddress();

        ResponseEntity<Object> entity= addressController.findAddressById(personToBeSaved.getId());

        Assertions.assertThat(personToBeSaved.getId()).isNotNull()
                .isEqualTo(expectedId);

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("save returns address when successful")
    void save_ReturnsAddress_WhenSuccessful(){
        AddressDto addressToBeSaved = AddressDtoCreator.createToBeSavedAddress();

        ResponseEntity<Object> entity= addressController.saveOrUpdate(addressToBeSaved);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("delete address when successful")
    void delete_RemoveAddress_WhenSuccessful() {

        Assertions.assertThatCode(() -> addressController.removeAddress(1l)).doesNotThrowAnyException();

        ResponseEntity<Object> entity = addressController.removeAddress(1l);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update returns address when successful")
    void update_Address_WhenSuccessful() {

        AddressDto addressToBeSaved = AddressDtoCreator.createToBeSavedAddress();
        addressToBeSaved.setAddress("rua jose joao");

        ResponseEntity<Object> entity= addressController.saveOrUpdate(addressToBeSaved);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}