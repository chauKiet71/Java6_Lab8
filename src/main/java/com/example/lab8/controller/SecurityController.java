package com.example.lab8.controller;

import com.example.lab8.dao.AccountDao;
import com.example.lab8.service.AcountService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

    @Autowired
    HttpServletRequest req;

    @ModelAttribute("remoteUser")
    public String getRemoteUser() {
        return req.getRemoteUser();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return req.isUserInRole("ADMIN");
    }

    @Autowired
    AcountService accountService;


    @Autowired
    AccountDao dao;

    @ModelAttribute("isDire")
    public boolean isDire(Model model) {
        System.out.println(req.isUserInRole("DIRE"));
        return req.isUserInRole("DIRE");
    }

    @RequestMapping("/security/login/form")
    public String loginForm(Model model) {
        model.addAttribute("message", "Vui lòng đăng nhập");
        return "security/login";
    }

    @RequestMapping("/security/login/success")
    public String success(Model model) {
        model.addAttribute("message", "Đăng nhập thành công");
        return "security/login";
    }

    @RequestMapping("/security/login/error")
    public String error(Model model) {
        model.addAttribute("message", "Sai thông tin đăng nhập");
        return "security/login";
    }

    @RequestMapping("/security/unaothoried")
    public String unaothoried(Model model) {
        model.addAttribute("message", "Không có quyền truy cập");
        return "security/login";
    }

    @RequestMapping("/security/logoff/success")
    public String logoff(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "security/login";
    }
}
