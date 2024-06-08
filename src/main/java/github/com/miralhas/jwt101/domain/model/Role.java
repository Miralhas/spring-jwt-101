package github.com.miralhas.jwt101.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Value name;

    @Getter
    public enum Value {
        ADMIN(new SimpleGrantedAuthority("ADMIN")),
        USER(new SimpleGrantedAuthority("USER"));

        private final SimpleGrantedAuthority authority;

        Value(SimpleGrantedAuthority authority) {
            this.authority = authority;
        }
    }

}