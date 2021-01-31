package com.uu_demo.models.dto;


import com.uu_demo.models.util.OnCreate;
import com.uu_demo.models.util.OnUpdate;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserUpdateInfoDto {


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


}
