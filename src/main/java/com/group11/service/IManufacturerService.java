package com.group11.service;

import com.group11.dto.ManufacturerDTO;

import java.util.List;

public interface IManufacturerService {
    List<ManufacturerDTO> getAllManufacturers();
    ManufacturerDTO getManufacturerById(Long id);
    ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO);
    ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDTO);
    void deleteManufacturer(Long id);
}
