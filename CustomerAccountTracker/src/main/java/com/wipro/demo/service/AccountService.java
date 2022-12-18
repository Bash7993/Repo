package com.wipro.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.demo.dao.AccountRepository;
import com.wipro.demo.dto.UpdateAccountRequest;
import com.wipro.demo.exceptions.ResourceNotFoundException;
import com.wipro.demo.model.Account;

@Service
public class AccountService {
	Logger log = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private AccountRepository accRepo;


	public Account findByAccountNum(Long accountNumber) {
		return accRepo.findById(accountNumber).get();
	}

	public Account getAccountDetailsByAccNum(Long accountNumber) throws ResourceNotFoundException {
		Optional<Account> account = accRepo.findById(accountNumber);
		if (account.isPresent())
			return account.get();
		else
			throw new ResourceNotFoundException(
					"No customer record is exist for given account number:" + accountNumber);

	}
	public Account updateAccount(long accountNo, UpdateAccountRequest req) throws ResourceNotFoundException {
		Account ac = accRepo.findById(accountNo).get();
		if (ac != null) {
			ac.setName(req.getAccountName());
			ac.setAccountType(req.getAccountType());
			ac.setBalanceAmount(req.getBalanceAmount());
			log.info("Updated Account Details");
		} else
			throw new ResourceNotFoundException("No Account Record is found!");
		return accRepo.save(ac);
	}

	public void deleteCustomerByAccountNum(Long accountNumber) throws ResourceNotFoundException {
		Optional<Account> ac = accRepo.findById(accountNumber);
		if (!ac.isEmpty()) {
			log.info("Delete Account ");
			accRepo.deleteById(accountNumber);
		} else {
			throw new ResourceNotFoundException("Account not found with Account No:" + accountNumber);
		}
	}
}
