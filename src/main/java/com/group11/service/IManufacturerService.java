package com.group11.service;

import com.group11.dto.ManufacturerDTO;
import com.group11.entity.ManufacturerEntity;

import java.util.List;
import java.util.Optional;

public interface IManufacturerService {
    List<ManufacturerDTO> getAllManufacturers();
    ManufacturerDTO getManufacturerById(Long id);
    ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO);
    ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDTO);
    void deleteManufacturer(Long id);

    List<ManufacturerEntity> findAll();

    Optional<ManufacturerEntity> findById(Long id);

    ManufacturerEntity save(ManufacturerEntity category);

    void deleteById(Long id);
}
