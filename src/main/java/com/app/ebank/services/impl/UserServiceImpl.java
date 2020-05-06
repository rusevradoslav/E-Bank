package com.app.ebank.services.impl;

import com.app.ebank.domain.bindingModels.UserBindingModel;
import com.app.ebank.domain.entities.Role;
import com.app.ebank.domain.entities.User;
import com.app.ebank.repositories.UserRepository;
import com.app.ebank.services.RoleService;
import com.app.ebank.services.UserService;
import com.app.ebank.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ValidationUtil validationUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.validationUtil = validationUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = this.userRepository.findFirstByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("Invalid user");
        }
        return userDetails;
    }

    @Override
    public boolean registerUser(UserBindingModel userBindingModel) {

        if (this.validationUtil.isValid(userBindingModel)) {
            User user = this.userRepository.findFirstByUsername(userBindingModel.getUsername());
            if (user != null) {
                return false;
            } else if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
                return false;
            }

            Role role = this.roleService.findByAuthority("USER");

            user = new User();
            user.setUsername(userBindingModel.getUsername());
            user.setEmail(userBindingModel.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userBindingModel.getPassword()));
            user.getAuthorities().add(role);


            this.userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findUserByName(String name) {
        return this.userRepository.findFirstByUsername(name);
    }
}