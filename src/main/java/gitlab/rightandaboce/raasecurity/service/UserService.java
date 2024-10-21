package gitlab.rightandaboce.raasecurity.service;

import gitlab.rightandaboce.raasecurity.dto.user.UserInfoDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {
    UserResponseDto saveOrGetIfExist(UserRequestDto userRequestDto);
    List<UserInfoDto> findAll();
    UserInfoDto updateStage(UserRequestDto userRequestDto);
    void generateReport(HttpServletResponse response);
}
