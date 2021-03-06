package com.uu_demo.models.dto;



import com.uu_demo.models.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserUpdateInfoDto {


    @Null(message = "The id field must be null when created")
    @NotNull(message = "The id field must not be null when created")
    private Long userId;


    @Null(message = "The fullName field must be null when created")
    @NotNull(message = "The fullName field must not be null when created")
    private String fullName;

    private String avatar;


    @NotNull(message = "Field email must not be null")
    @Email(regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email has to be correct")
    private String email;

    public static User toUserEntity(UserUpdateInfoDto userUpdateInfoDto) {
        return new User(
                userUpdateInfoDto.getUserId(),
                userUpdateInfoDto.getFullName(),
                userUpdateInfoDto.getEmail(),
                userUpdateInfoDto.getAvatar()
        );
    }


}
