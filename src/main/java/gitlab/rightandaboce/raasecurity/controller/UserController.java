package gitlab.rightandaboce.raasecurity.controller;

import gitlab.rightandaboce.raasecurity.dto.user.UserInfoDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserResponseDto;
import gitlab.rightandaboce.raasecurity.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raa")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<UserResponseDto> loginOrCreate(@Validated @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.saveOrGetIfExist(requestDto));
    }

    @GetMapping("/info")
    public ResponseEntity<List<UserInfoDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/update")
    public ResponseEntity<UserInfoDto> updateStage(@Validated @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateStage(requestDto));
    }

    @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void generateReport(HttpServletResponse response) {
        userService.generateReport(response);
    }
}
