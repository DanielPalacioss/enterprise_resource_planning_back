package com.erp.accesscontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    @Size(min = 3, max = 60, message = "full name must be between 3 and 60 characters")
    @Column(name = "fullName", length = 60, nullable = false)
    private String fullName;

    @NotBlank(message = "last name cannot be blank or null")
    @Size(min = 3, max = 60, message = "'lastName' must be between 3 and 60 characters")
    @Column(name = "lastName", length = 60, nullable = false)
    private String lastName;

    @JsonIgnore
    @NotBlank(message = "password cannot be blank or null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_-])[a-zA-Z0-9!@#$%^&*()_-]{12,}$",
            message = "The password must have at least 12 characters, one lowercase letter, one uppercase letter, one number, and a special symbol.")
    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "isAccountNonLocked", nullable = false)
    private Boolean isAccountNonLocked;

    @JsonIgnore
    @Column(name = "validationQuestionsCompleted", nullable = false)
    private Boolean validationQuestionsCompleted;

    @JsonIgnore
    @Column(name = "isEnabled", nullable = false)
    private Boolean isEnabled;

    @JsonIgnore
    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @JsonIgnore
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @JsonIgnore
    @NotNull(message = "role cannot be null")
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private RoleModel role;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        role.convertPermissionsStringToList();
        List<GrantedAuthority> authorities = role.getPermissionsList().stream().map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
