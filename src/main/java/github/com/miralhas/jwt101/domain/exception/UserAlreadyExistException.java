package github.com.miralhas.jwt101.domain.exception;


import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException {

    public static final String MESSAGE = "Usuário de nome %s já existe";

    public UserAlreadyExistException(String username) {
        super(String.format(MESSAGE, username));
    }

    public UserAlreadyExistException(String username, Throwable cause) {
        super(String.format(MESSAGE, username), cause);
    }
}
