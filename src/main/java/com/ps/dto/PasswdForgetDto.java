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
@ApiModel(description = "密碼重置請求")
public class PasswdForgetDto {
    @ApiModelProperty(value = "帳號", required = true, example = "sam.chu12")
    @NotNull
    @Size(min = 5, max = 30)
    private String username;
}
