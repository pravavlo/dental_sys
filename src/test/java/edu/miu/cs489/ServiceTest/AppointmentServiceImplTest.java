package edu.miu.cs489.ServiceTest;
import edu.miu.cs489.dto.request.AppointmentRequestDto;
import edu.miu.cs489.dto.response.AppointmentResponseDto;
import edu.miu.cs489.exception.ResourceNotFoundException;
import edu.miu.cs489.mapper.PatientMapper;
import edu.miu.cs489.mapper.SurgeryMapper;
import edu.miu.cs489.model.*;
import edu.miu.cs489.repository.*;
import edu.miu.cs489.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DentistRepository dentistRepository;
    @Mock
    private SurgeryRepository surgeryRepository;
    @Mock
    private SurgeryMapper surgeryMapper;
    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointment_Success() {
        // Arrange
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setPatientId(1L);
        dto.setDentistId(2L);
        dto.setSurgeryId(3L);
        dto.setAppointmentDate(LocalDate.of(2025, 5, 1));
        dto.setAppointmentTime(LocalTime.of(10, 0));

        Patient patient = new Patient();
        Dentist dentist = new Dentist();
        Surgery surgery = new Surgery();
        Appointment appointment = new Appointment();
        Appointment savedAppointment = new Appointment();
        savedAppointment.setId(99L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(dentistRepository.findById(2L)).thenReturn(Optional.of(dentist));
        when(surgeryRepository.findById(3L)).thenReturn(Optional.of(surgery));
        when(appointmentRepository.findByDentistAndAppointmentDateBetween(eq(dentist), any(), any()))
                .thenReturn(Collections.emptyList());
        when(appointmentRepository.existsByPatientAndPaidFalse(patient)).thenReturn(false);
        when(appointmentRepository.save(any())).thenReturn(savedAppointment);

        // Act
        AppointmentResponseDto result = appointmentService.createAppointment(dto);

        // Assert
        assertNotNull(result);
        verify(appointmentRepository).save(any());
    }

    @Test
    void testCreateAppointment_ThrowsIf5AppointmentsExist() {
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setPatientId(1L);
        dto.setDentistId(2L);
        dto.setSurgeryId(3L);
        dto.setAppointmentDate(LocalDate.now());

        Patient patient = new Patient();
        Dentist dentist = new Dentist();
        Surgery surgery = new Surgery();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(dentistRepository.findById(2L)).thenReturn(Optional.of(dentist));
        when(surgeryRepository.findById(3L)).thenReturn(Optional.of(surgery));
        when(appointmentRepository.findByDentistAndAppointmentDateBetween(eq(dentist), any(), any()))
                .thenReturn(List.of(new Appointment(), new Appointment(), new Appointment(), new Appointment(), new Appointment()));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            appointmentService.createAppointment(dto);
        });

        assertEquals("Dentist already has 5 appointments in the given week.", exception.getMessage());
    }

    @Test
    void testCreateAppointment_UnpaidBill_ThrowsException() {
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setPatientId(1L);
        dto.setDentistId(2L);
        dto.setSurgeryId(3L);
        dto.setAppointmentDate(LocalDate.now());

        Patient patient = new Patient();
        Dentist dentist = new Dentist();
        Surgery surgery = new Surgery();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(dentistRepository.findById(2L)).thenReturn(Optional.of(dentist));
        when(surgeryRepository.findById(3L)).thenReturn(Optional.of(surgery));
        when(appointmentRepository.findByDentistAndAppointmentDateBetween(any(), any(), any())).thenReturn(Collections.emptyList());
        when(appointmentRepository.existsByPatientAndPaidFalse(patient)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            appointmentService.createAppointment(dto);
        });

        assertEquals("Cannot create new appointment: unpaid bill exists.", exception.getMessage());
    }

    @Test
    void testGetAppointmentById_NotFound() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.getAppointmentById(99L);
        });

        assertTrue(exception.getMessage().contains("Appointment not found"));
    }

    @Test
    void testDeleteAppointment_Exists() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository).deleteById(1L);
    }

    @Test
    void testDeleteAppointment_NotExists() {
        when(appointmentRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.deleteAppointment(1L);
        });

        assertTrue(exception.getMessage().contains("Appointment not found with id"));
    }

    @Test
    void testMarkAppointmentAsPaid_Success() {
        Appointment appt = new Appointment();
        appt.setPaid(false);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appt));

        appointmentService.markAppointmentAsPaid(1L);

        assertTrue(appt.isPaid());
        verify(appointmentRepository).save(appt);
    }

    @Test
    void testGetAppointmentsByDentistId_Success() {
        Dentist dentist = new Dentist();
        dentist.setId(1L);
        Appointment appt = new Appointment();
        appt.setDentist(dentist);

        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(appointmentRepository.findByDentist(dentist)).thenReturn(List.of(appt));

        List<AppointmentResponseDto> result = appointmentService.getAppointmentsByDentistId(1L);

        assertNotNull(result);
        verify(appointmentRepository).findByDentist(dentist);
    }

    @Test
    void testGetAppointmentsByDentistId_NotFound() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.getAppointmentsByDentistId(1L));
    }

    @Test
    void testGetAppointmentsByPatientId_Success() {
        Patient patient = new Patient();
        patient.setId(1L);
        Appointment appt = new Appointment();
        appt.setPatient(patient);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatient(patient)).thenReturn(List.of(appt));

        List<AppointmentResponseDto> result = appointmentService.getAppointmentsByPatientId(1L);

        assertNotNull(result);
        verify(appointmentRepository).findByPatient(patient);
    }

    @Test
    void testGetAppointmentsBySurgeryId_Success() {
        Surgery surgery = new Surgery();
        surgery.setId(1L);
        Appointment appt = new Appointment();
        appt.setSurgery(surgery);

        when(surgeryRepository.findById(1L)).thenReturn(Optional.of(surgery));
        when(appointmentRepository.findBySurgery(surgery)).thenReturn(List.of(appt));

        List<AppointmentResponseDto> result = appointmentService.getAppointmentsBySurgeryId(1L);

        assertNotNull(result);
        verify(appointmentRepository).findBySurgery(surgery);
    }

    @Test
    void testGetAppointmentsByDate() {
        LocalDate date = LocalDate.now();
        Appointment appt = new Appointment();
        appt.setAppointmentDate(date);

        when(appointmentRepository.findByAppointmentDate(date)).thenReturn(List.of(appt));

        List<AppointmentResponseDto> result = appointmentService.getAppointmentsByDate(date);

        assertNotNull(result);
        verify(appointmentRepository).findByAppointmentDate(date);
    }

    @Test
    void testGetAllAppointments() {
        Appointment appt = new Appointment();
        when(appointmentRepository.findAll()).thenReturn(List.of(appt));

        List<AppointmentResponseDto> result = appointmentService.getAllAppointments();

        assertNotNull(result);
        verify(appointmentRepository).findAll();
    }

    @Test
    void testUpdateAppointment_Success() {
        Long id = 1L;
        Appointment existing = new Appointment();
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setAppointmentDate(LocalDate.of(2025, 5, 5));
        dto.setAppointmentTime(LocalTime.of(14, 30));
        dto.setPatientId(2L);
        dto.setDentistId(3L);
        dto.setSurgeryId(4L);

        Patient patient = new Patient();
        Dentist dentist = new Dentist();
        Surgery surgery = new Surgery();

        when(appointmentRepository.findById(id)).thenReturn(Optional.of(existing));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(dentistRepository.findById(3L)).thenReturn(Optional.of(dentist));
        when(surgeryRepository.findById(4L)).thenReturn(Optional.of(surgery));
        when(appointmentRepository.save(any())).thenReturn(existing);

        AppointmentResponseDto result = appointmentService.updateAppointment(id, dto);

        assertNotNull(result);
        verify(appointmentRepository).save(existing);
    }


    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}