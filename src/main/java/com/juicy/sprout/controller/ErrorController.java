package com.juicy.sprout.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping("/error")
    public String getError() {return "OOPS, You broke it...";}
}
