package github.com.miralhas.jwt101.api.dto_mapper;

import github.com.miralhas.jwt101.api.dto.input.UserInput;
import github.com.miralhas.jwt101.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUnmapper {

    private final ModelMapper modelMapper;

    public User toDomainObject(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public void copyToDomainObject(UserInput userInput, User user) {
        modelMapper.map(userInput, user);
    }

}
