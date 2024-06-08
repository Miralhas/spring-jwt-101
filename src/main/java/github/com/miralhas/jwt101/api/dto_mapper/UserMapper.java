package github.com.miralhas.jwt101.api.dto_mapper;

import github.com.miralhas.jwt101.api.dto.UserDTO;
import github.com.miralhas.jwt101.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDTO toModel(User user) {
        UserDTO map = modelMapper.map(user, UserDTO.class);
        map.setRoles(user.getRoles().stream().map(r -> r.getName().name()).toList());
        return map;
    }

    public List<UserDTO> toCollectionModel(List<User> users) {
        return users.stream().map(this::toModel).toList();
    }

}
