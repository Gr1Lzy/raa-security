package gitlab.rightandaboce.raasecurity.service;

import gitlab.rightandaboce.raasecurity.dto.user.UserInfoDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto saveOrGetIfExist(UserRequestDto userRequestDto);
    List<UserInfoDto> findAll();
}
