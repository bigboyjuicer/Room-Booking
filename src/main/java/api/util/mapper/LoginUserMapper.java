package api.util.mapper;

import api.dto.LoginUserDto;
import api.dto.RegisterUserDto;
import api.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginUserMapper {

    LoginUserMapper MAPPER = Mappers.getMapper(LoginUserMapper.class);

    User toUser(LoginUserDto loginUserDto);

    @InheritInverseConfiguration
    LoginUserDto fromUser(User user);

    List<User> toUsers(List<LoginUserDto> loginUserDtos);

    List<LoginUserDto> fromUsers(List<User> users);

}
