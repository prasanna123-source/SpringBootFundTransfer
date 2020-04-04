package com.app.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TransactionHistory {
	
	
	public TransactionHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransactionHistory(long accountNumber2, Float balance2, String accountType2, Timestamp timestamp) {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private long accountNumber;
	Float balance;
	String accountType;
	String message;
	public void setMessage(String string) {
		// TODO Auto-generated method stub
		
	}
	

}
