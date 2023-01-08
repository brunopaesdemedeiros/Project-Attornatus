package com.example.attornatus.controllers;

import com.example.attornatus.dtos.AddressDto;
import com.example.attornatus.model.Address;
import com.example.attornatus.services.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Object> saveOrUpdate(@RequestBody @Valid AddressDto addressDto) {
        var address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveOrUpdate(address));
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAllAddress() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findAddressById(@PathVariable(value = "id") Long id) {
        Optional<Address> addressOptional = addressService.findById(id);
        if (!addressOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(addressOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeAddress(@PathVariable(value = "id") Long id) {
        Optional<Address> addressOptional = addressService.findById(id);
        if (!addressOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found.");
        }
        addressService.removeById(addressOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Address removed successfully.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAddress(@PathVariable(value = "id") Long id, @RequestBody @Valid AddressDto addressDto) {
        Optional<Address> addressOptional = addressService.findById(id);
        if (!addressOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found.");
        }
        var address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        address.setId(addressOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(addressService.saveOrUpdate(address));
    }
}
