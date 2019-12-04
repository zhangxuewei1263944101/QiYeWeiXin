package com.zxw.wx.impl;

import com.zxw.wx.entity.dto.WeiXinLoginDTO;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;
import com.zxw.wx.service.CacheableService;
import com.zxw.wx.service.WeiXinService;
import com.zxw.wx.util.WXUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Service
public class WeiXinServiceImpl implements WeiXinService {

    //企业ID
    @Value("${appid}")
    private String appid;

    //应用AgentId
    @Value("${agentid}")
    private String agentid;

    //第三方网站指定自己的端口
    @Value("${redirectUri}")
    private String redirectUri;

    @Autowired
    private CacheableService cacheableService;

    /**
     * 初始加载二维码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public WeiXinLoginDTO loginGetErWeiMa() throws UnsupportedEncodingException {
        String redirect_uri = URLEncoder.encode(redirectUri, "utf-8");
        WeiXinLoginDTO weiXinLoginDTO = new WeiXinLoginDTO();
        weiXinLoginDTO.setAppid(appid);
        weiXinLoginDTO.setAgentid(agentid);
        weiXinLoginDTO.setRedirectUri(redirect_uri);
        return weiXinLoginDTO;
    }

    /**
     * 获取用户ID
     * @param code
     * @return
     */
    @Override
    public String getUserID(String code) {
        String token = cacheableService.getToken();
        WXUtil.getUserIDByToken(token,code);
        return null;
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public WeiXinUserInfoDTO getUserInfo(String userId) {

        return null;
    }

}
