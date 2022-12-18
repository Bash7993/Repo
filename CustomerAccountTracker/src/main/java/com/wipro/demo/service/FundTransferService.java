//package com.wipro.demo.service;
//
//import java.util.Optional;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.wipro.demo.dao.AccountRepository;
//import com.wipro.demo.dao.FundTransferRepository;
//import com.wipro.demo.model.Account;
//import com.wipro.demo.model.Customer;
//import com.wipro.demo.model.FundTransfers;
//
//@Service
//public class FundTransferService {
//	@Autowired
//	FundTransferRepository fundRepo;
//	@Autowired
//	AccountRepository accRepo;
//
//	public String amountTransfer(Long accountNumber, FundTransfers f, Account ac) {
//		Optional<Account> transaction = accRepo.findById(accountNumber);
//		String result = "";
//		if (transaction.isPresent()) {
//			f.setAmount(f.getAmount());
//			f.setBeneificaryAccountNumber(f.getBeneificaryAccountNumber());
//			f.setBenificiaryName(f.getBenificiaryName());
//			Account acc = transaction.get();
//			if (acc.getBalanceAmount() > f.getAmount()) {
//				f.setBalanceAmount(acc.getBalanceAmount() - f.getAmount());
//				fundRepo.save(f);
//				acc.setBalanceAmount(acc.getBalanceAmount() - f.getAmount());
//				accRepo.save(acc);
//				result += "Transaction Succesfull";
//			} else {
//				result += "Max amount available for transaction is" + acc.getBalanceAmount();
//			}
//              return result;
//		}else {
//			return "Account Number Not Found";
//		}
//
//	}
//
//	
//	
//}
