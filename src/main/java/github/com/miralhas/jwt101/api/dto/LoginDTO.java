package github.com.miralhas.jwt101.api.dto;

public record LoginDTO(String token_acess, Long expiresIn) {
}
