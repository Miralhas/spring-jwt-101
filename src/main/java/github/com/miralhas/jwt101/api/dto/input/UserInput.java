package github.com.miralhas.jwt101.api.dto.input;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 3)
    private String password;

}
