package com.ECommerceProject.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ECommerceProject.Model.CustomerUserDetail;
import com.ECommerceProject.Model.User;
import com.ECommerceProject.Repository.UserRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Use username instead of the undeclared 'email'
        Optional<User> user = userRepository.findUserByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return user.map(CustomerUserDetail::new).get();
    }
}
