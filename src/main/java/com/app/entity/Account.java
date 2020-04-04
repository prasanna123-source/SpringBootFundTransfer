package com.app.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_number")
	private int accountNumber;	
	private double balance;	
	private String accountType;
	private Date createdDate;

	
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@ManyToOne(optional = false)
	@JoinColumn(name="user_id")
	private User user;


	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double d) {
		this.balance = d;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}	
	
	
}
