package com.erp.gateway.model;

import com.erp.gateway.util.Permission;
import com.erp.gateway.util.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "userErp")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username cannot be blank or null")
    @Size(min = 3, max = 20, message = "username must be between 3 and 20 characters")
    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;

    @NotBlank(message = "full name cannot be blank or null")
    @Size(min = 3, max = 60, message = "'full name' must be between 3 and 60 characters")
    @Column(name = "fullName", length = 60, nullable = false)
    private String fullName;

    @NotBlank(message = "last name cannot be blank or null")
    @Size(min = 3, max = 60, message = "'lastName' must be between 3 and 60 characters")
    @Column(name = "lastName", length = 60, nullable = false)
    private String lastName;

    @NotBlank(message = "password cannot be blank or null")
    @Size(min = 8, max = 250, message = "password must be between 8 and 250 characters")
    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "microservice", updatable = false, nullable = false)
    private MicroserviceModel microservice;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        role.setPermissionsJson(role.convertStringToJsonNode(role.getPermissionsList()));
        try {
            role.setPermissions(role.jsonNodeToList(role.getPermissionsJson(), Permission.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<GrantedAuthority> authorities = role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" +role.getName()));
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
}
