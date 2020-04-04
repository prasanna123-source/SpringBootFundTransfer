package com.app.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;
import javax.xml.bind.DataBindingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.entity.Account;
import com.app.entity.TransactionHistory;
import com.app.entity.User;
import com.app.exception.TransactionFailed;
import com.app.exception.UserNotFoundException;
import com.app.model.FundTransfer;
import com.app.model.RegisterDto;
import com.app.model.ResponseDto;
import com.app.repository.AccountRepository;
import com.app.repository.PaymentOperationRepository;
import com.app.repository.UserRepository;
import com.app.service.BankingApplicationService;

@Service
@Transactional
public class BankingApplicationserviceImpl implements BankingApplicationService{
	private static final Logger logger = LoggerFactory.getLogger(BankingApplicationserviceImpl.class);
	
	@Autowired
	UserRepository userRepo;
	
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	TransactionHistory transactionRepo;
	
	

	public ResponseDto registerUser(RegisterDto registerDto) {
		logger.info("Inside register of BankingApplicationserviceImpl");
		Account accountInfo=null;
		User userData=new User();		
	   
		if (!registerDto.equals(null)) {
			userData.setUserName(registerDto.getUserName());  
			userData.setAge(registerDto.getAge());
			userData.setAdhar(registerDto.getAdhar());
			userData.setGender(registerDto.getGender());
			userData.setContactNumber(registerDto.getContactNumber());
			userData.setPan(registerDto.getPan());
						
	    }
		User user=userRepo.save(user);
		if(Objects.nonNull(user) && Objects.nonNull(user.getUserId()){
			Account account=new Account();
			account.setAccountNumber(accountNumber());			
			account.setBalance(registerDto.getBalance());
			account.setAccountType(registerDto.getAccountType());
			account.setUser(user);
			accountInfo=accountRepo.save(account);		
		}
		if(Objects.isNull(accountInfo) && Objects.isNull(accountInfo.getAccountNumber()){
			logger.error("Account Data insertion error");
		}
		else {
			logger.error("User Data insertion error");
		}
		
		TransactionHistory transaction=new TransactionHistory(
				accountInfo.getAccountNumber(),accountInfo.getBalance(),accountInfo.getAccountType(),new Timestamp(System.currentTimeMillis())
				);
		transaction.setMessage(accountInfo.getBalance()+" has been Credited to "+accountInfo.getAccountNumber());
		transactionRepo.save(transaction);
		
		ResponseDto responseDto=new ResponseDto();
		
		responseDto.setMessage("REGISTERED SUCCESSFULLY");
		responseDto.setStatusCode(HttpStatus.OK.value());
		return responseDto;    	
	
	}
	
	private long accountNumber() {
		Random generator = new Random(System.currentTimeMillis());
		long acctNum = generator.nextInt(1000) + 9999;  
		return acctNum;
	}
	
	public User getUserById(int userId) {
		Optional<User> user=userRepo.findById(userId);
		if(!user.isPresent()) {
			throw new UserNotFoundException("USer not found");
		}
		return user.get();		
	}
	
	@Transactional
	public List<TransactionHistory> transfer(FundTransfer fundTransfer) {
       logger.info("Inside transfer method");
        int fromAcct=fundTransfer.getFromAccount();
		int toAcct= fundTransfer.getToAccount();
		double transferAmount=fundTransfer.getAmount();		
        Account fromAccount = accountRepo.findbyAccountNumber(fromAcct);
		Account toAccount = accountRepo.findbyAccountNumber(toAcct);		
		List<TransactionHistory> transactionList=new ArrayList<TransactionHistory>();
		if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())){
			throw new RuntimeException(
					"Impossible operation: account id must be different");	
		}
		if(fromAccount.getBalance()>transferAmount) {
			throw new LowBalanceException("INSUFFICIENT_BALANCE");
		}
		if(fromAccount.getBalance()>transferAmount) {             
			fromAccount.setBalance(fromAccount.getBalance()-fundTransfer.getAmount());
			toAccount.setBalance(toAccount.getBalance()+fundTransfer.getAmount());
			
			accountRepo.save(toAccount);
			accountRepo.save(fromAccount);
			
			
			TransactionHistory fromTransaction = new TransactionHistory(transferAmount,fromAccount.getAccountNumber(),new Timestamp(System.currentTimeMillis()));
			fromTransaction.setMessage(transferAmount+" has been Debited from "+fromAccount.getAccountNumber());
			TransactionHistory sourceAccount=transactionRepo.save(fromTransaction);
			
			if(Objects.isNull(fromTransaction)) {
				throw new TransactionFailed("Trnsaction failed ");
			}
			
			TransactionHistory toTransaction = new TransactionHistory(transferAmount,toAccount.getAccountNumber(),new Timestamp(System.currentTimeMillis()));
			fromTransaction.setMessage(transferAmount+" has been credited from "+toAccount.getAccountNumber());
			TransactionHistory destinationAccount=transactionRepo.save(toTransaction);
			
			if(Objects.isNull(toTransaction)) {
				throw new TransactionFailed("Trnsaction failed ");
			}
			transactionList.add(sourceAccount);
			transactionList.add(toTransaction);
			
			return transactionList;
		} 
		
		
		
	}


	
	
}
