package com.zxw.wx.entity.common;

public class AppConstant {

    public static final String WX_TOKEN = "'WX_TOKEN'"; //token的缓存键(之所以里面加了单引号是因为Ehcache默认存储的是对象，而不是变量，如果存储的是变量就需要加单引号)
    public static final String SESSION_USER = "'SESSION_USER'"; //token的缓存键
}
