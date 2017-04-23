package com.ps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by samchu on 2017/2/17.
 */
@Data
@ApiModel(description = "角色资料")
public class RoleDto {
    @ApiModelProperty(value = "角色代码", required = true, example = "ROLE_ADMIN")
    @NotNull
    @NotEmpty
    @Length(min = 5, max = 20, message = "代码(code)长度 需介于 {min} 到 {max} 之间")
    private String code;
    @ApiModelProperty(value = "角色标签", required = true, example = "新角色名称")
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 50,message = "标签(label)长度 需介于 {min} 到 {max} 之间")
    private String label;
}
