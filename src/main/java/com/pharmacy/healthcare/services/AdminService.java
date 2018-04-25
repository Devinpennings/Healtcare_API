package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Admin;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(long id, Admin admin) {
        Admin currentUser = (Admin) userRepository.findOne(id);

        if (currentUser == null) {
            return null;
        } else {
            currentUser.setUsername(admin.getUsername());
            currentUser.setFirstname(admin.getFirstname());
            currentUser.setLastname(admin.getLastname());
            userRepository.save(currentUser);
            return currentUser;
        }
    }
}
