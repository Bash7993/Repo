package com.wipro.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.demo.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	// Optional<Account> findByAccountNumber(long accountNumber);

	// Account deleteByAccountNumber(Long accountNumber);
//
//	Account findById(Long accountNumber);

}
