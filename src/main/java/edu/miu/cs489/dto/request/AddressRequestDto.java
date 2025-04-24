package edu.miu.cs489.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
