package gitlab.rightandaboce.raasecurity.model.user;

import gitlab.rightandaboce.raasecurity.util.email.EmailValidator;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @EmailValidator(domain = "rightandabove.com")
    private String email;

    @Column
    private int currentStage = 0;

    @Column
    private int maxStage = 4;
}


