package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);
    void delete(int id);
    Role show(String role);
    List<Role> getAll();
}
