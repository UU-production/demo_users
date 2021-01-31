package com.uu_demo.models.dto;


import com.uu_demo.models.util.OnCreate;
import com.uu_demo.models.util.OnUpdate;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserResetPasswordDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле password не должно быть null")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;
}
