package gitlab.rightandaboce.raasecurity.service.impl;

import gitlab.rightandaboce.raasecurity.dto.user.UserInfoDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserResponseDto;
import gitlab.rightandaboce.raasecurity.exception.EmailNotFoundException;
import gitlab.rightandaboce.raasecurity.exception.FileCreateException;
import gitlab.rightandaboce.raasecurity.exception.TaskCompletedException;
import gitlab.rightandaboce.raasecurity.mapper.user.UserMapper;
import gitlab.rightandaboce.raasecurity.model.user.User;
import gitlab.rightandaboce.raasecurity.repository.UserRepository;
import gitlab.rightandaboce.raasecurity.service.UserService;
import gitlab.rightandaboce.raasecurity.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponseDto saveOrGetIfExist(UserRequestDto userRequestDto) {
        Optional<User> userOpt = userRepository.findByEmail(userRequestDto.getEmail());

        if (userOpt.isPresent()) {
            return UserResponseDto.builder()
                    .token(jwtTokenProvider.generateToken(userOpt.get()))
                    .build();
        }

        User user = UserMapper.INSTANCE.toUser(userRequestDto);
        userRepository.save(user);

        return UserResponseDto.builder()
                .token(jwtTokenProvider.generateToken(user))
                .build();
    }

    @Override
    public List<UserInfoDto> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new IllegalArgumentException("No users found.");
        }

        return UserMapper.INSTANCE.toUserInfoDtoList(users);
    }

    @Override
    public UserInfoDto updateStage(UserRequestDto userRequestDto) {
        User user = userRepository
                .findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(
                        String.format("Email %s not found", userRequestDto.getEmail())));

        int value = user.getCurrentStage();

        if (value == 4)
            throw new TaskCompletedException(userRequestDto.getEmail() + " completed all stages.");

        user.setCurrentStage(value + 1);

        userRepository.save(user);

        return UserMapper.INSTANCE.toUserInfoDto(user);
    }

    @Override
    public void generateReport(HttpServletResponse response) {
        try (PrintWriter printWriter = response.getWriter()) {
            response.setContentType("test/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv");

            List<UserInfoDto> users = findAll();

            printWriter.println("email,current_stage,max_stage");

            for (UserInfoDto user : users) {
                printWriter.println(
                        user.getEmail() + "," +
                        user.getCurrentStage());
            }

            printWriter.flush();
        } catch (IOException e) {
            throw new FileCreateException(String.format("Error creating file %s", e.getMessage()));
        }
    }
}
