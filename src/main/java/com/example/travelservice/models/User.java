package com.example.travelservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Ім'я не може бути порожнім")
    private String firstName;
    @NotBlank(message = "Прізвище не може бути порожнім")
    private String secondName;
    @NotBlank(message = "Пошта не може бути порожньою")
    @Email(message = "Пошта має бути у форматі example@example.com")
    private String email;
    @NotBlank(message = "Пароль не може бути порожнім")
    private String password;

    private String studentCard;
    private String studentCardNumber;
    private String benefitCard;
    private String benefitCardNumber;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private boolean active;
    private String activationCode;

    public User(String firstName, String secondName, String email, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
    }

    public boolean isAdmin() { return roles.contains(Role.ADMIN); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return email;
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
        return isActive();
    }


}
