package edu.miu.cs489.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistRequestDto {
    private String dentistName;
    private String specialization;
}
