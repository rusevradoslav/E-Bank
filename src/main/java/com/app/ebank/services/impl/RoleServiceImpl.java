package com.app.ebank.services.impl;

import com.app.ebank.domain.entities.Role;
import com.app.ebank.repositories.RoleRepository;
import com.app.ebank.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
@Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @PostConstruct
    public void seedRolesInDb() {
        if (this.roleRepository.count()==0){
            Role  role = new Role();
            role.setAuthority("USER");
            this.roleRepository.save(role);
        }
    }

    public Role findByAuthority(String role){
        return this.roleRepository.findFirstByAuthority(role);
    }
}
