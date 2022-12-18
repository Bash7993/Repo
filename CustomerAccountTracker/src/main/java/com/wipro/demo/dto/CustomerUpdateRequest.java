package com.wipro.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerUpdateRequest {
	private String name;

	private String gender;

	private String email;

	private String contact;

	private String aadharNo;
	private String accountType;
	private double balanceAmount;
	private String accountName;



}
