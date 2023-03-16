package com.luisscudeler.userdep.config;

import com.luisscudeler.userdep.entities.Department;
import com.luisscudeler.userdep.entities.User;
import com.luisscudeler.userdep.repository.DepartmentRepository;
import com.luisscudeler.userdep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Initialization implements CommandLineRunner {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public void run(String... args) throws Exception {

        Department d1 = new Department(null, "Gestão");
        Department d2 = new Department(null, "Informatica");

        departmentRepository.saveAll(Arrays.asList(d1,d2));

        User u1 = new User(null, "Maria", "maria@gmail.com", d1);
        User u2 = new User(null, "Bob", "bob@gmail.com", d1);
        User u3 = new User(null, "Carlos", "caca@gmail.com", d2);
        User u4 = new User(null, "Alex", "alm@gmail.com", d2);

        userRepository.saveAll((Arrays.asList(u1,u2,u3,u4)));


    }
}
