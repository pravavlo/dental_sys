package edu.miu.cs489.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String patNo;
    private String patName;
    private AddressResponseDto address;
}
