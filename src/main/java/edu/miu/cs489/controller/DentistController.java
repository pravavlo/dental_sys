package edu.miu.cs489.controller;


import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.service.DentistService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;


import java.util.List;

@RestController
@RequestMapping("/api/dentists")
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class DentistController {

    private final DentistService dentistService;
    @PreAuthorize("hasRole('OFFICE_MANAGER')")
    @PostMapping
    public ResponseEntity<DentistResponseDto> createDentist(@RequestBody DentistRequestDto dentistRequestDto) {
        System.out.println(dentistRequestDto);
        return new ResponseEntity<>(dentistService.createDentist(dentistRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")

    public ResponseEntity<DentistResponseDto> getDentistById(@PathVariable Long id) {
        return ResponseEntity.ok(dentistService.getDentistById(id));
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")
    @GetMapping("/name/{name}")
    public ResponseEntity<DentistResponseDto> getDentistByName(@PathVariable String name) {
        return ResponseEntity.ok(dentistService.getDentistByName(name));
    }

  @PreAuthorize("hasRole('OFFICE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<DentistResponseDto>> getAllDentists(Pageable pageable ) {
        return ResponseEntity.ok(dentistService.getAllDentists(pageable));
    }


    @PreAuthorize("hasRole('OFFICE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<DentistResponseDto> updateDentist(@PathVariable Long id, @RequestBody DentistRequestDto dentistRequestDto) {
        return ResponseEntity.ok(dentistService.updateDentist(id, dentistRequestDto));
    }

    @PreAuthorize("hasRole('OFFICE_MANAGER')")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.deleteDentist(id);
        return ResponseEntity.noContent().build();
    }
}
