package com.wipro.demo.model;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Account {
	@Id
	private long accountNumber;

	private String accountType;
	private double balanceAmount;
	private String name;


//
//	private long generatedRandom(int length) {
//		// TODO Auto-generated method stub
//		Random r = new Random();
//		char[] digits = new char[length];
//		digits[0] = (char) (r.nextInt(9) + '1');
//		for (int i = 1; i < length; i++) {
//			digits[i] = (char) (r.nextInt(10) + '1');
//		}
//		return Long.parseLong(new String(digits));
//	}

}
