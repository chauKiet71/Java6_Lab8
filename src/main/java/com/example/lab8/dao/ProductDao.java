package com.example.lab8.dao;

import com.example.lab8.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p where p.category.id = ?1")
    List<Product> findByCategoryId(String cid);
}
