package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userServiceImpl;
    private final RoleService roleServiceImpl;

    public AdminController(UserService userServiceImpl, RoleService roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String index(Model model, Principal principal){
        model.addAttribute("user", userServiceImpl.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("")));
        model.addAttribute("users", userServiceImpl.getAllUsers());
        model.addAttribute("rolesList", roleServiceImpl.getAll());
        model.addAttribute("newUser", new User());
        return "admin";
    }

//    @PostMapping("/save")
//    public String create(@RequestParam String[] roles,
//                         @ModelAttribute("newUser") User user) {
//        setRole(roles, user);
//        userServiceImpl.save(user);
//        System.out.println("Это пост запрос");
//        return "redirect:/admin";
//    }
//
//    @PatchMapping("/update/{id}")
//    public String update(@RequestParam String[] roles,
//                         @ModelAttribute("user") User user,
//                         @PathVariable("id") int id) {
//        setRole(roles, user);
//        userServiceImpl.update(user);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable("id") int id) {
//        userServiceImpl.delete(id);
//        return "redirect:/admin";
//    }
//
//    private void setRole(String[] roles, User user) {
//        List<Role> list = new ArrayList<>();
//        for (String role : roles) {
//            list.add(roleServiceImpl.show(role));
//        }
//        user.setRoles(list);
//    }
}
