package com.zxw.wx.impl;

import com.zxw.wx.entity.common.AppConstant;
import com.zxw.wx.entity.common.BaseRes;
import com.zxw.wx.entity.common.util.WeiXinUtil;
import com.zxw.wx.entity.dto.DepartmentWXDTO;
import com.zxw.wx.entity.dto.WeiXinLoginDTO;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;
import com.zxw.wx.service.WeiXinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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

    @Value("${secret}")
    private String secret;

    @Value("${tongXunLuSecret}")
    private String tongXunLuSecret;

    @Autowired
    private WeiXinService weiXinService;

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
     * 获取token
     *
     * @return
     */
    @Override
    @Cacheable(key = AppConstant.WX_TOKEN, value = "wxToken")
    public String getToken() {
        String token = WeiXinUtil.getFirstAccessToken(appid, secret);
        return token;
//        return "b8BIIPQyq01UEjRFb9HpM29R2u3FLjuIyntIrv8x_4oBOFIwKAxd9jvGXIOba7rswAc8bzTVOXobzno_Jkuc8h-L05ykXoYlK_wy1c_NL8TY6Kg7IYLBXxn0qXn-L6Mx8ILM58urAoocp3J9wWMRKvEoQDKi5V37AUU9za73wRvQ1zrFMQebr85aLdmnAKobgVx7kf3a92BAyJyfaV2BXw-F3ReJXgrrPhL-80i83VTF5fcPkpm-pZzqFP8ezdMZ4be5yw72m6GbJ7uc00waFKhZHr-iCLSLaIOku4w156aR4CCSZpmBAneTG7_lmod0Ru3RTZOkVWe5eYs7A";
    }

    /**
     * 通讯录的token
     *
     * @return
     */
    @Override
    @Cacheable(key = AppConstant.WX_TONG_XUN_LU_TOKEN, value = "wxToken")
    public String getTongXunLuToken() {
        String token = WeiXinUtil.getFirstAccessToken(appid, tongXunLuSecret);
        return token;
//        return "FgcFw9I2dxUYD_gHVci9NOYcZGfFvk73eb5BZlpoXlJRkgmt7WACf9cT2sTfte5x-igca-XyI4Wmowe_g0C2ED902M4lkKUljbn8s8BOFLFinxsSfohPmix6VnC8F7HppdwA-mPoJfzxhioExFIo1xT4delbEDGUblxtJk1PySnD1idgygtu4GC66E9c79L-HInvKNYOltgjmaG6jjYkFg";
    }

    /**
     * 增加用户
     * @param weiXinUserInfoDTO
     * @return
     */
    @Override
    public BaseRes addUser(WeiXinUserInfoDTO weiXinUserInfoDTO) {
        String token = weiXinService.getTongXunLuToken();
        BaseRes res = WeiXinUtil.addUser(weiXinUserInfoDTO, token);
        return res;
    }

    /**
     * 删除用户
     */
    @Override
    public BaseRes deleteUser(String userId) {
        String token = weiXinService.getTongXunLuToken();
        BaseRes res = WeiXinUtil.delUser(token,userId);
        return res;
    }

    /**
     * 更新用户
     *
     * @param weiXinUserInfoDTO
     * @return
     */
    @Override
    public BaseRes updateUser(WeiXinUserInfoDTO weiXinUserInfoDTO) {
        String token = weiXinService.getTongXunLuToken();
        BaseRes res = WeiXinUtil.updateUser(weiXinUserInfoDTO, token);
        return res;
    }

    /**
     * 获取所有的用户信息
     * @return
     */
    @Override
    public List<WeiXinUserInfoDTO> getAllUser() {
        //先获取所有的部门列表
        List<DepartmentWXDTO> departmentWXDTOList = getDepartment(null);
        Long departmentId = 0L;
        //找出顶级部门：parentId:0
        for (DepartmentWXDTO departmentWXDTO : departmentWXDTOList) {
            if (departmentWXDTO.getParentid() == 0) {
                departmentId = departmentWXDTO.getId();
                break;
            }
        }
        String token = weiXinService.getTongXunLuToken();
        return WeiXinUtil.getDepartmentUsersDetail(token, departmentId, 1);
    }

    /**
     * 获取用户ID
     *
     * @param code
     */
    @Override
    public String getUserID(String code) {
        log.info("code：" + code);
        //逻辑判断token是否过期,如果过期刷新,没有过期直接拿来使用
        String token = weiXinService.getToken();
        String userId = WeiXinUtil.getUserIDByToken(token, code);
        return userId;
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public WeiXinUserInfoDTO getUserInfo(String userId) {
        //逻辑判断token是否过期,如果过期刷新,没有过期直接拿来使用
        String token = weiXinService.getToken();
        //根据userID和token获取用户的基本信息
        WeiXinUserInfoDTO weiXinUserInfoDTO = WeiXinUtil.getUserInfoByID(token, userId);
        return weiXinUserInfoDTO;
    }


    /**
     * 增加部门
     * @param departmentWXDTO
     * @return
     */
    @Override
    public BaseRes addDepartment(DepartmentWXDTO departmentWXDTO) {
        String token = weiXinService.getTongXunLuToken();
        BaseRes res = WeiXinUtil.addDepartmnet(departmentWXDTO, token);
        return res;
    }

    /**
     * 删除部门id
     * @param departmentId
     */
    @Override
    public void deleteDepartment(String departmentId) {
        String token = weiXinService.getTongXunLuToken();
        WeiXinUtil.delDepartment(token, departmentId);
    }


    /**
     * 更新部门
     *
     * @param departmentWXDTO
     * @return
     */
    @Override
    public BaseRes updateDepartment(DepartmentWXDTO departmentWXDTO) {
        String token = weiXinService.getTongXunLuToken();
        BaseRes res = new BaseRes();
        res = WeiXinUtil.updateDepartmnet(departmentWXDTO, token);
        return res;
    }

    @Override
    public List<DepartmentWXDTO> getDepartment(Long id) {
        String token = weiXinService.getTongXunLuToken();
        List<DepartmentWXDTO> list = WeiXinUtil.getDepartmentList(token, id);
        return list;
    }

}
