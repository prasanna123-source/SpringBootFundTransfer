package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.TransactionHistory;
import com.app.model.FundTransfer;
import com.app.model.RegisterDto;
import com.app.model.ResponseDto;
import com.app.service.BankingApplicationService;


@RestController
@RequestMapping("/fundtransfer")
public class fundController {
	
	@Autowired
	private BankingApplicationService bankingService;
	private ResponseDto registerUser;
	private List<TransactionHistory> transfer;

	@PostMapping
	public ResponseEntity<ResponseDto> registerUser(@RequestBody RegisterDto registerDto) {
		registerUser = bankingService.registerUser(registerDto);
		return new ResponseEntity(registerUser,HttpStatus.OK);
	}
	
	
	@PostMapping("/transferfund")
	public ResponseEntity<List<TransactionHistory>> transfer(FundTransfer fundTransfer){
		transfer = bankingService.transfer(fundTransfer);
		return new ResponseEntity(transfer,HttpStatus.OK);
	}

}
