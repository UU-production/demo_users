package com.uu_demo.controllers;


import com.uu_demo.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logout", produces = "application/json")
public class LogOutController {

    private JwtProvider jwtProvider;

    @Autowired
    public LogOutController(JwtProvider jwtProvider) {
       this.jwtProvider = jwtProvider;
    }


    @GetMapping
    public void makeLogout() {
        jwtProvider.logout();
    }
}
