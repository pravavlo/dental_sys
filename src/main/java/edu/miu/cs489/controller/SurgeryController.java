
package edu.miu.cs489.controller;


import edu.miu.cs489.dto.request.SurgeryRequestDto;
import edu.miu.cs489.dto.response.SurgeryResponseDto;
import edu.miu.cs489.service.SurgeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surgeries")
@RequiredArgsConstructor
public class SurgeryController {

    private final SurgeryService surgeryService;

    @PostMapping
    public ResponseEntity<SurgeryResponseDto> createSurgery(@RequestBody SurgeryRequestDto surgeryRequestDto) {
        System.out.println("i have entered ,,,,,,,,,,,,,,,,,,,,,,"+surgeryRequestDto);
        return new ResponseEntity<>(surgeryService.createSurgery(surgeryRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurgeryResponseDto> getSurgeryById(@PathVariable Long id) {
        return ResponseEntity.ok(surgeryService.getSurgeryById(id));
    }

    @GetMapping("/surgery-no/{surgeryNo}")
    public ResponseEntity<SurgeryResponseDto> getSurgeryBySurgeryNo(@PathVariable String surgeryNo) {
        return ResponseEntity.ok(surgeryService.getSurgeryBySurgeryNo(surgeryNo));
    }

    @GetMapping
    public ResponseEntity<List<SurgeryResponseDto>> getAllSurgeries() {
        return ResponseEntity.ok(surgeryService.getAllSurgeries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurgeryResponseDto> updateSurgery(@PathVariable Long id, @RequestBody SurgeryRequestDto surgeryRequestDto) {
        return ResponseEntity.ok(surgeryService.updateSurgery(id, surgeryRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurgery(@PathVariable Long id) {
        surgeryService.deleteSurgery(id);
        return ResponseEntity.noContent().build();
    }
}


