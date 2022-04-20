package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> index();

    User show(int id);

    void save(User user);

    void update(User updatedUser);

    void delete(int id);

    Optional<User> findByEmail(String email);
}
