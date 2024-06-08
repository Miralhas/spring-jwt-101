package github.com.miralhas.jwt101.domain.repository;

import github.com.miralhas.jwt101.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}