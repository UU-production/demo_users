package com.uu_demo.models.dto;

import com.uu_demo.models.util.OnCreate;
import com.uu_demo.models.util.OnUpdate;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
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
@Validated
public class UserAuthorizationDto {


    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле Email не должно быть null")
    @Email(groups = {OnCreate.class, OnUpdate.class}, regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email должен быть корректным")
    private String email;


    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле password не должно быть null")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не менее 8 символов.")
    private String password;
}
