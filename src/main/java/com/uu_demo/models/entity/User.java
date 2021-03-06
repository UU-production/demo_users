package com.uu_demo.models.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(@NotNull String fullName, String avatar, @NotNull String email, @NotNull String password) {
        this.fullName = fullName;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
    }

    public User(Long userId, @NotNull String fullName, String avatar, @NotNull String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.email = email;
    }

    public User(@NotNull String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
