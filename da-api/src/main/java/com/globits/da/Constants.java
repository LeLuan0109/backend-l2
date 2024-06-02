package com.globits.da;

public class Constants {
	public static final int MAX_NUMBER_OF_CERTIFICATES = 3;
	public static final String EMPLOYEE_CODE_REGEX = "(\\S){6,10}";
	public static final String EMPLOYEE_EMAIL_REGEX = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}";
	public static final String EMPLOYEE_PHONE_REGEX = "(\\d){1,11}";

	public static final String FILE_FORMAT = "File excel phải có định dạng: code (text), name (text), age (number), email (text), phone (text), provinceId (number), districtId (number), communeId(number)";
	public static final String EMPLOYEE_CODE_FORMAT_ERROR = "Employee code có độ dài 6-10 kí tự, không được chứa khoảng trắng";
	public static final String EMPLOYEE_EMAIL_FORMAT_ERROR = "Định dạng email không phù hợp";
	public static final String EMPLOYEE_PHONE_FORMAT_ERROR = "Số điện thoại chỉ gồm các kí tự là số, độ dài từ 1 - 11 kí tự";

	public static final String NULL_EMPLOYEE_CODE = "Mã nhân viên không được null";
	public static final String NULL_EMPLOYEE_NAME = "Tên nhân viên không được null";
	public static final String NULL_EMPLOYEE_EMAIL = "Email nhân viên không được null";
	public static final String NULL_EMPLOYEE_PHONE = "Số điện thoại nhân viên không được null";
	public static final String NULL_EMPLOYEE_AGE = "Tuổi nhân viên không được null";
	public static final String NULL_EMPLOYEE_PROVINCEID = "Id tỉnh của nhân viên không được null";
	public static final String NULL_EMPLOYEE_DISTRICTID = "Id huyện của viên không được null";
	public static final String NULL_EMPLOYEE_COMMUNEID = "Id xã của nhân viên không được null";

	public static final int MIN_EMPLOYEE_AGE = 18;
	public static final int MAX_EMPLOYEE_AGE = 60;
	public static final String INVALID_EMPLOYEE_AGE_ERROR = "Tuổi của employee từ 18 đến 60";


	public static enum StaffType {
		Sale(1), // nhân viên bán hàng
		Cashier(2), // nhân viên thu ngân
		Other(3)// khác
		;

		private Integer value;

		private StaffType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum ChannelAds {// kenh quang cao
		Webiste(1), // website
		Contextual_Advertiser(2), // khen hquang cao
		Social_Netword(3), // mang xa hoi
		Youtube_channel(4)// youtube
		;

		private Integer value;

		private ChannelAds(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}
	
	public static enum Social_Netword {// kenh quang cao
		Facebook(1), // website
		Zalo(2), // khen hquang cao
		Tiktok(3), // mang xa hoi
		Other(4)// youtube
		;

		private Integer value;

		private Social_Netword(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

}
