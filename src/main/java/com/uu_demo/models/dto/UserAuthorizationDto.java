package com.uu_demo.models.dto;


import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Validated
public class UserAuthorizationDto {


    @NotNull(message = "Field Email must not be  null")
    @Email(regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email has to be correct")
    private String email;


    @NotNull(message = "Fiels password must not be null")
//    @Pattern( regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
//            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    // @Size( min = 8, message = "Поле password должен быть не менее 8 символов.")
    private String password;
}
