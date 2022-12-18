package com.wipro.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.demo.dao.AccountRepository;
import com.wipro.demo.dao.CustomerRepository;
import com.wipro.demo.dto.CustomerUpdateRequest;
import com.wipro.demo.dto.FundTransferRequest;
import com.wipro.demo.exceptions.ResourceNotFoundException;
import com.wipro.demo.model.Account;
import com.wipro.demo.model.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private AccountRepository accRepo;


	public Customer createCustomer(Customer c) {
		return custRepo.save(c);
	}



	public Customer updateCustomer(long userId, CustomerUpdateRequest req) throws ResourceNotFoundException {
		Customer c = custRepo.findById(userId).get();
		if (c != null) {
			c.setName(req.getName());
			c.setGender(req.getGender());
			c.setContact(req.getContact());
			c.setEmail(req.getEmail());
			c.setAadharNo(req.getAadharNo());
			custRepo.save(c);
			return c;
		} else

			throw new ResourceNotFoundException("No Customer Record is found!");

	}

	public List<Customer> getAllCustomers() throws ResourceNotFoundException {
		List<Customer> list = custRepo.findAll();
		if (list.isEmpty())
			throw new ResourceNotFoundException("No Customer Record is found!");

		return list;
	}

	public Customer getCustomerById(Long userId) throws ResourceNotFoundException {
		// return custRepo.findById(userId).get();
		Customer c = custRepo.findById(userId).get();
		if (c == null)
			throw new ResourceNotFoundException("No Record Found on this:" + userId);

		return c;
	}

	public Customer getCustomerByAccountNum(Long accountNumber) throws ResourceNotFoundException {

		return custRepo.findById(accountNumber).get();
	}

	public String DeleteCustomerById(Long userId) {
		Optional<Customer> customer = custRepo.findById(userId);
		if (customer.isPresent()) {
			custRepo.deleteById(userId);
			return "Customer Deleted";

		} else {
			return "No Customer record exist for given userId:" + userId;
		}
	}

	public void deleteAll() {
		custRepo.deleteAll();
	}

	public Set<Account> fundTransfer1(FundTransferRequest req) throws ResourceNotFoundException {
		Account fromAc = accRepo.findById(req.getFromAcc()).get();
		Account toAc = accRepo.findById(req.getToAcc()).get();
		if (fromAc.getBalanceAmount() > req.getTransferAmount()) {
			double fromAcBal = fromAc.getBalanceAmount() - req.getTransferAmount();
			double toAcBal = toAc.getBalanceAmount() + req.getTransferAmount();
			fromAc.setBalanceAmount(fromAcBal);
			toAc.setBalanceAmount(toAcBal);
		} else {
			throw new ResourceNotFoundException("Transaction not allowed");
		}
//        Account frAc = accRepo.save(fromAc);
//        Account trAc =accRepo.save(toAc);
		accRepo.save(fromAc);
		accRepo.save(toAc);

		Set<Account> transAccs = new HashSet<>();
		transAccs.add(fromAc);
		transAccs.add(toAc);

		return transAccs;

	}
}
