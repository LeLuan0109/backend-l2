package com.globits.da.service.impl;

import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.ProvinceService;
import com.globits.da.utils.Converter;
import com.globits.da.validator.ProvinceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    private ProvinceRepository provinceRepository;

    private DistrictRepository districtRepository;

    private ProvinceValidator validator;

    private Converter converter;

    @Autowired
    public void setProvinceRepository(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Autowired
    public void setValidator(ProvinceValidator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public ProvinceDto create(ProvinceDto dto) {
        validator.checkDto(dto, null);
        Province province = new Province();
        converter.setValueToProvince(province, dto, false);
        provinceRepository.save(province);
        return converter.provinceToProvinceDto(province, true, true);
    }

    @Override
    public ProvinceDto updateById(Integer id, ProvinceDto dto) {
        validator.checkDto(dto, id);
        Province province = provinceRepository.getOne(id);
        converter.setValueToProvince(province, dto, true);
        province = provinceRepository.save(province);
        return converter.provinceToProvinceDto(province, true, true);
    }

    @Override
    public ProvinceDto getById(Integer id) {
        validator.checkExistence(id);
        Province province = provinceRepository.getOne(id);
        return converter.provinceToProvinceDto(province, true, true);
    }

    @Override
    public List<DistrictDto> getDistrictsById(Integer id, boolean includeCommunes) {
        validator.checkExistence(id);
        List<District> districts = districtRepository.getAllByProvinceId(id);
        return converter.districtsToDistrictDtos(districts, includeCommunes);
    }

    @Override
    public List<ProvinceDto> getAll(boolean includeDistricts, boolean includeCommunes) {
        List<Province> provinces = provinceRepository.findAll();
        if (CollectionUtils.isEmpty(provinces))
            return new ArrayList<>();
        return provinces.stream()
                .map(province -> converter.provinceToProvinceDto(province, includeDistricts, includeCommunes))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Integer id) {
        validator.checkForDelete(id);
        provinceRepository.deleteById(id);
        return true;
    }
}
