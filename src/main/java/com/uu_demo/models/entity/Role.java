package com.uu_demo.models.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


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
    private String name;

//    @Transient
//    @JsonIgnore
//    @OneToMany(mappedBy = "role")
//    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name;
    }
}
