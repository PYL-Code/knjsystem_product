package edu.du.knjsystem_product.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final Long seqNoA001;
    private final String username;
    private final String password;

    public CustomUserDetails(Long seqNoA001, String username, String password) {
        this.seqNoA001 = seqNoA001;
        this.username = username;
        this.password = password;
    }

    public Long getSeqNoA001() {
        return seqNoA001;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
