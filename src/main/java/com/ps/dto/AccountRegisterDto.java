package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by samchu on 2017/2/15.
 */
@Data
@ApiModel(description = "帳號註冊資料")
public class AccountRegisterDto {
    @ApiModelProperty(value = "帳號", required = true, example = "sam.chu")
    @NotNull
    @Size(min = 5, max = 30)
    private String username;
    @ApiModelProperty(value = "信箱", required = true, example = "sam@gmail.com")
    @NotNull
    @Email
    @Size(min = 5, max = 255)
    private String email;
    @ApiModelProperty(value = "密碼", required = true, example = "12345678")
    @NotNull
    @Size(min = 8, max = 255)
    private String password;

    @ApiModelProperty(value = "角色代碼 ROLE_USER", required = true, dataType = "Array[string]")
    @NotNull
    @NotEmpty
    private List<String> roles;
}
