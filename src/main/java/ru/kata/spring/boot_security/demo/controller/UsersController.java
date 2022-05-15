package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userServiceImpl;

    public UsersController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public String show(Model model, Principal principal){
        model.addAttribute("user", userServiceImpl.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("")));
        return "user";
    }

}
