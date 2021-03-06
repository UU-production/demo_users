package com.uu_demo.models.dto;

import com.uu_demo.models.entity.Role;
import com.uu_demo.models.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    @Null( message = "The id field must be null when created")
    @NotNull( message = "The id field must not be null when created")
    private Long userId;

    @NotNull( message = "The fullName field must not be null when created")
    @NotNull( message = "The fullName field must not  be null when updated")
    private String fullName;

    private String avatar;

    @NotNull( message = "The email field must not be null ")
    private String email;

    @NotNull( message = "The password field must not be null ")
    private String password;

    @NotNull( message = "The id field must be null when updated")
    private String roleName;

    public static UserDto toUserDto(User user){

        return new UserDto(
                user.getUserId(),
                user.getFullName(),
                user.getAvatar(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().iterator().next().getName()
        );
    }

    public static User toUserEntity(UserDto userDto){
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(userDto.getRoleName()));
        return new User(
                userDto.getUserId(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getAvatar(),
                roles

        );
    }
}
