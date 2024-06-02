package com.globits.da.rest;

import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/certificate")
public class RestCertificateController {
    CertificateService service;

    @Autowired
    public void setService(CertificateService service) {
        this.service = service;
    }

    @PostMapping
    public RestResponse<CertificateDto> create(@RequestBody CertificateDto dto) {
        return new RestResponse<>(service.create(dto));
    }

    @PutMapping("{id}")
    public RestResponse<CertificateDto> updateById(@PathVariable Integer id, @RequestBody CertificateDto dto){
        return new RestResponse<>(service.updateById(id, dto));
    }

    @GetMapping("{id}")
    RestResponse<CertificateDto> getById(@PathVariable Integer id){
        return new RestResponse<>(service.getById(id));
    }

    @GetMapping("all")
    RestResponse<List<CertificateDto>> getAll(){
        return new RestResponse<>(service.getAll());
    }

    @DeleteMapping("{id}")
    RestResponse<Boolean> deleteById(@PathVariable Integer id){
        return new RestResponse<>(service.deleteById(id));
    }
}
