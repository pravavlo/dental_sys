package edu.miu.cs489.repository;


import edu.miu.cs489.model.Appointment;
import edu.miu.cs489.model.Dentist;
import edu.miu.cs489.model.Patient;
import edu.miu.cs489.model.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDentist(Dentist dentist);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findBySurgery(Surgery surgery);

    List<Appointment> findByAppointmentDate(LocalDate date);
    List<Appointment> findByDentistAndAppointmentDateBetween(Dentist dentist, LocalDate start, LocalDate end);
    boolean existsByPatientAndPaidFalse(Patient patient);

}
