package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDaoImpl;

    public UserServiceImpl(UserDAO userDaoHib) {
        this.userDaoImpl = userDaoHib;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDaoImpl.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id){
        return userDaoImpl.getUser(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        userDaoImpl.save(user);
    }

    @Override
    @Transactional
    public void update(User updatedUser){ userDaoImpl.update(updatedUser); }

    @Override
    @Transactional
    public void delete(int id) {
        userDaoImpl.delete(id);}

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return userDaoImpl.findByEmail(email);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDaoImpl.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
