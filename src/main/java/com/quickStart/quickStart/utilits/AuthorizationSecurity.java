package com.quickStart.quickStart.utilits;

import com.quickStart.quickStart.repositories.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthorizationSecurity {
    @Autowired
    public UserAccountRepo userAccountRepo;

    public boolean isOwner(UUID employeeId, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        var userAccount = userAccountRepo.findByUsername(authentication.getName()).orElse(null);

        Boolean hasPermission = authentication.getName().equals(userAccount.getUsername())
                && userAccount.getEmployee().getId().equals(employeeId);
        return hasPermission;
    }
}
