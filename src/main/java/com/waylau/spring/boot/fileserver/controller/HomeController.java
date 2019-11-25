package com.waylau.spring.boot.fileserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600) // 允许所有域名访问
@Controller
public class HomeController {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/homepage")
    public String homepage(ModelMap model){
        return "homepage";
    }
}
