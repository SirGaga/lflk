package com.asideal.lflk.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="基本树形实体", description="基本树形实体所有树形实体继承这个类")
public class BaseTreeEntity {
    @ApiModelProperty(value = "节点ID")
    private Integer id;

    @ApiModelProperty(value = "父级节点ID")
    private Integer parentId;

    @ApiModelProperty(value = "叶子节点")
    private List<? extends BaseTreeEntity> children;
}
