package com.app.model;

public class FundTransfer {
	Integer fromAccount;
	int toAccount;
	double amount;
	public Integer getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Integer fromAccount) {
		this.fromAccount = fromAccount;
	}
	public int getToAccount() {
		return toAccount;
	}
	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
