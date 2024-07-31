package com.example.lab8.controller;

import com.example.lab8.entity.Product;
import com.example.lab8.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    ProductService productService;

    @RequestMapping("/product/list")
    public String list(Model model, @RequestParam("cid") Optional<String> cid) {
        if (cid.isPresent()) {
            List<Product> list = productService.findByCategoryId(cid.get());
            model.addAttribute("items", list);
            System.out.println(req.getRemoteUser());
        }else {
            List<Product> list = productService.findAll();
            model.addAttribute("items", list);
            System.out.println(req.getRemoteUser());
        }
        return "product/list";
    }

    @RequestMapping("/product/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Product item = productService.findById(id);
        model.addAttribute("item", item);
        return "product/detail";
    }
}
