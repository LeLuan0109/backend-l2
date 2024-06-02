package com.globits.da.rest;

import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.service.EmployeeCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee-certificate")
public class RestEmployeeCertificateController {
    EmployeeCertificateService service;

    @Autowired
    public void setService(EmployeeCertificateService service) {
        this.service = service;
    }

    @PostMapping
    public RestResponse<EmployeeCertificateDto> create(@RequestBody EmployeeCertificateDto dto) {
        return new RestResponse<>(service.create(dto));
    }

    @PutMapping("{id}")
    public RestResponse<EmployeeCertificateDto> updateById(@PathVariable Integer id, @RequestBody EmployeeCertificateDto dto){
        return new RestResponse<>(service.updateById(dto, id));
    }

    @GetMapping("{id}")
    RestResponse<EmployeeCertificateDto> getById(@PathVariable Integer id){
        return new RestResponse<>(service.getById(id));
    }

    @GetMapping("all")
    RestResponse<List<EmployeeCertificateDto>> getAll(){
        return new RestResponse<>(service.getAll());
    }

    @DeleteMapping("{id}")
    RestResponse<Boolean> deleteById(@PathVariable Integer id){
        return new RestResponse<>(service.deleteById(id));
    }
}
