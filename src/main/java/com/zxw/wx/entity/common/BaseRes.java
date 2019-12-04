package com.zxw.wx.entity.common;

import lombok.Data;

@Data
public class BaseRes {

    private Integer code;

    private Object data;

    private String msg;

    public BaseRes(int code) {
        this.code = code;
    }

}
