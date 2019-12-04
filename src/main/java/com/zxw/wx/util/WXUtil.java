package com.zxw.wx.util;

import com.alibaba.fastjson.JSONObject;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class WXUtil {

    private static String accessTokenUrl;
    @Value("${accessTokenUrl}")
    private void setAccessTokenUrl(String accessTokenUrl){
        this.accessTokenUrl= accessTokenUrl;
    }


    private static String getUserIDUrl;
    @Value("${getUserIDUrl}")
    private void setGetUserIDUrl(String getUserIDUrl){
        this.getUserIDUrl= getUserIDUrl;
    }


    private static String getUserUrl;
    @Value("${getUserUrl}")
    private void setGetUserUrl(String getUserUrl){
        this.getUserUrl= getUserUrl;
    }

    /**
     * 获取token
     * @param appid
     * @param appsecret
     * @return
     */
    public static String getFirstAccessToken(String appid, String appsecret) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String saveDate = sdf.format(new Date());
        log.info("获取微信TOKN请求开始，当前时间：" + saveDate);
        String requestUrl = accessTokenUrl.replace("{corpId}", appid)
                .replace("{corpsecret}", appsecret);
        JSONObject jsonObject = SendRequest.sendGet(requestUrl);
        // 如果请求成功
        if (null != jsonObject && jsonObject.getIntValue("errcode") == 0) {
            try {
                log.info("获取的token: " + jsonObject.getString("access_token"));
                log.info("时间" + saveDate);
                return jsonObject.getString("access_token");
            } catch (Exception e) {
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}",
                        jsonObject.getString("errmsg"));
            }
        } else {
            log.error("获取token失败 errcode:{} errmsg:{}",
                    jsonObject.getString("errmsg"));
        }
        return null;
    }


    /**
     * 获取用户ID
     *
     * @param accessToken
     * @param code
     * @return
     */
    public static String getUserIDByToken(String accessToken, String code) {
        //1.获取请求的url
        String url = getUserIDUrl.replace("ACCESS_TOKEN", accessToken)
                .replace("CODE", code);
        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = SendRequest.sendGet(url);

        //3.错误消息处理
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            log.info("用户ID：" + jsonObject.getString("UserId"));
            log.info("OpenID：" + jsonObject.getString("OpenId"));
            if (!StringUtils.isEmpty(jsonObject.getString("UserId"))) {
                return jsonObject.getString("UserId");
            } else if (jsonObject.getString("OpenId") != null) {
                log.info("该用户不是本企业人员，OpenID为：" + jsonObject.getString("OpenId"));
                return null;
            }
        }
        return null;
    }


    /**
     * 获取用户ID
     *
     * @param accessToken
     * @param userId
     * @return
     */
    public static WeiXinUserInfoDTO getUserInfoByID(String accessToken, String userId) {
        //1.获取请求的url
        String url = getUserUrl.replace("ACCESS_TOKEN", accessToken)
                .replace("USERID", userId);
        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = SendRequest.sendGet(url);

        //3.错误消息处理
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            log.info("用户ID：" + jsonObject.getString("userid"));
            log.info("用户名称：" + jsonObject.getString("name"));
            log.info("用户手机号：" + jsonObject.getString("mobile"));
            log.info("用户邮箱：" + jsonObject.getString("email"));
            WeiXinUserInfoDTO weiXinUserInfoDTO = new WeiXinUserInfoDTO();
            weiXinUserInfoDTO.setUserid(jsonObject.getString("userid"));
            weiXinUserInfoDTO.setName(jsonObject.getString("name"));
            weiXinUserInfoDTO.setMobile(jsonObject.getString("mobile"));
            weiXinUserInfoDTO.setEmail(jsonObject.getString("email"));
            weiXinUserInfoDTO.setAvatar(jsonObject.getString("avatar"));//头像
            weiXinUserInfoDTO.setEnable(jsonObject.getString("enables"));//成员启用状态。1表示启用的成员，0表示被禁用。注意，服务商调用接口不会返回此字段
            return weiXinUserInfoDTO;
        }
        return null;
    }

}
