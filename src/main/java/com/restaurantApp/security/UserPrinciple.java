package com.restaurantApp.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantApp.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;
    private String email;
    @JsonIgnore
    private String password;
    private String username;
    private String mobileNumber;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(String id, String mobileNumber, 
			    		String username, String email, String password, 
			    		Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getMobileNumber(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}