package gitlab.rightandaboce.raasecurity.controller;

import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserResponseDto;
import gitlab.rightandaboce.raasecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/raa")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<UserResponseDto> register(@Validated @RequestBody UserRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.saveOrGetIfExist(requestDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
