package edu.miu.cs489.repoTest;

import edu.miu.cs489.model.*;
import edu.miu.cs489.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest

class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void testFindByPatNo_shouldReturnPatient_whenPatNoExists() {
        Patient patient = new Patient();
        patient.setPatNo("P999");
        patient.setPatName("Jane Doe");

        patientRepository.save(patient);

        Optional<Patient> result = patientRepository.findByPatNo("P999");

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(p -> {
                    assertThat(p.getPatName()).isEqualTo("Jane Doe");
                    assertThat(p.getPatNo()).isEqualTo("P999");
                });
    }

    @Test
    void testFindByPatNo_shouldReturnEmpty_whenPatNoDoesNotExist() {
        Optional<Patient> result = patientRepository.findByPatNo("NONEXISTENT");

        assertThat(result).isNotPresent();
    }
}
