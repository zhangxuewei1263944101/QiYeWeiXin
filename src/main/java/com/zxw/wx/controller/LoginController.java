package com.zxw.wx.controller;

import com.zxw.wx.entity.User;
import com.zxw.wx.entity.common.AppConstant;
import com.zxw.wx.entity.common.BaseRes;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;
import com.zxw.wx.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private WeiXinService weiXinService;

    @RequestMapping({"", "login"}) //这里为空或者是login都能进入该方法
    public String tiaoZhuan() {
        return "login";
    }

    /**
     *加载二维码
     * @return
     */
    @RequestMapping(value = "getErWeiMa",method = RequestMethod.GET)
    @ResponseBody
    private BaseRes getErWeiMa() throws UnsupportedEncodingException {
        BaseRes res = new BaseRes(0);
        res.setData(weiXinService.loginGetErWeiMa());
        return res;
    }

    /**
     * 根据扫码返回的code去查询用户的id
     * 根据用户的id查询用户的信息
     * 根据得到的用户信息（可根据手机号）和本地数据库进行比对，如果存在即可登录或者做一些其他的操作
     * @param session
     * @param code
     * @return
     */
    @RequestMapping(value = "existUser",method = RequestMethod.GET)
    private String getCode(HttpSession session,String code) {
        BaseRes res = new BaseRes(0);
        String userId  = weiXinService.getUserID(code);
        WeiXinUserInfoDTO weiXinUserInfoDTO = weiXinService.getUserInfo(userId);

        session.setAttribute(AppConstant.SESSION_USER,weiXinUserInfoDTO);
        return "index";

    }

}
