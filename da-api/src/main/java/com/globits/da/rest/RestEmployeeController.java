package com.globits.da.rest;

import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.dto.search.EmployeeSearchDto;
import com.globits.da.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
public class RestEmployeeController {
    EmployeeService service;

    @Autowired
    public void setService(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public RestResponse<EmployeeDto> create(@RequestBody EmployeeDto dto) {
        return new RestResponse<>(service.create(dto));
    }

    @PutMapping("{id}")
    public RestResponse<EmployeeDto> updateById(@PathVariable Integer id, @RequestBody EmployeeDto dto){
        return new RestResponse<>(service.updateById(id, dto));
    }

    @GetMapping("{id}")
    RestResponse<EmployeeDto> getById(@PathVariable Integer id){
        return new RestResponse<>(service.getById(id));
    }

    @GetMapping("all")
    RestResponse<List<EmployeeDto>> getAll(){
        return new RestResponse<>(service.getAll());
    }

    @DeleteMapping("{id}")
    RestResponse<Boolean> deleteById(@PathVariable Integer id){
        return new RestResponse<>(service.deleteById(id));
    }
    
    @PostMapping("excel")
    public RestResponse<List<EmployeeDto>> importExcel(@RequestPart MultipartFile file) {
        return service.createByExcelFile(file);
    }

    @GetMapping("excel")
    public RestResponse<?> exportExcel(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Employees.xlsx";
        response.setHeader(headerKey, headerValue);
        return service.exportExcelFile(response);
    }

    @GetMapping("search")
    RestResponse<Page<EmployeeDto>> search(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer provinceId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId
    ){
        EmployeeSearchDto searchDto = new EmployeeSearchDto();
        searchDto.setAge(age);
        searchDto.setCode(code);
        searchDto.setName(name);
        searchDto.setEmail(email);
        searchDto.setPhone(phone);
        searchDto.setProvinceId(provinceId);
        searchDto.setDistrictId(districtId);
        searchDto.setCommuneId(communeId);
        searchDto.setPageIndex(pageIndex);
        searchDto.setPageSize(pageSize);
        Page<EmployeeDto> results = service.search(searchDto);
        return new RestResponse<>(results);
    }
}
