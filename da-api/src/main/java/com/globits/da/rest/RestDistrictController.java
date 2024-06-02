package com.globits.da.rest;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/district")
public class RestDistrictController {
    DistrictService service;

    @Autowired
    public void setService(DistrictService service) {
        this.service = service;
    }

    @PostMapping
    RestResponse<DistrictDto> create(@RequestBody DistrictDto dto){
        return new RestResponse<>(service.create(dto));
    }

    @PutMapping("{id}")
    RestResponse<DistrictDto> update(@PathVariable Integer id, @RequestBody DistrictDto dto){
        return new RestResponse<>(service.update(id, dto));
    }

    @GetMapping("{id}")
    RestResponse<DistrictDto> getById(@PathVariable Integer id){
        return new RestResponse<>(service.getById(id));
    }

    @GetMapping("{id}/communes")
    RestResponse<List<CommuneDto>> getCommunesById(@PathVariable Integer id){
        return new RestResponse<>(service.getCommunesById(id));
    }

    @GetMapping("all")
    RestResponse<List<DistrictDto>> getAll(){
        return new RestResponse<>(service.getAll(false));
    }

    @GetMapping("all-in-detail")
    RestResponse<List<DistrictDto>> getAllInDetail(){
        return new RestResponse<>(service.getAll(true));
    }

    @DeleteMapping("{id}")
    RestResponse<Boolean> deleteById(@PathVariable Integer id){
        return new RestResponse<>(service.deleteById(id));
    }
}
