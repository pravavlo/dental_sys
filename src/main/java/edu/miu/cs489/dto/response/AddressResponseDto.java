package edu.miu.cs489.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
