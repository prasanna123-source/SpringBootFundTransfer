package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account , Integer> {

	Account findbyAccountNumber(Integer fromAccount);	

}
