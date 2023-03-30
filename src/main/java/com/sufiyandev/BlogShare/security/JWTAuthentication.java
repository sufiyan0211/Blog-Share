package com.sufiyandev.BlogShare.security;

import com.sufiyandev.BlogShare.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JWTAuthentication implements Authentication {
    String jwt;
    User user;

    public JWTAuthentication(String jwt) {
        this.jwt = jwt;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the credentials of the {@code Authentication} request.
     * For eg, the password, or the Bearer token, or the cookie
     * @return
     */
    @Override
    public String getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Returns the principal of the {@code Authentication} request.
     * The "principal" is the entity that is being authenticated.
     * In this case it is the User.
     * @return
     */
    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return (user != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
