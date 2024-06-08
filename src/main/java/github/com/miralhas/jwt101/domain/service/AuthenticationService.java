package github.com.miralhas.jwt101.domain.service;

import github.com.miralhas.jwt101.api.dto.input.LoginInput;
import github.com.miralhas.jwt101.api.dto.input.UserCreationInput;
import github.com.miralhas.jwt101.api.dto.input.UserInput;
import github.com.miralhas.jwt101.api.dto_mapper.UserUnmapper;
import github.com.miralhas.jwt101.domain.exception.InvalidPermissionException;
import github.com.miralhas.jwt101.domain.exception.UserAlreadyExistException;
import github.com.miralhas.jwt101.domain.model.Role;
import github.com.miralhas.jwt101.domain.model.User;
import github.com.miralhas.jwt101.domain.repository.RoleRepository;
import github.com.miralhas.jwt101.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final UserUnmapper userUnmapper;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }


    public Jwt authenticate(LoginInput loginInput) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenService.generateToken(authentication);
    }


    @Transactional
    public User create(UserCreationInput userCreationInput) {
        var user = new User();
        var userRole = roleRepository.findRoleByName(Role.Value.USER);
        var usernameCheck = userRepository.findByUsername(userCreationInput.getUsername());
        usernameCheck.ifPresent(username -> {
            throw new UserAlreadyExistException(username.getUsername());
        });
        user.setUsername(userCreationInput.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationInput.getPassword()));
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }


    @Transactional
    public User update(Long id, UserInput userInput, JwtAuthenticationToken jwtAuthenticationToken) {
        var user = getUserByUsername(jwtAuthenticationToken.getName());
        Set<Role> userRoles = user.getRoles();

        if (!Objects.equals(user.getId(), id) && !user.isAdmin()) {
            throw new InvalidPermissionException("Não possui permissão");
        }

        var usernameCheck = userRepository.findByUsername(userInput.getUsername());
        usernameCheck.ifPresent(checkedUser -> {
            if (!user.equals(checkedUser)) {
                throw new UserAlreadyExistException(checkedUser.getUsername());
            }
        });

        userInput.setPassword(passwordEncoder.encode(userInput.getPassword()));
        userUnmapper.copyToDomainObject(userInput, user);
        user.setRoles(userRoles);
        return userRepository.save(user);
    }


    @Transactional
    public void delete(Long id, JwtAuthenticationToken jwtAuthenticationToken) {
        var authenticatedUser = getUserByUsername(jwtAuthenticationToken.getName());
        var deleteUser = getUserById(id);
        if (!Objects.equals(authenticatedUser.getId(), deleteUser.getId()) && !authenticatedUser.isAdmin()) {
            throw new InvalidPermissionException("Não possui permissão");
        }
        userRepository.deleteById(id);
    }

}
