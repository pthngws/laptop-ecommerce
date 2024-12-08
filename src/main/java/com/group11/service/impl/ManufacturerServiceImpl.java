package com.group11.service.impl;

import com.group11.dto.ManufacturerDTO;
import com.group11.entity.ManufacturerEntity;
import com.group11.repository.ManufacturerRepository;
import com.group11.service.IManufacturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.List;

@Service
public class ManufacturerServiceImpl implements IManufacturerService {


    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ManufacturerDTO> getAllManufacturers() {
        return manufacturerRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, ManufacturerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ManufacturerDTO getManufacturerById(Long id) {
        ManufacturerEntity entity = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
        return modelMapper.map(entity, ManufacturerDTO.class);
    }

    @Override
    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO) {
        ManufacturerEntity entity = modelMapper.map(manufacturerDTO, ManufacturerEntity.class);
        ManufacturerEntity savedEntity = manufacturerRepository.save(entity);
        return modelMapper.map(savedEntity, ManufacturerDTO.class);
    }

    @Override
    public ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDTO) {
        ManufacturerEntity existingEntity = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
        existingEntity.setName(manufacturerDTO.getName());
        ManufacturerEntity updatedEntity = manufacturerRepository.save(existingEntity);
        return modelMapper.map(updatedEntity, ManufacturerDTO.class);
    }

    @Override
    public void deleteManufacturer(Long id) {
        ManufacturerEntity entity = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
        manufacturerRepository.delete(entity);
    }
}
