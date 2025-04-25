package edu.miu.cs489.repoTest;
import edu.miu.cs489.model.*;
import edu.miu.cs489.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class SurgeryRepositoryTest {

    @Autowired
    private SurgeryRepository surgeryRepository;

    @Test
    void testFindBySurgeryNo_shouldReturnSurgery_whenSurgeryNoExists() {
        Surgery surgery = new Surgery();
        surgery.setSurgeryNo("S123");

        surgeryRepository.save(surgery);

        Optional<Surgery> result = surgeryRepository.findBySurgeryNo("S123");

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(s -> {
                    assertThat(s.getSurgeryNo()).isEqualTo("S123");
                });
    }

    @Test
    void testFindBySurgeryNo_shouldReturnEmpty_whenSurgeryNoDoesNotExist() {
        Optional<Surgery> result = surgeryRepository.findBySurgeryNo("NON_EXISTENT");

        assertThat(result).isNotPresent();
    }
}