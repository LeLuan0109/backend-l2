package com.globits.da.service;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;

import java.util.List;

public interface DistrictService {
    DistrictDto create(DistrictDto dto);
    DistrictDto update(Integer id, DistrictDto dto);
    DistrictDto getById(Integer id);
    List<CommuneDto> getCommunesById(Integer id);
    List<DistrictDto> getAll(boolean includeCommunes);
    boolean deleteById(Integer id);
}
