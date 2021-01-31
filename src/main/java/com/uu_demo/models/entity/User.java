package com.uu_demo.models.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @NotNull
    private String fullName;

    private String avatar;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        return roles;
    }

    @Override
    public String getUsername() {
        return getEmail();
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
