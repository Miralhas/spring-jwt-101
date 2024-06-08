package github.com.miralhas.jwt101.api.controller;

import github.com.miralhas.jwt101.api.dto.LoginDTO;
import github.com.miralhas.jwt101.api.dto.UserDTO;
import github.com.miralhas.jwt101.api.dto.input.LoginInput;
import github.com.miralhas.jwt101.api.dto.input.UserCreationInput;
import github.com.miralhas.jwt101.api.dto.input.UserInput;
import github.com.miralhas.jwt101.api.dto_mapper.UserMapper;
import github.com.miralhas.jwt101.api.dto_mapper.UserUnmapper;
import github.com.miralhas.jwt101.domain.model.User;
import github.com.miralhas.jwt101.domain.repository.UserRepository;
import github.com.miralhas.jwt101.domain.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final UserUnmapper userUnmapper;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public LoginDTO login(@RequestBody @Valid LoginInput loginInput) {
        Jwt jwt =  authenticationService.authenticate(loginInput);
        return new LoginDTO(jwt.getTokenValue(), ChronoUnit.HOURS.getDuration().getSeconds());
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid UserCreationInput userCreationInput) {
        var user = authenticationService.create(userCreationInput);
        return userMapper.toModel(user);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody @Valid UserInput userInput, @PathVariable Long id, JwtAuthenticationToken jwtAuthenticationToken) {
        var user = authenticationService.update(id, userInput, jwtAuthenticationToken);
        return userMapper.toModel(user);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id, JwtAuthenticationToken jwtAuthenticationToken) {
        authenticationService.delete(id, jwtAuthenticationToken);
    }


    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userMapper.toCollectionModel(userRepository.findAll());
    }


    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO admin(JwtAuthenticationToken jwtAuthenticationToken) {
        User user = authenticationService.getUserByUsername(jwtAuthenticationToken.getName());
        return userMapper.toModel(user);
    }

}
