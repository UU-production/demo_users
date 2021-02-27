package com.uu_demo.models.dto;

import com.uu_demo.models.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserRegisterDto {

    @NotNull(message = "The id field must not be null when created")
    @NotNull(message = "The id field must not be null when updated")
    @Pattern(regexp = "[а-яА-ЯёЁa-zA-Z]+.*$", message = "Поле имя должно начинаться с буквы")
    private String fullName;


    @NotNull(message = "The field  Email must not be  null")
    @Email(regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email has to be correct")
    private String email;


    @NotNull(message = "The password field must not be null ")
    // @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;

    private String avatar;

    public static User toUserEntity(UserRegisterDto userRegisterDto) {
        return new User(
                userRegisterDto.getFullName(),
                userRegisterDto.getEmail(),
                userRegisterDto.getPassword(),
                userRegisterDto.getAvatar()
        );
    }
}
