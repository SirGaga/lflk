package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 增删改查
 * @author ZhangJie
 */
@Data
@ApiModel(value = "增删改查Vo", description = "增删改查Vo")
public class CrudVo {

    @ApiModelProperty(value = "存储批量操作的id")
    List<Integer> ids;

    @ApiModelProperty(value = "存储单数据操作的ip")
    Integer id;

    @ApiModelProperty(value = "用户id")
    Integer userId;

}
