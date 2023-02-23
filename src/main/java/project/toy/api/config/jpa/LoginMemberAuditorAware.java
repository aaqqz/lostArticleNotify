package project.toy.api.config.jpa;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import project.toy.api.config.security.SecurityUtils;

import java.util.Optional;

@Component
public class LoginMemberAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.currentMemberName());
    }
}
