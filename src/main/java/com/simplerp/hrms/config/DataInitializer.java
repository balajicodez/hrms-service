package com.simplerp.hrms.config;

import com.simplerp.hrms.model.Permission;
import com.simplerp.hrms.model.Role;
import com.simplerp.hrms.model.User;
import com.simplerp.hrms.repository.PermissionRepository;
import com.simplerp.hrms.repository.RoleRepository;
import com.simplerp.hrms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PermissionRepository permissionRepository,
                           RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Permission viewEmployee = permissionRepository.findByName("VIEW_EMPLOYEE")
                .orElseGet(() -> permissionRepository.save(createPermission("VIEW_EMPLOYEE")));

        Permission processPayroll = permissionRepository.findByName("PROCESS_PAYROLL")
                .orElseGet(() -> permissionRepository.save(createPermission("PROCESS_PAYROLL")));

        Permission approveLeave = permissionRepository.findByName("APPROVE_LEAVE")
                .orElseGet(() -> permissionRepository.save(createPermission("APPROVE_LEAVE")));

        Permission expenses = permissionRepository.findByName("EXPENSES_OR_PETTYCASH")
                .orElseGet(() -> permissionRepository.save(createPermission("EXPENSES_OR_PETTYCASH")));

        Role admin = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role r = new Role();
            r.setName("ADMIN");
            r.setPermissions(new HashSet<>(Set.of(viewEmployee, processPayroll, approveLeave, expenses)));
            return roleRepository.save(r);
        });

        Role hr = roleRepository.findByName("HR").orElseGet(() -> {
            Role r = new Role();
            r.setName("HR");
            r.setPermissions(new HashSet<>(Set.of(viewEmployee, processPayroll, approveLeave)));
            return roleRepository.save(r);
        });

        Role employee = roleRepository.findByName("EMPLOYEE").orElseGet(() -> {
            Role r = new Role();
            r.setName("EMPLOYEE");
            Set<Permission> perms = new HashSet<>();
            perms.add(viewEmployee);
            r.setPermissions(perms);
            return roleRepository.save(r);
        });

        Role cash = roleRepository.findByName("CASHASSISTANT").orElseGet(() -> {
            Role r = new Role();
            r.setName("CASHASSISTANT");
            Set<Permission> perms = new HashSet<>();
            perms.add(expenses);
            r.setPermissions(perms);
            return roleRepository.save(r);
        });

        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("adminpass"));
            adminUser.setActive(true);
            adminUser.setRoles(Set.of(admin));
            userRepository.save(adminUser);
        }
    }

    private Permission createPermission(String name) {
        Permission p = new Permission();
        p.setName(name);
        return p;
    }
}
