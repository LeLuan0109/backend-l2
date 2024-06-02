package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.dto.CommuneDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.CommuneService;
import com.globits.da.utils.Converter;
import com.globits.da.validator.CommuneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuneServiceImpl implements CommuneService {
    private CommuneRepository communeRepository;

    private DistrictRepository districtRepository;

    private CommuneValidator validator;

    private Converter converter;

    @Autowired
    public void setCommuneRepository(CommuneRepository communeRepository) {
        this.communeRepository = communeRepository;
    }

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Autowired
    public void setValidator(CommuneValidator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public CommuneDto create(CommuneDto dto) {
        validator.checkDto(dto, null, true);
        Commune commune = new Commune();
        commune.setName(dto.getName().trim());
        commune.setDistrict(districtRepository.getOne(dto.getDistrictId()));
        communeRepository.save(commune);
        return new CommuneDto(commune);
    }

    @Override
    public CommuneDto updateById(CommuneDto dto, Integer id) {
        validator.checkDto(dto, id, true);
        Commune commune = communeRepository.getOne(id);
        commune.setName(dto.getName().trim());
        commune.setDistrict(districtRepository.getOne(dto.getDistrictId()));
        commune = communeRepository.save(commune);
        return new CommuneDto(commune);
    }

    @Override
    public CommuneDto getById(Integer id) {
        validator.checkExistence(id);
        return new CommuneDto(communeRepository.getOne(id));
    }

    @Override
    public List<CommuneDto> getAll() {
        List<Commune> communes = communeRepository.findAll();
        return converter.communesToCommuneDtos(communes);
    }

    @Override
    public boolean deleteById(Integer id) {
        validator.checkForDelete(id);
        communeRepository.deleteById(id);
        return true;
    }
}
