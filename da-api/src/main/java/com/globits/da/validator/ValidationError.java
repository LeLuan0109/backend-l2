package com.globits.da.validator;

public enum ValidationError {
    SUCCESS(20000, "Yêu cầu được xử lý thành công"),

    NULL_PROVINCE_ID(40000, "Id của tỉnh không được null"),
    NULL_DISTRICT_ID(400001, "Id của huyện không được null"),
    NULL_COMMUNE_ID(40002, "Id của xã không được null"),
    NULL_EMPLOYEE_ID(40003, "Id của nhân viên không được null"),
    NULL_CERTIFICATE_ID(40004, "Id của xã không được null"),
    NULL_EMPLOYEE_CERTIFICATE_ID(40005, "Id của nhân viên không được null"),
    NULL_CERTIFICATE_START_DATE(40006, "Ngày bắt đầu của văn bằng không được null"),
    NULL_CERTIFICATE_EXPIRATION_DATE(40007, "Ngày kết thúc của văn bằng không được null"),
    NULL_COMMUNE_NAME(40008, "Tên của xã không được null"),
    NULL_DISTRICT_NAME(40009, "Tên của huyện không được null"),
    NULL_PROVINCE_NAME(40010, "Tên của tỉnh không được null"),
    NULL_CERTIFICATE_NAME(40011, "Tên của văn bằng không được null"),

    PROVINCE_NOT_FOUND(40100, "Id tỉnh không tồn tại"),
    DISTRICT_NOT_FOUND(40101, "Id huyện không tồn tại"),
    COMMUNE_NOT_FOUND(40102, "Id xã không tồn tại"),
    EMPLOYEE_NOT_FOUND(40103, "Id nhân viên không tồn tại"),
    EMPLOYEES_NOT_FOUND(40104, "Danh sách nhân viên hiện chưa có dữ liệu"),
    CERTIFICATE_NOT_FOUND(40105, "Id văn bằng không tồn tại"),
    EMPLOYEE_CERTIFICATE_NOT_FOUND(40106, "Id nhân viên - văn bằng không tồn tại"),
    FILE_NOT_FOUND(40107, "File không tồn tại"),

    COMMUNE_NOT_IN_DISTRICT(40300, "Xã không thuộc huyện"),
    DISTRICT_NOT_IN_PROVINCE(40301, "Huyện không thuộc tỉnh"),

    DELETE_PROVINCE_NOT_ALLOWED(40400, "Không thể xoá tỉnh này"),
    DELETE_DISTRICT_NOT_ALLOWED(40401, "Không thể xoá huyện này"),
    DELETE_COMMUNE_NOT_ALLOWED(40402, "Không thể xoá xã này"),
    DELETE_EMPLOYEE_NOT_ALLOWED(40403, "Không thể xoá nhân viên này"),
    DELETE_CERTIFICATE_NOT_ALLOWER(40404, "Không thể xoá văn bằng này"),

    DUPLICATE_PROVINCE_NAME(40500, "Tên tỉnh đã tồn tại"),
    DUPLICATE_DISTRICT_NAME(40501, "Tên huyện đã tồn tại"),
    DUPLICATE_COMMUNE_NAME(40502, "Tên xã đã tồn tại"),
    DUPLICATE_CERTIFICATE_NAME(40503, "Tên văn bằng đã tồn tại"),
    DUPLICATE_EMPLOYEE_EMAIL(40504, "Email nhân viên đã tồn tại"),
    DUPLICATE_EMPLOYEE_CODE(40505, "Mã nhân viên đã tồn tại"),
    DUPLICATE_EMPLOYEE_PHONE(40506, "Số điện thoại nhân viên đã tồn tại"),
    DUPLICATE_COMMUNE_NAMES_IN_LIST(40507, "Danh sách gửi lên chứa các xã có tên giống nhau"),
    DUPLICATE_DISTRICT_NAMES_IN_LIST(40508, "Danh sách gửi lên chứa các huyện có tên giống nhau"),

    FILE_NOT_READABLE(40600, "File không thể đọc"),
    CANNOT_CREATE_EXCEL_FILE(40601, "Lỗi khi tạo file"),

    CERTIFICATE_EXPIRATION_DATE_BEFORE_START_DATE(40700, "Ngày bắt đầu của văn bằng phải trước ngày hết hạn"),
    CERTIFICATE_EXPIRATION_DATE_BEFORE_NOW(40701,"Ngày hết hạn của văn bằng phải sau ngày hiện tại"),
    INVALID_NUMBER_OF_CERTIFICATES(40702, "Mỗi nhân viên chỉ có tối đa 3 văn bằng cùng loại"),
    INVALID_CERTIFICATE_AND_PROVINCE(40703, "Mỗi nhân viên chỉ không được có 2 văn bằng cùng loại do một tỉnh cấp"),

    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(40800, null),
    CONTRAIN_VIOLATION_EXCEPTION(40801, null),
    RUNTIME_EXCEPTION(50000, null),
    GENERAL_EXCEPTION(50001, null);

    private final int errorCode;
    private final String errorMessage;

    ValidationError(int code, String errorMessage){
        this.errorCode = code;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
 }
