package com.wipro.demo.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.demo.dto.CustomerUpdateRequest;
import com.wipro.demo.dto.FundTransferRequest;
import com.wipro.demo.dto.UpdateAccountRequest;
import com.wipro.demo.exceptions.ResourceNotFoundException;
import com.wipro.demo.model.Account;
import com.wipro.demo.model.Customer;
import com.wipro.demo.service.AccountService;
import com.wipro.demo.service.CustomerService;

@RestController
public class ControllerClass {
	Logger log = LoggerFactory.getLogger(ControllerClass.class);
	@Autowired
	CustomerService cs;
	@Autowired
	AccountService as;

	/**
	 * Customer CrudOperations
	 * 
	 * @param customer
	 * @param account
	 * @return
	 */

	@PostMapping("/customer")
	public ResponseEntity<Customer> createAccount(@RequestBody Customer customer) {
		Set<Account> accounts = customer.getAccounts();
		for (Account ac : accounts) {
			long acc = ThreadLocalRandom.current().nextLong(100000000000L, 999999999999L);
			ac.setAccountNumber(acc);
			log.info("Created Customer with userId:" + customer.getUserId());
		}
		Customer c = cs.createCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
		// return cs.createCustomer(customer);

	}

	@PutMapping("/customer/{userId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable long userId, @RequestBody CustomerUpdateRequest req)
			throws ResourceNotFoundException {
		Customer c = cs.updateCustomer(userId, req);
		log.info("Update Customer details for userId:" + userId);
		return new ResponseEntity<>(c, HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> findAllCustomers() throws ResourceNotFoundException {
		List<Customer> c = cs.getAllCustomers();
		log.debug("Get All customer details");
		return new ResponseEntity<List<Customer>>(c, HttpStatus.OK);

	}

	@GetMapping("/customer/{userId}")
	public ResponseEntity<Customer> findByCustomerId(@PathVariable Long userId) {
		Customer c = null;
		try {
			c = cs.getCustomerById(userId);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Get Customer Details by userId :" + userId);
		return new ResponseEntity<Customer>(c, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{userId}")
	public void deleteById(@PathVariable Long userId) {
		cs.DeleteCustomerById(userId);
	}

	@DeleteMapping("/delete")
	public void deleteAll() {
		cs.deleteAll();
	}

	/**
	 * Account CrudOperation
	 * 
	 * @param accountNumber
	 * @return
	 */

	@GetMapping("/account/{accountNumber}")
	public Account findByAccountNum(@PathVariable Long accountNumber) {
		return as.findByAccountNum(accountNumber);
	}

	@PutMapping("/account/{accountNo}")
	public Account updateCustomer(@PathVariable long accountNo, @RequestBody UpdateAccountRequest req)
			throws ResourceNotFoundException {
		log.info("Update Account Details");
		return as.updateAccount(accountNo, req);
	}

	@DeleteMapping("/account/{accountNumber}")
	public void deleteAccountByAccountNum(@PathVariable Long accountNumber) throws ResourceNotFoundException {
		log.info("Delete Account by account number" + accountNumber);
		as.deleteCustomerByAccountNum(accountNumber);
	}

	/**
	 * FundTransfer
	 * 
	 * @param req
	 * @return
	 * @throws ResourceNotFoundException
	 */

	@PutMapping("/fundTransfer")
	public Set<Account> fundTransfer1(@RequestBody FundTransferRequest req) {

		Set<Account> accounts = null;
		try {
			accounts = cs.fundTransfer1(req);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

}
