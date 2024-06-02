package com.globits.da.service;

import com.globits.da.dto.CommuneDto;

import java.util.List;

public interface CommuneService {
    CommuneDto create(CommuneDto communeDto);
    CommuneDto updateById(CommuneDto communeDto, Integer id);
    CommuneDto getById(Integer id);
    boolean deleteById(Integer id);
    List<CommuneDto> getAll();
}
