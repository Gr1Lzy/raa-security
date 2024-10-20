package gitlab.rightandaboce.raasecurity.service.impl;

import gitlab.rightandaboce.raasecurity.dto.user.UserInfoDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserRequestDto;
import gitlab.rightandaboce.raasecurity.dto.user.UserResponseDto;
import gitlab.rightandaboce.raasecurity.model.user.User;
import gitlab.rightandaboce.raasecurity.repository.UserRepository;
import gitlab.rightandaboce.raasecurity.service.UserService;
import gitlab.rightandaboce.raasecurity.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        userRepository.save(user);

        return UserResponseDto.builder()
                .token(jwtTokenProvider.generateToken(user))
                .build();
    }

    @Override
    public List<UserInfoDto> findAll() {
        return List.of();
    }
}
