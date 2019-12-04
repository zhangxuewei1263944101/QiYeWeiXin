package com.zxw.wx.entity.dto;

import lombok.Data;

@Data
public class WeiXinLoginDTO {

    private String appid;

    private String agentid;

    private String redirectUri;
}
