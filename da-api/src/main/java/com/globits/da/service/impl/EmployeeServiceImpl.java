package com.globits.da.service.impl;

import com.globits.da.Constants;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.RestResponse;
import com.globits.da.dto.search.EmployeeSearchDto;
import com.globits.da.exception.*;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.EmployeeService;
import com.globits.da.utils.Converter;
import com.globits.da.validator.EmployeeValidator;
import com.globits.da.validator.ValidationError;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private Converter converter;

    private EmployeeRepository employeeRepository;

    private EmployeeValidator employeeValidator;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setEmployeeValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        employeeValidator.checkDto(employeeDto, null);
        Employee employee = new Employee();
        converter.setValueToEmployee(employee, employeeDto);
        employee = employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateById(Integer id, EmployeeDto dto) {
        employeeValidator.checkDto(dto, id);
        Employee employee = employeeRepository.getOne(id);
        converter.setValueToEmployee(employee, dto);
        employee = employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }

    @Override
    public EmployeeDto getById(Integer id) {
        employeeValidator.checkExistence(id);
        Employee employee = employeeRepository.getOne(id);
        return new EmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Integer id) {
        employeeValidator.checkForDelete(id);
        employeeRepository.deleteById(id);
        return true;
    }

    @Override
    public RestResponse<List<EmployeeDto>> createByExcelFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty())
            throw new NotFoundException(ValidationError.FILE_NOT_FOUND);
        Sheet sheet;
        try (InputStream inputStream = multipartFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            throw new FileNotReadableException(ValidationError.FILE_NOT_READABLE);
        }
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        String errorMessage = "Lỗi xảy ra tại dòng ";
        int rowIndex = 0;
        for (Row row : sheet) {
            EmployeeDto dto = new EmployeeDto();
            rowIndex++;
            int columnIndex = 0;
            try {
                dto.setCode(row.getCell(columnIndex++).getStringCellValue());
                dto.setName(row.getCell(columnIndex++).getStringCellValue());
                dto.setAge((int) row.getCell(columnIndex++).getNumericCellValue());
                dto.setEmail(row.getCell(columnIndex++).getStringCellValue());
                dto.setPhone(row.getCell(columnIndex++).getStringCellValue());
                dto.setProvinceId((int) row.getCell(columnIndex++).getNumericCellValue());
                dto.setDistrictId((int) row.getCell(columnIndex++).getNumericCellValue());
                dto.setCommuneId((int) row.getCell(columnIndex++).getNumericCellValue());
            } catch (NullPointerException | IllegalStateException | IllegalArgumentException e) {
                errorMessages.add(errorMessage + rowIndex + ", cột " + columnIndex);
                continue;
            }
            try {
                employeeDtos.add(create(dto));
            } catch (ConstraintViolationException ex) {
                errorMessages.add(errorMessage + rowIndex);
                ex.getConstraintViolations().forEach(violation -> errorMessages.add(violation.getMessage()));
            } catch (NotNullableException | NotFoundException | DuplicateValueException ex) {
                errorMessages.add(errorMessage + rowIndex);
                errorMessages.add(ex.getMessage());
            }
        }
        if (!CollectionUtils.isEmpty(errorMessages))
            errorMessages.add(0, Constants.FILE_FORMAT);
        return new RestResponse<>(employeeDtos, errorMessages);
    }

    @Override
    public RestResponse<?> exportExcelFile(HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Employees");
        List<EmployeeDto> employeeDtos = getAll();
        if (CollectionUtils.isEmpty(employeeDtos))
            throw new NotFoundException(ValidationError.EMPLOYEES_NOT_FOUND);
        createHeaderLine(workbook, sheet);
        createDataLines(employeeDtos, workbook, sheet);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return new RestResponse<>(null);
        } catch (IOException e) {
           throw new ExportExcelFileException(ValidationError.CANNOT_CREATE_EXCEL_FILE);
        }
    }

    private void createHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);
        int columnCount = 0;
        createCell(row, columnCount++, "Id", style, sheet);
        createCell(row, columnCount++, "Code", style, sheet);
        createCell(row, columnCount++, "Name", style, sheet);
        createCell(row, columnCount++, "Age", style, sheet);
        createCell(row, columnCount++, "Email", style, sheet);
        createCell(row, columnCount++, "Phone", style, sheet);
        createCell(row, columnCount++, "Province", style, sheet);
        createCell(row, columnCount++, "District", style, sheet);
        createCell(row, columnCount, "Commune", style, sheet);
    }
    private void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createDataLines(List<EmployeeDto> employeeDtos, XSSFWorkbook workbook, XSSFSheet sheet) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(13);
        style.setFont(font);
        for (EmployeeDto employee : employeeDtos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, employee.getId(), style, sheet);
            createCell(row, columnCount++, employee.getCode(), style, sheet);
            createCell(row, columnCount++, employee.getName(), style, sheet);
            createCell(row, columnCount++, employee.getAge(), style, sheet);
            createCell(row, columnCount++, employee.getEmail(), style, sheet);
            createCell(row, columnCount++, employee.getPhone(), style, sheet);
            createCell(row, columnCount++, employee.getProvinceId(), style, sheet);
            createCell(row, columnCount++, employee.getDistrictId(), style, sheet);
            createCell(row, columnCount, employee.getCommuneId(), style, sheet);
        }
    }

    @Override
    public Page<EmployeeDto> search(EmployeeSearchDto searchDto) {
        String selectEmployee = "SELECT entity FROM Employee entity WHERE (1=1) " + genSearchQueryWhereClause(searchDto);
        String countEmployee = "SELECT COUNT(entity) FROM Employee entity WHERE (1=1) " + genSearchQueryWhereClause(searchDto);

        TypedQuery<Employee> selectQuery = entityManager.createQuery(selectEmployee, Employee.class);
        Query countQuery = entityManager.createQuery(countEmployee);
        setSearchQueryParameter(selectQuery, searchDto);
        setSearchQueryParameter(countQuery, searchDto);

        int pageIndex = searchDto.getPageIndex() - 1;
        int pageSize = searchDto.getPageSize();
        int startPosition = pageIndex * pageSize;
        selectQuery.setFirstResult(startPosition);
        selectQuery.setMaxResults(pageSize);
        List<Employee> employees = selectQuery.getResultList();
        List<EmployeeDto> employeeDtos = employees.stream().map(EmployeeDto::new).collect(Collectors.toList());

        long count = (long) countQuery.getSingleResult();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return new PageImpl<>(employeeDtos, pageable, count);
    }

    private String genSearchQueryWhereClause(EmployeeSearchDto searchDTO) {
        StringBuilder whereClause = new StringBuilder();
        if (StringUtils.hasText(searchDTO.getCode())) {
            whereClause.append("AND LOWER(entity.code) LIKE LOWER(:code)");
        }
        if (StringUtils.hasText(searchDTO.getEmail())) {
            whereClause.append("AND LOWER(entity.email) LIKE LOWER(:email)");
        }
        if (StringUtils.hasText(searchDTO.getName())) {
            whereClause.append("AND LOWER(entity.name) LIKE LOWER(:name)");
        }
        if (StringUtils.hasText(searchDTO.getPhone())) {
            whereClause.append("AND LOWER(entity.phone) LIKE LOWER(:phone)");
        }
        if (!ObjectUtils.isEmpty(searchDTO.getAge())) {
            whereClause.append("AND LOWER(entity.age) LIKE LOWER(:age)");
        }
        if (!ObjectUtils.isEmpty(searchDTO.getProvinceId())) {
            whereClause.append("AND LOWER(entity.province.id) LIKE LOWER(:provinceId)");
        }
        if (!ObjectUtils.isEmpty(searchDTO.getDistrictId())) {
            whereClause.append("AND LOWER(entity.district.id) LIKE LOWER(:districtId)");
        }
        if (!ObjectUtils.isEmpty(searchDTO.getCommuneId())) {
            whereClause.append("AND LOWER(entity.commune.id) LIKE LOWER(:communeId)");
        }
        return whereClause.toString();
    }

    private void setSearchQueryParameter(Query query, EmployeeSearchDto searchDTO) {
        if (searchDTO.getCode() != null && !searchDTO.getCode().trim().isEmpty())
            query.setParameter("code", searchDTO.getCode());
        if (searchDTO.getEmail() != null && !searchDTO.getEmail().trim().isEmpty())
            query.setParameter("email", '%' + searchDTO.getEmail() + '%');
        if (searchDTO.getPhone() != null && !searchDTO.getPhone().trim().isEmpty())
            query.setParameter("phone", '%' + searchDTO.getPhone() + '%');
        if (searchDTO.getName() != null && !searchDTO.getName().trim().isEmpty())
            query.setParameter("name", '%' + searchDTO.getName() + '%');
        if (searchDTO.getAge() != null)
            query.setParameter("age", '%' + searchDTO.getCode() + '%');
        if (searchDTO.getProvinceId() != null)
            query.setParameter("provinceId", '%' + searchDTO.getName() + '%');
        if (searchDTO.getDistrictId() != null)
            query.setParameter("districtId", '%' + searchDTO.getDistrictId() + '%');
        if (searchDTO.getCommuneId() != null)
            query.setParameter("communeId", '%' + searchDTO.getCommuneId() + '%');
    }
}
