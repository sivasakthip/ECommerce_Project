package com.ECommerceProject.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ECommerceProject.Model.Role;
import com.ECommerceProject.Model.User;
import com.ECommerceProject.Repository.RoleRepository;
import com.ECommerceProject.Repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();

        // Check if user already exists in the database
        if (!userRepository.findUserByEmail(email).isPresent()) {
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);

            // Assign the default role for new users (can be USER or ADMIN depending on your logic)
            List<Role> roles = new ArrayList<>();

            // Assign role based on your system
            roles.add(roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"))); // Assuming ID 2 is for 'USER'

            user.setRoles(roles);
            userRepository.save(user);
        }

        // Redirect to home page after successful login
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
