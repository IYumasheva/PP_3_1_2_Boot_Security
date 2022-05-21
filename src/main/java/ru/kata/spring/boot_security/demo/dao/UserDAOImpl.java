package ru.kata.spring.boot_security.demo.dao;

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
    private final PasswordEncoder passwordEncoder;

    public UserDAOImpl(EntityManager em, PasswordEncoder passwordEncoder) {
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        TypedQuery<User> query = em.createQuery("from User user join fetch user.roles where user.id =:id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();

    }

    @Override
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
    }

    @Override
    public void update(User updatedUser){
        if (updatedUser.getPassword().equals(em.find(User.class, updatedUser.getId()).getPassword()) ) {
        } else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        em.merge(updatedUser);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("from User user join fetch user.roles  where user.email =: email", User.class);
        query.setParameter("email", email);
        return Optional.ofNullable(query.getSingleResult());
    }
}
