package com.zxw.wx.service;

import com.zxw.wx.entity.dto.WeiXinLoginDTO;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public interface WeiXinService {

    WeiXinLoginDTO loginGetErWeiMa() throws UnsupportedEncodingException;

    String getUserID(String code);

    WeiXinUserInfoDTO getUserInfo(String userId);

}
