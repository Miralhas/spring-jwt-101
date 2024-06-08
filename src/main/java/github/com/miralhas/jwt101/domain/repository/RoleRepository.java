package github.com.miralhas.jwt101.domain.repository;

import github.com.miralhas.jwt101.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(Role.Value name);
}