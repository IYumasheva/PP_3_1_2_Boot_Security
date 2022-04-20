package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {

		ApplicationContext context =
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);


		UserService userServiceImpl = context.getBean(UserService.class);
		RoleService roleServiceImpl = context.getBean(RoleService.class);
		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

		Role role1 = new Role("ADMIN");
		Role role2 = new Role("USER");
		roleServiceImpl.save(role1);
		roleServiceImpl.save(role2);

		User admin = new User("Admin", "Admin","25", "admin@mail.ru", "password");
		admin.setRoles(List.of(role1));
		userServiceImpl.save(admin);

		User user = new User("User", "User","25", "user@mail.ru", "password");
		user.setRoles(List.of(role2));
		userServiceImpl.save(user);

	}

}
