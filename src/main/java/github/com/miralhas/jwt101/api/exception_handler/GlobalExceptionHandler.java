package github.com.miralhas.jwt101.api.exception_handler;

import github.com.miralhas.jwt101.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;


    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException ex, WebRequest webRequest) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Business Exception");
        problem.setType(URI.create("http://localhost:8080/business-exception"));

        return problem;
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errorsMap = new HashMap<>();
        ex.getFieldErrors()
                .forEach(error -> {
                    var message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                    errorsMap.put(error.getField(), message);
                });

        var problemDetail = ProblemDetail.forStatusAndDetail(status, "Verifique os argumentos");
        problemDetail.setTitle("Argumento Inválido");
        problemDetail.setInstance(URI.create(((ServletWebRequest)request).getRequest().getRequestURI()));
        problemDetail.setType(URI.create("http://localhost:8080/error"));
        problemDetail.setProperty("errors", errorsMap);

        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex, WebRequest webRequest) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Forbidden");
        return problemDetail;
    }
}
