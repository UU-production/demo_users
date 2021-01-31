package com.uu_demo.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String roleName;

    @Transient
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role(String roleName) {
        this.roleName = roleName;
    }


    @Override
    public String getAuthority() {
        return roleName;
    }
}
