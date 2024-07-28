package com.example.lab8.dao;

import com.example.lab8.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, String> {
}
