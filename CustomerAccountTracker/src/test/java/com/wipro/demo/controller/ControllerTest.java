package com.wipro.demo.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.doNothing;

import java.util.List;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wipro.demo.dto.CustomerUpdateRequest;
import com.wipro.demo.dto.FundTransferRequest;
import com.wipro.demo.exceptions.ResourceNotFoundException;
import com.wipro.demo.model.Account;
import com.wipro.demo.model.Customer;
import com.wipro.demo.service.AccountService;
import com.wipro.demo.service.CustomerService;

import ch.qos.logback.core.encoder.ByteArrayUtil;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
	@InjectMocks
	ControllerClass controller;
	@Mock
	CustomerService cs;
	@Mock
	AccountService as;

	@Test
	public void createAccountTest() {
		Customer c = buildCustomerData();
		Mockito.when(cs.createCustomer(c)).thenReturn(c);
		ResponseEntity<Customer> response = controller.createAccount(c);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertEquals(response.getBody().getName(), c.getName());
		assertEquals(response.getBody().getAccounts(), c.getAccounts());
	}

//	@Test
//	public void updateCustomerTest() throws ResourceNotFoundException {
//		Customer c = buildCustomerData();
//		Mockito.when(cs.updateCustomer(1, c)).thenReturn(c);
//		ResponseEntity<Customer> response = controller.updateCustomer(1, c);
//		assertEquals(response.getBody().getName(), c.getName());
//		// return cs.updateCustomer(userId, customer);
//	}

	@Test
	public void updateCustomer1Test() throws ResourceNotFoundException {
		Customer c = buildCustomerData();
		CustomerUpdateRequest cr = new CustomerUpdateRequest();
		Mockito.when(cs.updateCustomer(1, cr)).thenReturn(c);
		ResponseEntity<Customer> response = controller.updateCustomer(1, cr);
		assertEquals(response.getBody().getName(), c.getName());
	}

	@Test
	public void getAllCustomersTest() throws ResourceNotFoundException {
		List<Customer> c = List.of(buildCustomerData());
//		Mockito.when(cs.getAllCustomers()).thenReturn(c);
//		ResponseEntity<Customer> response = controller.findAllCustomers();
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
		// assertEquals(response.getBody().getName(), c.get(0).getName());
		// List<Customer> c = new ArrayList<>();
		Mockito.when(cs.getAllCustomers()).thenReturn(c);
		ResponseEntity<List<Customer>> response = controller.findAllCustomers();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().get(0).getName(), c.get(0).getName());

	}

	@Test
	public void findByCustomerIdTest() throws ResourceNotFoundException {
		Customer c = buildCustomerData();
		Mockito.when(cs.getCustomerById(1l)).thenReturn(c);
		ResponseEntity<Customer> response = controller.findByCustomerId(1l);
		assertEquals(response.getBody().getName(), c.getName());
	}

	@Test
	public void deleteByIdTest() throws ResourceNotFoundException {
		cs.DeleteCustomerById(1l);
		assertNull(cs.getCustomerById(1l));
	}

	@Test
	public void deleteAllTest() {
		doNothing().when(cs).deleteAll();
		cs.deleteAll();
	}

	@Test
	public void findByAccountNumTest() throws ResourceNotFoundException {
		Account c = buildCustomerData().getAccounts().iterator().next();
		Mockito.when(as.findByAccountNum(1l)).thenReturn(c);
		Account response = controller.findByAccountNum(1l);
		assertEquals(response.getName(), c.getName());
		// return as.findByAccountNum(accountNumber);
	}

	@Test
	public void fundTransferTest1() throws ResourceNotFoundException {
		FundTransferRequest req = new FundTransferRequest();
		req.setFromAcc((long) 11);
		req.setToAcc((long) 12);
		req.setTransferAmount((long) 3000);
		Set<Account> accounts = buildCustomerData().getAccounts();
		Mockito.when(cs.fundTransfer1(req)).thenReturn(accounts);
		Set<Account> response = controller.fundTransfer1(req);
		assertEquals(response.iterator().next().getBalanceAmount(), accounts.iterator().next().getBalanceAmount());
	}

	private Customer buildCustomerData() {
		Customer c = new Customer();
		Account ac = new Account();
		ac.setAccountNumber(11);
		ac.setAccountType("Savings account");
		ac.setBalanceAmount(10000);
		ac.setName("Hdfc");
		Account ac1 = new Account();
		ac1.setAccountNumber(22);
		ac1.setAccountType("Current account");
		ac1.setBalanceAmount(10000);
		ac1.setName("Hdfc");
		c.setAadharNo("537368336323");
		c.setContact("463746333");
		c.setEmail("Hari@gmail.com");
		c.setName("Hari");
		c.setGender("Male");
		c.setAccounts(Set.of(ac, ac1));
		c.setUserId(1);
		return c;
	}
}
