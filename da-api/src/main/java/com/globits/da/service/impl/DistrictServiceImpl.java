package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.DistrictService;
import com.globits.da.utils.Converter;
import com.globits.da.validator.DistrictValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    private DistrictRepository districtRepository;

    private ProvinceRepository provinceRepository;

    private CommuneRepository communeRepository;

    private DistrictValidator districtValidator;

    private Converter converter;

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Autowired
    public void setCommuneRepository(CommuneRepository communeRepository) {
        this.communeRepository = communeRepository;
    }

    @Autowired
    public void setDistrictValidator(DistrictValidator districtValidator) {
        this.districtValidator = districtValidator;
    }

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public DistrictDto create(DistrictDto dto) {
        districtValidator.checkDto(dto, null, true);
        District district = new District();
        Province province = provinceRepository.getOne(dto.getProvinceId());
        converter.setValueToDistrict(district, dto, province, false);
        districtRepository.save(district);
        return converter.districtToDistrictDto(district, true);
    }

    @Override
    public DistrictDto update(Integer id, DistrictDto dto) {
        districtValidator.checkDto(dto, id, true);
        District district = districtRepository.getOne(id);
        Province province = provinceRepository.getOne(dto.getProvinceId());
        converter.setValueToDistrict(district, dto, province, true);
        districtRepository.save(district);
        return converter.districtToDistrictDto(district, true);
    }
                    
    @Override
    public DistrictDto getById(Integer id) {
        districtValidator.checkExistence(id);
        District entity = districtRepository.getOne(id);
        return converter.districtToDistrictDto(entity, true);
    }

    @Override
    public List<CommuneDto> getCommunesById(Integer districtId) {
        districtValidator.checkExistence(districtId);
        List<Commune> communes = communeRepository.getAllByDistrictId(districtId);
        return converter.communesToCommuneDtos(communes);
    }

    @Override
    public List<DistrictDto> getAll(boolean includeCommunes) {
        List<District> districts = districtRepository.findAll();
        return converter.districtsToDistrictDtos(districts, includeCommunes);
    }

    @Override
    public boolean deleteById(Integer id) {
        districtValidator.checkForDelete(id);
        districtRepository.deleteById(id);
        return true;
    }
}
