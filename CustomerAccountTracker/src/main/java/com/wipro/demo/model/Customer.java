package com.wipro.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
//	@Column(name = "AccountNumber")
//	long accountNumber;
	@Column(name = "Name")
	private String name;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "Email")
	private String email;
	@Column(name = "Contact")
	private String contact;
	@Column(name = "AadharNo")
	private String aadharNo;
	@OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "userId")
	// @JsonIgnore
	private Set<Account> accounts;




}
