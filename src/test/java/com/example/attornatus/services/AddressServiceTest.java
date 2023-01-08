package com.example.attornatus.services;

import com.example.attornatus.model.Address;
import com.example.attornatus.repository.AddressRepository;
import com.example.attornatus.util.AddressCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepositoryMock;

    @BeforeEach
    void setUp(){
        List<Address> addresses = new ArrayList<>(List.of(AddressCreator.createValidAddress()));

        BDDMockito.when(addressRepositoryMock.findAll()).thenReturn(addresses);

        BDDMockito.when(addressRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AddressCreator.createValidAddress()));

        BDDMockito.when(addressRepositoryMock.save(ArgumentMatchers.any(Address.class)))
                .thenReturn(AddressCreator.createValidAddress());

        BDDMockito.doNothing().when(addressRepositoryMock).delete(ArgumentMatchers.any(Address.class));
    }

    @Test
    @DisplayName("ListAll returns list of address when successful")
    void listAll_ReturnsListOfAddress_WhenSuccessful(){
        String expectedAddress = AddressCreator.createValidAddress().getAddress();
        List<Address> addresss = addressService.findAll();

        Assertions.assertThat(addresss)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(addresss.get(0).getAddress()).isEqualTo(expectedAddress);
    }

    @Test
    @DisplayName("findById returns list of address when successful")
    void findById_ReturnsAddress_WhenSuccessful(){
        Long expectedId = AddressCreator.createValidAddress().getId();
        Optional<Address> address = addressService.findById(1l);

        Assertions.assertThat(address)
                .isNotNull();
        Assertions.assertThat(address.get().getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns address when successful")
    void save_Address_WhenSuccessful(){
        Address addressToBeSaved = AddressCreator.createToBeSavedAddress();
        addressToBeSaved.setId(1l);
        Address addressSaved = addressService.saveOrUpdate(addressToBeSaved);
        addressService.saveOrUpdate(addressSaved);

        Assertions.assertThat(addressSaved)
                .isNotNull().isEqualTo(addressToBeSaved);
    }

    @Test
    @DisplayName("delete address when successful")
    void delete_RemoveAddress_WhenSuccessful() {
        Address addressToBeSaved = AddressCreator.createToBeSavedAddress();
        Address addressSaved = addressRepositoryMock.save(addressToBeSaved);

        addressService.removeById(addressToBeSaved);
        Optional<Address> addressOptional = addressService.findById(addressSaved.getId());

        Assertions.assertThat(addressOptional.isEmpty());
    }

    @Test
    @DisplayName("update returns address when successful")
    void update_Address_WhenSuccessful() {

        Assertions.assertThatCode(() -> addressService.saveOrUpdate(AddressCreator.createToBeSavedAddress()))
                .doesNotThrowAnyException();
    }
}