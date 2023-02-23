package project.toy.api.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.toy.api.config.data.CustomMemberDetails;

public class SecurityUtils {

    public static Long currentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        CustomMemberDetails principal = (CustomMemberDetails) authentication.getPrincipal();
        return principal.getId();
    }

    public static String currentMemberName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        CustomMemberDetails principal = (CustomMemberDetails) authentication.getPrincipal();
        return principal.getName();
    }
}
