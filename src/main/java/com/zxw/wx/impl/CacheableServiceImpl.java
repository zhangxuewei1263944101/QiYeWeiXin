package com.zxw.wx.impl;

import com.zxw.wx.entity.common.AppConstant;
import com.zxw.wx.service.CacheableService;
import com.zxw.wx.util.WXUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = {"wxToken"})
public class CacheableServiceImpl implements CacheableService {

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String appSecret;

    /**
     *
     * @return
     */
    @Override
    @Cacheable(key = AppConstant.WX_TOKEN)
    public String getToken() {
//        return WXUtil.getFirstAccessToken(appid,appSecret);
        log.info("开始执行方法");
        return "1212";
    }
}
