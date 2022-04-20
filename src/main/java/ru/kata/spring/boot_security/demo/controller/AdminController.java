package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

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
    public String index(Model model){
        model.addAttribute("users", userServiceImpl.index());
        return "index";
    }

    @GetMapping("/new")
    public String newPerson( @ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping
    public String create(@RequestParam(required = false) String confirm1,
                         @RequestParam(required = false) String confirm2,
                         @ModelAttribute("user") User user) {
        setRole(confirm1, confirm2, user);
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImpl.show(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@RequestParam(required = false) String confirm1,
                         @RequestParam(required = false) String confirm2,
                         @ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        setRole(confirm1, confirm2, user);
        userServiceImpl.update(user);
        return "redirect:/admin";
    }

    private void setRole(String confirm1, String confirm2, User user) {
        List<Role> list = new ArrayList<>();
        if (confirm1 != null && confirm2 != null) {
            list = List.of(roleServiceImpl.show("ADMIN"),
                    roleServiceImpl.show("USER"));
        } else if (confirm1 != null) {
            list = List.of(roleServiceImpl.show("ADMIN"));
        } else if (confirm2 != null) {
            list = List.of(roleServiceImpl.show("USER"));
        }
        user.setRoles(list);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}
