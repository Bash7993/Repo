package com.wipro.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FundTransferRequest {
	Long fromAcc;
	Long toAcc;
	Long transferAmount;

}
