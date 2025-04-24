package edu.miu.cs489.controller;


import edu.miu.cs489.dto.request.DentistRequestDto;
import edu.miu.cs489.dto.response.DentistResponseDto;
import edu.miu.cs489.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dentists")
@RequiredArgsConstructor
public class DentistController {

    private final DentistService dentistService;

    @PostMapping
    public ResponseEntity<DentistResponseDto> createDentist(@RequestBody DentistRequestDto dentistRequestDto) {
        System.out.println(dentistRequestDto);
        return new ResponseEntity<>(dentistService.createDentist(dentistRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")

    public ResponseEntity<DentistResponseDto> getDentistById(@PathVariable Long id) {
        return ResponseEntity.ok(dentistService.getDentistById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DentistResponseDto> getDentistByName(@PathVariable String name) {
        return ResponseEntity.ok(dentistService.getDentistByName(name));
    }

    @GetMapping
    public ResponseEntity<List<DentistResponseDto>> getAllDentists() {
        return ResponseEntity.ok(dentistService.getAllDentists());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistResponseDto> updateDentist(@PathVariable Long id, @RequestBody DentistRequestDto dentistRequestDto) {
        return ResponseEntity.ok(dentistService.updateDentist(id, dentistRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.deleteDentist(id);
        return ResponseEntity.noContent().build();
    }
}
