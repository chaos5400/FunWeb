package com.funweb.web.dto;

public class Account {

	private int idx;
	private String userID;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String mobilePhoneNumber;
	private String userRole;
	private boolean EMailActive;

	
	
	
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setAddress(String post, String roadAddress,
			String jibunAddress, String detailAddress) {
		this.address = post + "&" 
				+ roadAddress + "&"
				+ jibunAddress + "&"
				+ detailAddress + "&";
	}

	public String getPostCode() {
		return parseAddress(0);
	}

	public String getRoadAddress() {
		return parseAddress(1);
	}

	public String getJibunAddress() {
		return parseAddress(2);
	}

	public String getDetailAddress() {
		return parseAddress(3);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}
	
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getUserRole() {
		return userRole;
	}
	
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isEMailActive() {
		return EMailActive;
	}
	
	public void setEMailActive(boolean EMailActive) {
		this.EMailActive = EMailActive;
	}

	private String parseAddress(int index) {
		int beginIndex = 0;
		int endIndex = -1;

		do {
			beginIndex = endIndex + 1;
			endIndex = this.address.indexOf('&', beginIndex);
		} while(index-- > 0);
		
		if(endIndex < 0) return "";
		
		return this.address.substring(beginIndex, endIndex);
	}

}
