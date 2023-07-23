package com.stpg.distrinet.security.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CustomAuditAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        if (authentication.getPrincipal() instanceof String)
            return Optional.ofNullable((String) authentication.getPrincipal());
        return Optional.ofNullable(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
    }
}
