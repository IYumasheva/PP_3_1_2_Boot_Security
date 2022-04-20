package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> index();

    User show(int id);

    void save(User user);

    void update(User updatedUser);

    void delete(int id);

    Optional<User> findByEmail(String email);


}
