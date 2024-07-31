package com.example.lab8.service.impl;

import com.example.lab8.dao.AccountDao;
import com.example.lab8.entity.Account;
import com.example.lab8.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcountServiceImpl implements AcountService {

    @Autowired
    AccountDao dao;

    @Override
    public Account findById(String username) {
        return dao.findById(username).get();
    }
}
