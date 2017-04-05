package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by samchu on 2017/3/27.
 */
@Data
@ApiModel(description = "用戶新增角色")
public class AccountRoleAddDto {
    @ApiModelProperty(value = "角色代碼", required = true, example = "ROLE_USER")
    @NotNull
    @NotEmpty
    private String code;
}
