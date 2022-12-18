package com.wipro.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UpdateAccountRequest {

	private String accountType;
	private double balanceAmount;
	private String accountName;
	
}
