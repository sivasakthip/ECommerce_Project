package com.ECommerceProject.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerUserDetail extends User implements UserDetails {

    public CustomerUserDetail(User user) {
        super(user);  // Call the parent User constructor
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // Loop through the user's roles and convert them to GrantedAuthority
        super.getRoles().forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority(role.getName())); // Use getName() to retrieve the role name
        });
        return authorityList;  // Return the list of authorities
    }

    @Override
    public String getUsername() {
        return super.getEmail();  // Use email as the username
    }

    @Override
    public String getPassword() {
        return super.getPassword();  // Return the user's password
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Hardcoded to true, you can implement custom logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Changed from false to true, to ensure the account is not locked by default
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true;  // User is enabled
    }
}
