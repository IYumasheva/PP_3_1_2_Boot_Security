package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private final EntityManager em;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> index() {
        TypedQuery<User> query = em.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User show(int id) {
        User user = em.find(User.class, id);
        return user;
    }

    @Override
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
    }

    @Override
    public void update(User updatedUser){
        em.merge(updatedUser);
    }

    @Override
    public void delete(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("from User user where user.email =: email", User.class);
        query.setParameter("email", email);
        return Optional.ofNullable(query.getSingleResult());
    }
}
