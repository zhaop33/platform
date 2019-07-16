package com.zhao.platform.controller;

import com.zhao.platform.cache.InvalidTokenCache;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @GetMapping("getInfo")
    @ResponseBody
    public Authentication getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
    @RequestMapping(value = "get",method = RequestMethod.GET)
    @ResponseBody
    public void get(){
        try {
            InvalidTokenCache.test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}