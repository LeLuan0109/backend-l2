package com.globits.da.service;

import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.dto.search.EmployeeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto employeeDto);
    EmployeeDto getById(Integer id);
    EmployeeDto updateById(Integer id, EmployeeDto employeeDto);
    List<EmployeeDto> getAll();
    boolean deleteById(Integer id);
    RestResponse<List<EmployeeDto>> createByExcelFile(MultipartFile multipartFile);
    RestResponse<?> exportExcelFile(HttpServletResponse response);
    Page<EmployeeDto> search(EmployeeSearchDto searchDto);
}
