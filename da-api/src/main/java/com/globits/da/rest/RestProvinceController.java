package com.globits.da.rest;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/province")
public class RestProvinceController {
    ProvinceService service;

    @Autowired
    public void setService(ProvinceService service) {
        this.service = service;
    }

    @PostMapping("")
    RestResponse<ProvinceDto> create(@RequestBody ProvinceDto dto){
        return new RestResponse<>(service.create(dto));
    }

    @PutMapping("{id}")
    RestResponse<ProvinceDto> updateById(@RequestBody ProvinceDto dto, @PathVariable Integer id){
        return new RestResponse<>(service.updateById(id, dto));
    }

    @GetMapping("{id}")
    RestResponse<ProvinceDto> getById(@PathVariable Integer id){
        return new RestResponse<>(service.getById(id));
    }

    @GetMapping("{id}/districts")
    RestResponse<List<DistrictDto>> getDistrictsById(@PathVariable Integer id){
        return new RestResponse<>(service.getDistrictsById(id, true));
    }

    @GetMapping("all")
    RestResponse<List<ProvinceDto>> getAll(){
        return new RestResponse<>(service.getAll(false, false));
    }

    @GetMapping("all-in-detail")
    RestResponse<List<ProvinceDto>> getAllInDetail(){
        return new RestResponse<>(service.getAll(true, false));
    }

    @DeleteMapping("{id}")
    RestResponse<Boolean> delete(@PathVariable Integer id){
        return new RestResponse<>(service.deleteById(id));
    }
}
