package gitlab.rightandaboce.raasecurity.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private String token;
}
