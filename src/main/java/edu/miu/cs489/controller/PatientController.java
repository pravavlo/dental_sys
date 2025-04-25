package edu.miu.cs489.controller;

import edu.miu.cs489.dto.request.PatientRequestDto;
import edu.miu.cs489.dto.response.PatientResponseDto;
import edu.miu.cs489.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody PatientRequestDto patientRequestDto) {
        System.out.println(patientRequestDto);

        return new ResponseEntity<>(patientService.createPatient(patientRequestDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @GetMapping("/pat-no/{patNo}")
    public ResponseEntity<PatientResponseDto> getPatientByPatNo(@PathVariable String patNo) {
        return ResponseEntity.ok(patientService.getPatientByPatNo(patNo));
    }
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDto));
    }
    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
