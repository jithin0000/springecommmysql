package com.jithin.ecommerce.services;

import com.jithin.ecommerce.exception.UserNameAlreadyExistException;
import com.jithin.ecommerce.model.User;
import com.jithin.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User RegisterUser(User newUser){

        try {

            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());
            return userRepository.save(newUser);


        } catch (Exception ex) {
            throw new UserNameAlreadyExistException("user with " + newUser.getUsername() + " already exist");
        }

    }


}
