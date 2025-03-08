package api.util.mapper;

import api.dto.get.UserInfoDto;
import api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoMapper {

    UserInfoMapper MAPPER = Mappers.getMapper(UserInfoMapper.class);

    UserInfoDto fromUser(User user);

    User fromUserInfoDto(UserInfoDto userInfoDto);

}
