package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDaoImpl;

    public RoleServiceImpl(RoleDAO roleDaoImpl) {
        this.roleDaoImpl = roleDaoImpl;
    }

    @Override
    public void save(Role role) {
        roleDaoImpl.save(role);
    }

    @Override
    public void delete(int id) {
        roleDaoImpl.delete(id);
    }

    @Override
    public Role show(String role){
        return roleDaoImpl.show(role);
    }

    @Override
    public List<Role> getAll() {
        return roleDaoImpl.getAll();
    }
}
