package com.zxw.wx.entity.dto;

import lombok.Data;

@Data
public class DepartmentWXDTO {
    private Long id;//创建的部门id

    private String name;//部门名称，此字段对第三方将逐渐回收，后续第三方仅通讯录应用可获取，第三方页面需要通过企业微信开放数据域来展示名字

    private String name_en;//英文名称

    private Long parentid;//	父亲部门id。根部门为1

    private Integer order;//在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
}
