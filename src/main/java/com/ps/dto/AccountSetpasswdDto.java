package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by samchu on 2017/2/17.
 */
@Data
@ApiModel(description = "密碼變更資料")
public class AccountSetpasswdDto {
    @ApiModelProperty(value = "原始密碼", required = true, example = "12345678")
    @NotNull
    @Size(min = 8, max = 255)
    private String passwordoriginal;
    @ApiModelProperty(value = "新密碼", required = true, example = "12345678")
    @NotNull
    @Size(min = 8, max = 255)
    private String password;
    @ApiModelProperty(value = "新密碼確認", required = true, example = "12345678")
    @NotNull
    @Size(min = 8, max = 255)
    private String passwordconfirm;
}
