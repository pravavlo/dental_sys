package edu.miu.cs489.repoTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import edu.miu.cs489.model.*;
import edu.miu.cs489.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest

class DentistRepositoryTest {

    @Autowired
    private DentistRepository dentistRepository;

    @Test
    void testFindByDentistName_shouldReturnDentist_whenNameExists() {
        Dentist dentist = new Dentist();
        dentist.setDentistName("Dr. Strange");
        dentist.setDentistNumber("D456");
        dentist.setSpecialization("Endodontist");

        dentistRepository.save(dentist);

        Optional<Dentist> result = dentistRepository.findByDentistName("Dr. Strange");

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(d -> {
                    assertThat(d.getDentistName()).isEqualTo("Dr. Strange");
                    assertThat(d.getSpecialization()).isEqualTo("Endodontist");
                });
    }

    @Test
    void testFindByDentistName_shouldReturnEmpty_whenNameDoesNotExist() {
        Optional<Dentist> result = dentistRepository.findByDentistName("Nonexistent Dentist");

        assertThat(result).isNotPresent();
    }
}
