package com.example.lab8.service.impl;

import com.example.lab8.dao.ProductDao;
import com.example.lab8.entity.Product;
import com.example.lab8.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id).get();
    }

    @Override
    public List<Product> findByCategoryId(String cid) {
        return productDao.findByCategoryId(cid);
    }
}
