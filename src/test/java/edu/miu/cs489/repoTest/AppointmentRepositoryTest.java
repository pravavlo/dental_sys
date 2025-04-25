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



@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Dentist dentist;
    private Patient patient;
    private Surgery surgery;

    @BeforeEach
    void setup() {
        dentist = new Dentist();
        dentist.setDentistName("Dr. Tooth");
        dentist.setDentistNumber("D123");
        dentist.setSpecialization("Orthodontist");
        entityManager.persist(dentist);

        patient = new Patient();
        patient.setPatNo("P123");
        patient.setPatName("John Doe");
        entityManager.persist(patient);

        surgery = new Surgery();
        surgery.setSurgeryNo("S123");
        entityManager.persist(surgery);
    }

    @Test
    void testFindByDentist() {
        Appointment appointment = createAppointment(LocalDate.now());
        appointment.setDentist(dentist);
        entityManager.persist(appointment);

        List<Appointment> result = appointmentRepository.findByDentist(dentist);
        assertThat(result).hasSize(1).contains(appointment);
    }

    @Test
    void testFindByPatient() {
        Appointment appointment = createAppointment(LocalDate.now());
        appointment.setPatient(patient);
        entityManager.persist(appointment);

        List<Appointment> result = appointmentRepository.findByPatient(patient);
        assertThat(result).hasSize(1).contains(appointment);
    }

    @Test
    void testFindBySurgery() {
        Appointment appointment = createAppointment(LocalDate.now());
        appointment.setSurgery(surgery);
        entityManager.persist(appointment);

        List<Appointment> result = appointmentRepository.findBySurgery(surgery);
        assertThat(result).hasSize(1).contains(appointment);
    }

    @Test
    void testFindByAppointmentDate() {
        LocalDate date = LocalDate.of(2025, 4, 25);
        Appointment appointment = createAppointment(date);
        entityManager.persist(appointment);

        List<Appointment> result = appointmentRepository.findByAppointmentDate(date);
        assertThat(result).hasSize(1).contains(appointment);
    }

    @Test
    void testFindByDentistAndAppointmentDateBetween() {
        LocalDate today = LocalDate.now();
        Appointment appointment = createAppointment(today);
        appointment.setDentist(dentist);
        entityManager.persist(appointment);

        List<Appointment> result = appointmentRepository.findByDentistAndAppointmentDateBetween(
                dentist, today.minusDays(1), today.plusDays(1));

        assertThat(result).hasSize(1).contains(appointment);
    }

    @Test
    void testExistsByPatientAndPaidFalse() {
        Appointment appointment = createAppointment(LocalDate.now());
        appointment.setPatient(patient);
        appointment.setPaid(false);
        entityManager.persist(appointment);

        boolean exists = appointmentRepository.existsByPatientAndPaidFalse(patient);
        assertThat(exists).isTrue();
    }

    private Appointment createAppointment(LocalDate date) {
        Appointment a = new Appointment();
        a.setAppointmentDate(date);
        a.setDentist(dentist);
        a.setPatient(patient);
        a.setSurgery(surgery);
        a.setPaid(false);
        a.setAppointmentTime(LocalTime.of(10, 0));
        return a;
    }
}