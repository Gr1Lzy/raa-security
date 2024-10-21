package gitlab.rightandaboce.raasecurity.mapper.user;

import gitlab.rightandaboce.raasecurity.dto.user.UserInfoDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequestDto userRequestDto);
    UserInfoDto toUserInfoDto(User user);
    List<UserInfoDto> toUserInfoDtoList(List<User> users);
}
