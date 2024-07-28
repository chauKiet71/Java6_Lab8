package com.example.lab8.rest.controller;

import com.example.lab8.entity.Product;
import com.example.lab8.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/product")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping("{id}")
    public Product getProduct(@PathVariable("id") Integer id) {
        return productService.findById(id);
    }
}
