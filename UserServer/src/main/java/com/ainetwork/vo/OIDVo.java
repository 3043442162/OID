package com.ainetwork.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("注册OID")
public class OIDVo {

    Integer id;

    @NotNull
    @ApiModelProperty(value = "OID的具体数字")
    Integer oid;
    @ApiModelProperty(value = "ip地址")
    String ipAddress;
    @ApiModelProperty(value = "OID描述信息")
    String describe;
}
