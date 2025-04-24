package edu.miu.cs489.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDto {
    private String patNo;
    private String patName;
    private AddressRequestDto address;
}
