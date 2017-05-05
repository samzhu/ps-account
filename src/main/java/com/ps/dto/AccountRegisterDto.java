package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by samchu on 2017/2/15.
 */
@Data
@ApiModel(description = "帐号注册资料")
public class AccountRegisterDto {
    @ApiModelProperty(value = "帐号", required = true, example = "sam.chu")
    @NotNull
    @Length(min = 5, max = 30, message = "帐号 需介于 {min} 到 {max} 之间")
    private String username;
    @ApiModelProperty(value = "信箱", required = true, example = "sam@gmail.com")
    @NotNull
    @Email(message = "信箱格式不正确")
    @Length(min = 1, max = 255, message = "信箱长度 需介于 {min} 到 {max} 之间")
    private String email;
    @ApiModelProperty(value = "密码", required = true, example = "12345678")
    @NotNull
    @Length(min = 8, max = 255, message = "密码长度 需介于 {min} 到 {max} 之间")
    private String password;

    @ApiModelProperty(value = "角色代码 ROLE_USER", required = true, dataType = "Array[string]")
    @NotNull
    @NotEmpty
    @Size(min = 1, message = "至少必须勾选一角色")
    private List<String> roles;
}
