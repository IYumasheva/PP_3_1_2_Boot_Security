package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private final EntityManager em;

    public RoleDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Role role) {
        em.persist(role);
    }

    @Override
    public void delete(int id) {
        Role role = em.find(Role.class, id);
        em.remove(role);
    }
    @Override
    public Role show(String role) {
        TypedQuery<Role> query = em.createQuery("from Role role where role.name =: role", Role.class);
        query.setParameter("role", role);
        return query.getSingleResult();
    }
}
