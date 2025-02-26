package api.util.mapper;

import api.dto.get.UserProfileDto;
import api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserProfileDto userProfileDTO);

    UserProfileDto fromUser(User user);

    List<User> toUsers(List<UserProfileDto> userProfileDtoList);

    List<UserProfileDto> fromUsers(List<User> users);
}
