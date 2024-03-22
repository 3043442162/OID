package com.ainetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@ApiModel(value = "登录表单")
public class LoginForm {

    @Positive
    @NotNull
    @ApiModelProperty(value = "账号，具有唯一性")
    private Integer account;

//    @Email(message = "邮箱格式错误")
//    @ApiModelProperty(value = "邮箱")
//    private String email;

    @ApiModelProperty(value = "密码，使用bcrypt加密存储")
    @NotBlank
    private String password;
}
