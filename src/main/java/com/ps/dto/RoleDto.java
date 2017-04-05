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
@ApiModel(description = "角色資料")
public class RoleDto {
    @ApiModelProperty(value = "角色代碼", required = true, example = "ROLE_ADMIN")
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 20)
    private String code;
    @ApiModelProperty(value = "角色標籤", required = true, example = "新角色名稱")
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String label;
}
