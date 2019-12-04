package com.zxw.wx.controller;

import com.zxw.wx.entity.User;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;
import com.zxw.wx.service.UserService;
import com.zxw.wx.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    WeiXinService weiXinService;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(HttpSession session, String code) {

        String userId = weiXinService.getUserID(code);
        WeiXinUserInfoDTO weiXinUserInfoDTO = weiXinService.getUserInfo(userId);
        User user = userService.getWXLoginUserInfoByPhone(weiXinUserInfoDTO.getMobile());
        session.setAttribute("user_session", user);
        return "index";

    }

}
