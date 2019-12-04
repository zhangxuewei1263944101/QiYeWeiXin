package com.zxw.wx.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeiXinUserInfoDTO {


    //成员UserID。对应管理端的帐号，企业内必须唯一。不区分大小写，长度为1~64个字节
    private String userid;

    //成员名称
    private String name;

    //手机号码，第三方仅通讯录应用可获取
    private String mobile;

    //成员所属部门id列表，仅返回该应用有查看权限的部门id
    private List<Long> department;

    //部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)
    private String order;

    //职务信息；第三方仅通讯录应用可获取
    private String position;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性
     */
    private String gender;

    //邮箱，第三方仅通讯录应用可获取
    private String email;

    //头像url。注：如果要获取小图将url最后的”/0”改成”/100”即可。第三方仅通讯录应用可获取
    private String avatar;

    //座机。第三方仅通讯录应用可获取
    private String telephone;

    //成员启用状态。1表示启用的成员，0表示被禁用。注意，服务商调用接口不会返回此字段
    private String enable;

    //地址。长度最大128个字符
    private String address;

}
