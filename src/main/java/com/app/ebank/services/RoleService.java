package com.app.ebank.services;

import com.app.ebank.domain.entities.Role;

public interface RoleService {

    void seedRolesInDb();

    Role findByAuthority(String role);
}
