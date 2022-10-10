package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class indexController {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @GetMapping({"","/"})
    public String index(){
        //머스테치 기본폴더 src/main.resource/
        //뷰리졸버 설정 : templates(prefix), .mustache(suffix) 생략가능
        return "index";//
    }
    @GetMapping("/user")
    public @ResponseBody String User(){
        return "user";
    }
    @GetMapping("/admin")
    public String Admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String Manager(){
        return "manager";
    }
    @GetMapping("/user/loginView")
    public String Login(){
        return "loginForm";
    }
    @GetMapping("/join")
    public String Join(){
        return "joinForm";
    }
    @PostMapping("/join")
    public String Join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/user/login";
    }
}

