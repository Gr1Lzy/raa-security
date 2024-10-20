package gitlab.rightandaboce.raasecurity.util.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailDomainValidator implements ConstraintValidator<EmailValidator, String> {

    private String domain;

    @Override
    public void initialize(EmailValidator constraintAnnotation) {
        this.domain = constraintAnnotation.domain();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || domain == null || domain.isEmpty()) {
            return false;
        }
        return email.endsWith("@" + domain);
    }
}
