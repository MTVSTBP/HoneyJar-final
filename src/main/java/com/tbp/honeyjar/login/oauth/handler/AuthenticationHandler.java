package com.tbp.honeyjar.login.oauth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler {

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return false;
        }

        authentication.isAuthenticated();
        return false;
    }

    public void printUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("User name: " + authentication.getName());
            System.out.println("User authorities: " + authentication.getAuthorities());
            System.out.println("User details: " + authentication.getDetails());
            System.out.println("User principal: " + authentication.getPrincipal());
        } else {
            System.out.println("No authenticated user found");
        }
    }
}
