package github.com.miralhas.jwt101.domain.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidPermissionException extends AuthenticationException {

    public InvalidPermissionException(String message) {
        super(message);
    }

    public InvalidPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
