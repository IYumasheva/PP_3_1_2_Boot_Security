package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.ExceptionHandler.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userServiceImpl;
    private final RoleService roleServiceImpl;

    public MyRestController(UserService userServiceImpl, RoleService roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }
    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = userServiceImpl.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("THERE IS NO SUCH USER WITH ID = " + id);
        }
        return user;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        setRole(user);
        userServiceImpl.save(user);
        return user;
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") int id) {
        setRole(user);
        userServiceImpl.update(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userServiceImpl.delete(id);
    }

    @GetMapping("/users/current_user")
    public User showCurrentUser(Principal principal) {
        User currentUser = userServiceImpl.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(""));
        return currentUser;
    }

    private void setRole(User user) {
        List<Role> list = new ArrayList<>();
        for (Role role : user.getRoles()) {
            list.add(role);
        }
        user.setRoles(list);
    }
}
