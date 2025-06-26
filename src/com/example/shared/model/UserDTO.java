package com.example.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {
    private Long id;
    private String fullName;
    private String email;
    private int age;
    private String address;
    private String gender;

    
    public UserDTO() {}
    
    public UserDTO(String fullName, String email, int age, String address, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.address = address;
        this.gender = gender;
    }

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {	
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	
    
}
