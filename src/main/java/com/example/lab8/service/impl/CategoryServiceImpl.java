package com.example.lab8.service.impl;

import com.example.lab8.dao.CategoryDao;
import com.example.lab8.entity.Category;
import com.example.lab8.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao dao;

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }
}
