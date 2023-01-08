package com.example.attornatus.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    @NotBlank
    private String address;

    @NotBlank
    private String zipCode;

    @NotNull
    private int number;

    @NotBlank
    private String city;

    @NotNull
    private boolean mainAddress;
}
