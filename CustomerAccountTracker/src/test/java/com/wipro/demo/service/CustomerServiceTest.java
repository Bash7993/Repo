package com.wipro.demo.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.transaction.TestTransaction;

import com.wipro.demo.dao.AccountRepository;
import com.wipro.demo.dao.CustomerRepository;
import com.wipro.demo.dto.CustomerUpdateRequest;
import com.wipro.demo.dto.FundTransferRequest;
import com.wipro.demo.exceptions.ResourceNotFoundException;
import com.wipro.demo.model.Account;
import com.wipro.demo.model.Customer;

import net.bytebuddy.implementation.bind.annotation.Empty;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	@InjectMocks
	CustomerService cs;
	@Mock
	CustomerRepository cr;
	@Mock
	AccountRepository ar;

	@Test
	public void getAllCustomersTest() throws ResourceNotFoundException {
		List<Customer> list = buildCustomerData();
		Mockito.when(cr.findAll()).thenReturn(list);
		List<Customer> response = cs.getAllCustomers();
		// when(cr.findAll()).thenReturn(list);
		// List<Customer> cusList = cs.getAllCustomers();

		assertEquals(3, response.size());
		assertEquals(list.get(0).getName(), response.get(0).getName());
		assertTrue("Male".equalsIgnoreCase(response.get(0).getGender()));
	}

	@Test
	public void createCustomerTest() {
		List<Customer> list = buildCustomerData();
		Customer c = list.get(0);
		Mockito.when(cr.save(c)).thenReturn(c);
		Customer response = cs.createCustomer(c);
		// verify(cr, times(3)).save(response);
		assertEquals(c.getEmail(), response.getEmail());
	}

	@Test
	public void getCustomerByIdTest() {
		cr.save(new Customer(1, "Harsha", "Male", "Harsha@gmail.com", "8844846448", "546325736332", null));

		when(cr.findById((long) 1)).thenReturn(
				Optional.of(new Customer(1, "Harsha", "Male", "Harsha@gmail.com", "8844846448", "546325736332", null)));

		Customer cust = null;
		try {
			cust = cs.getCustomerById((long) 1);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1, cust.getUserId());
	}

	@Test
	public void getCustomerByIdTest1() {
		List<Customer> list = buildCustomerData();
		// Customer c=list.get(0);
		Optional<Customer> cust = Optional.of(list.get(0));
		Mockito.when(cr.findById((long) 1)).thenReturn(cust);

		Customer response = null;
		try {
			response = cs.getCustomerById((long) 1);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(cust.get().getAadharNo(), response.getAadharNo());
	}

	@Test
	public void deleteCustomerTest() {
//		cr.save(new Customer(1, "Harsha", "Male", "Harsha@gmail.com", "8844846448", "546325736332", null));
//		cr.deleteById((long) 1);
		// verify(cr, times(1)).deleteById((long) 1);
		List<Customer> list = buildCustomerData();
		Optional<Customer> cust = Optional.of(list.get(0));
		Mockito.when(cr.findById((long) 1)).thenReturn(cust);
		doNothing().when(cr).deleteById((long) 1);
		String response = cs.DeleteCustomerById((long) 1);
		// assertEquals("Customer Deleted", response);
		assertTrue(response.contains("Customer Deleted"));
	}

	@Test
	public void deleteAllTest() {
		doNothing().when(cr).deleteAll();
		cs.deleteAll();
		// assertTrue(response.contains("Customer Deleted"));

	}

	@Test
	public void updateCustomerTest() throws ResourceNotFoundException {

		List<Customer> list = buildCustomerData();
		CustomerUpdateRequest req = new CustomerUpdateRequest();
		req.setName("Ramesh");
		Optional<Customer> cust = Optional.of(list.get(0));
		Mockito.when(cr.findById((long) 1)).thenReturn(cust);

		Customer cust1 = cust.get();

		Mockito.when(cr.save(cust1)).thenReturn(cust1);
		Customer response = cs.updateCustomer(1, req);
		assertEquals(req.getName(), response.getName());
	}

	@Test
	public void fundTransferTest1() throws ResourceNotFoundException {
		FundTransferRequest req = new FundTransferRequest();
		req.setFromAcc((long) 11);
		req.setToAcc((long) 12);
		req.setTransferAmount((long) 3000);

		Account fromAccount = new Account();
		fromAccount.setAccountNumber(11L);
		fromAccount.setAccountType("Saving Account");
		fromAccount.setBalanceAmount(100000);
		fromAccount.setName("HDFC");

		Account toAccount = new Account();
		toAccount.setAccountNumber(12L);
		toAccount.setAccountType("Current Account");
		toAccount.setBalanceAmount(100000);
		toAccount.setName("HDFC");

		Mockito.when(ar.findById((long) 11)).thenReturn(Optional.of(fromAccount));
		Mockito.when(ar.findById((long) 12)).thenReturn(Optional.of(toAccount));
		Mockito.when(ar.save(fromAccount)).thenReturn(fromAccount);
		Mockito.when(ar.save(toAccount)).thenReturn(toAccount);

		Set<Account> response = cs.fundTransfer1(req);
		Iterator<Account> iterator = response.iterator();
		assertNotEquals(iterator.next().getBalanceAmount(), iterator.next().getBalanceAmount());

	}

	private List<Customer> buildCustomerData() {
		List<Customer> list = new ArrayList<Customer>();
		Set<Account> accounts = new HashSet<Account>();
		Account account1 = new Account(324, "saving", 345678, "HDFC");
		Account account2 = new Account(311, "current", 3478, "HDFC");
		accounts.add(account1);
		accounts.add(account2);
		Customer customer1 = new Customer(1, "Harsha", "Male", "Harsha@gmail.com", "8844846448", "546325736332",
				accounts);
		Customer customer2 = new Customer(2, "Harish", "Male", "Harish@gmail.com", "9844846448", "646335536332",
				accounts);
		Customer customer3 = new Customer(3, "shiva", "Male", "shiva@gmail.com", "9744846448", "846334736332",
				accounts);
		list.add(customer1);
		list.add(customer2);
		list.add(customer3);
		return list;
	}
}
