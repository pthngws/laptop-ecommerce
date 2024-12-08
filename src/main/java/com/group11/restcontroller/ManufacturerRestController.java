package com.group11.restcontroller;


import com.group11.dto.ManufacturerDTO;
import com.group11.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerRestController {

    @Autowired
    private IManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturerById(@PathVariable Long id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        return ResponseEntity.ok(manufacturerService.createManufacturer(manufacturerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(
            @PathVariable Long id,
            @RequestBody ManufacturerDTO manufacturerDTO) {
        return ResponseEntity.ok(manufacturerService.updateManufacturer(id, manufacturerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}
