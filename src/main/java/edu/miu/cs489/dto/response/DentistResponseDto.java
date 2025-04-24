package edu.miu.cs489.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistResponseDto {
    private Long id;
    private String dentistName;
    private String dentistNumber;
    private String specialization;


    public DentistResponseDto(Long id, String dentistName, String specialization) {
    }
}
