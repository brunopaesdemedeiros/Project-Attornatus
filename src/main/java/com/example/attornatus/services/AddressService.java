package com.example.attornatus.services;

import com.example.attornatus.model.Address;
import com.example.attornatus.model.Person;
import com.example.attornatus.repository.AddressRepository;
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
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Address saveOrUpdate(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAll() {

        return addressRepository.findAll();
    }
    public List<Address> findByName(String name) {

        return addressRepository.findByName(name);
    }

    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Transactional
    public void removeById(Address address) {
        addressRepository.delete(address);
    }


}
