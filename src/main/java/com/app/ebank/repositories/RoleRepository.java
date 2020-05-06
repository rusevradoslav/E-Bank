package com.app.ebank.repositories;



import com.app.ebank.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findFirstByAuthority(String role);
}


