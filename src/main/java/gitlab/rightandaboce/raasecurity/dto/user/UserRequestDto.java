package gitlab.rightandaboce.raasecurity.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Email is required.")
    private String email;
}
