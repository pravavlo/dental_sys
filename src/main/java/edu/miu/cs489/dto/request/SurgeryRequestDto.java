package edu.miu.cs489.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurgeryRequestDto {
    private String surgeryNo;
    private AddressRequestDto address;
}
