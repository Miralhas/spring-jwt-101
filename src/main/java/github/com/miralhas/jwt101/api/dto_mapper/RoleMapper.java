package github.com.miralhas.jwt101.api.dto_mapper;

import github.com.miralhas.jwt101.api.dto.RoleDTO;
import github.com.miralhas.jwt101.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleDTO toModel(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

}
