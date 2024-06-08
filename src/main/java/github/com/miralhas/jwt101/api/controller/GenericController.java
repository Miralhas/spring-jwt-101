package github.com.miralhas.jwt101.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generic")
public class GenericController {

    @GetMapping
    public boolean hello(Authentication authentication) {
        return authentication != null;
    }


}
