package com.uu_demo.models.dto;

import com.uu_demo.models.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserResetPasswordDto {

    @NotNull( message = "Field must not be null")
//    @Pattern( regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
//            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
//    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;

    public static User toUserEntity(UserResetPasswordDto userResetPasswordDto) {
        return new User(
               userResetPasswordDto.getPassword()
        );
    }
}

