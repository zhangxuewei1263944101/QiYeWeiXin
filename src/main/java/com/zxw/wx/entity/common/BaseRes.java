package com.zxw.wx.entity.common;

import lombok.Data;

@Data
public class BaseRes {

    private int code;
    private String message;
    private Object data;

    public BaseRes() {}

    public BaseRes(int code) {
        this.code = code;
    }

    public BaseRes(int code,String message) {
        this.code = code;
        this.message = message;
    }
}
