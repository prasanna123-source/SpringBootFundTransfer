package com.app.service;

import java.util.List;

import com.app.entity.TransactionHistory;
import com.app.entity.User;
import com.app.model.FundTransfer;
import com.app.model.RegisterDto;
import com.app.model.ResponseDto;

public interface BankingApplicationService {
	
	public ResponseDto registerUser(RegisterDto registerDto);

	List<TransactionHistory> transfer(FundTransfer fundTransfer);
	
}
