package com.globits.da.service;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import java.util.List;

public interface ProvinceService {
    ProvinceDto create(ProvinceDto dto);
    ProvinceDto getById(Integer id);
    ProvinceDto updateById(Integer id, ProvinceDto dto);
    boolean deleteById(Integer id);
    List<DistrictDto> getDistrictsById(Integer id, boolean includeCommunes);
    List<ProvinceDto> getAll(boolean includeDistricts, boolean includeCommunes);
}
