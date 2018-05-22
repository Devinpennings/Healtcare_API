package com.pharmacy.healthcare;

import com.pharmacy.healthcare.domain.Role;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.RoleRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

        boolean alreadySetup = false;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;




    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;


        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");
        alreadySetup = true;

        //dit is om te testen of het werkt
        Role adminRole = roleRepository.findByName("ADMIN");
        User user1 = userRepository.findOne(1L);
        user1.setRoles(Arrays.asList(adminRole));


    }


    @Transactional
    private Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }


}
