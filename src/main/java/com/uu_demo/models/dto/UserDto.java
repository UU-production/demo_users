package com.uu_demo.models.dto;

import com.uu_demo.models.util.OnCreate;
import com.uu_demo.models.util.OnUpdate;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле id не должно принимать null значение при обновлении")
    private Long userId;

    @NotNull(groups = OnCreate.class, message = "Поле имя не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле имя не должно быть Null при обновлении")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "[а-яА-ЯёЁa-zA-Z]+.*$", message = "Поле имя должно начинаться с буквы")
    private String fullName;

    private String avatar;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле Email не должно быть null")
    @Email(groups = {OnCreate.class, OnUpdate.class}, regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email должен быть корректным")
    private String email;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле password не должно быть null")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;

    @Null(groups = OnCreate.class, message = " 'role' автоматически назначается при создании всем пользователям, " +
            "явно указывать не нужно")
    @NotNull(groups = OnUpdate.class, message = "Поле roleName не должно быть null при обновлении")
    private String roleName;
}
