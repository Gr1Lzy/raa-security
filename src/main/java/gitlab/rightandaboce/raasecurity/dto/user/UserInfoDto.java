package gitlab.rightandaboce.raasecurity.dto.user;

import lombok.Data;

@Data
public class UserInfoDto {
    private String email;
    private int currentStage;
    private int maxStage;
}
