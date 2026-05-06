package api.util.mapper;

import api.dto.post.RegisterUserDto;
import api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterUserMapper {

    RegisterUserMapper MAPPER = Mappers.getMapper(RegisterUserMapper.class);

    User toUser(RegisterUserDto registerUserDto);

    RegisterUserDto fromUser(User user);
}
