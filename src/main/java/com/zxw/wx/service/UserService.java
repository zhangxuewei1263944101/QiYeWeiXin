package com.zxw.wx.service;

import com.zxw.wx.entity.User;

import java.util.List;

public interface UserService {

     User getWXLoginUserInfoByPhone(String phone);
}
