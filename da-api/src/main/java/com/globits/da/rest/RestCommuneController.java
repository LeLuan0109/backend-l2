package com.globits.da.rest;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/commune")
public class RestCommuneController {
    CommuneService service;

    @Autowired
    public void setService(CommuneService service) {
        this.service = service;
    }

    @PostMapping
    RestResponse<CommuneDto> create(@RequestBody CommuneDto communeDto){
        return new RestResponse<>(service.create(communeDto));
    }

    @PutMapping("{id}")
    RestResponse<CommuneDto> updateById(@RequestBody CommuneDto communeDto, @PathVariable Integer id){
        return new RestResponse<>(service.updateById(communeDto, id));
    }

    @GetMapping("{id}")
    RestResponse<CommuneDto> getById(@PathVariable Integer id){
        return new RestResponse<>(service.getById(id));
    }

    @GetMapping("all")
    RestResponse<List<CommuneDto>> getAll(){
        return new RestResponse<>(service.getAll());
    }

    @DeleteMapping("{id}")
    RestResponse<Boolean> deleteById(@PathVariable Integer id){
        return new RestResponse<>(service.deleteById(id));
    }
}
