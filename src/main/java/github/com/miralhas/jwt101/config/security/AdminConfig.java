package github.com.miralhas.jwt101.config.security;

import github.com.miralhas.jwt101.domain.model.Role;
import github.com.miralhas.jwt101.domain.model.User;
import github.com.miralhas.jwt101.domain.repository.RoleRepository;
import github.com.miralhas.jwt101.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findRoleByName(Role.Value.ADMIN);
        var roleUser = roleRepository.findRoleByName(Role.Value.USER);

        var user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRoles(Set.of(roleAdmin, roleUser));
        user = userRepository.saveAndFlush(user);
        System.out.println(user);
    }
}
