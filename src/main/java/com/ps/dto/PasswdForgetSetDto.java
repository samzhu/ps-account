package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by samchu on 2017/2/17.
 */
@Data
@ApiModel(description = "密碼重置請求")
public class PasswdForgetSetDto {
    @ApiModelProperty(value = "帳號", required = true, example = "sam@gmail.com", notes = "email帳號")
    @NotNull @NotEmpty
    @Size(min = 5, max = 30)
    private String username;

    @ApiModelProperty(value = "密碼", required = true, example = "123456")
    @NotNull @NotEmpty
    @Size(min = 8, max = 255)
    private String password;

    @ApiModelProperty(value = "密碼確認", required = true, example = "123456")
    @NotNull @NotEmpty
    @Size(min = 8, max = 255)
    private String passwordconfirm;

    @ApiModelProperty(value = "OTP", required = true, example = "05487")
    @NotNull @NotEmpty
    @Size(min = 4, max = 10)
    private String otp;
}
