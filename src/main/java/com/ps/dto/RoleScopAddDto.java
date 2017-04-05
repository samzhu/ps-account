package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by samchu on 2017/3/28.
 */
@Data
@ApiModel(description = "角色新增權限")
public class RoleScopAddDto {
    @ApiModelProperty(value = "權限代碼", required = true, example = "account.readonly")
    @NotNull
    @NotEmpty
    private String scopcode;
}
