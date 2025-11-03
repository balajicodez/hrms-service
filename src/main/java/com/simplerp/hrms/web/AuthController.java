package com.simplerp.hrms.web;

import com.simplerp.hrms.model.Permission;
import com.simplerp.hrms.model.Role;
import com.simplerp.hrms.model.User;
import com.simplerp.hrms.repository.PermissionRepository;
import com.simplerp.hrms.repository.RoleRepository;
import com.simplerp.hrms.repository.UserRepository;
import com.simplerp.hrms.security.JwtUtil;
import com.simplerp.hrms.web.dto.AuthRequest;
import com.simplerp.hrms.web.dto.AuthResponse;
import com.simplerp.hrms.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PermissionRepository permissionRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        List<String> authorities = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toList());

        authorities.addAll(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        String token = jwtUtil.generateToken(user.getUsername(), authorities);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        Set<Role> roles = request.getRoleNames() == null ? Set.of() :
                request.getRoleNames().stream()
                        .map(roleName -> roleRepository.findByName(roleName).orElse(null))
                        .filter(r -> r != null)
                        .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }
}
