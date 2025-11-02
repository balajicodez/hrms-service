package com.simplerp.hrms.service;

import com.simplerp.hrms.model.User;
import com.simplerp.hrms.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toSet());

        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getName())));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                true, true, true,
                authorities
        );
    }
}
