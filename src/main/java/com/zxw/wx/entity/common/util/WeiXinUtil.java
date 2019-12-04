package com.zxw.wx.entity.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxw.wx.entity.common.BaseRes;
import com.zxw.wx.entity.dto.DepartmentWXDTO;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;
import com.zxw.wx.util.SendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class WeiXinUtil {

    private static String accessTokenUrl;

    @Value("${accessTokenUrl}")
    private void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }


    private static String getUserIDUrl;

    @Value("${getUserIDUrl}")
    private void setGetUserIDUrl(String getUserIDUrl) {
        this.getUserIDUrl = getUserIDUrl;
    }


    private static String getUserUrl;

    @Value("${getUserUrl}")
    private void setGetUserUrl(String getUserUrl) {
        this.getUserUrl = getUserUrl;
    }

    private static String getDepartmentUrl1;

    @Value("${getDepartmentUrl1}")
    public void setGetDepartmentUrl1(String getDepartmentUrl1) {
        this.getDepartmentUrl1 = getDepartmentUrl1;
    }

    private static String getDepartmentUrl2;

    @Value("${getDepartmentUrl2}")
    public void setGetDepartmentUrl2(String getDepartmentUrl2) {
        this.getDepartmentUrl2 = getDepartmentUrl2;
    }

    private static String addDepartmentUrl;

    @Value("${addDepartmentUrl}")
    public void setAddDepartmentUrl(String addDepartmentUrl) {
        this.addDepartmentUrl = addDepartmentUrl;
    }

    private static String updateDepartmentUrl;

    @Value("${updateDepartmentUrl}")
    public void setUpdateDepartmentUrl(String updateDepartmentUrl) {
        this.updateDepartmentUrl = updateDepartmentUrl;
    }

    private static String delDepartmentUrl;

    @Value("${delDepartmentUrl}")
    public void setDelDepartmentUrl(String delDepartmentUrl) {
        this.delDepartmentUrl = delDepartmentUrl;
    }

    private static String getDepartmentUsersDetailUrl;

    @Value("${getDepartmentUsersDetailUrl}")
    public void setGetDepartmentUsersDetailUrl(String getDepartmentUsersDetailUrl) {
        this.getDepartmentUsersDetailUrl = getDepartmentUsersDetailUrl;
    }

    private static String addUserUrl;

    @Value("${addUserUrl}")
    public void setAddUserUrl(String addUserUrl) {
        this.addUserUrl = addUserUrl;
    }

    private static String updateUserUrl;

    @Value("${updateUserUrl}")
    public void setUpdateUserUrl(String updateUserUrl) {
        this.updateUserUrl = updateUserUrl;
    }


    private static String delUserUrl;

    @Value("${delUserUrl}")
    public void setDelUserUrl(String delUserUrl) {
        this.delUserUrl = delUserUrl;
    }

    /**
     * 刪除用户
     *
     * @param token
     * @param userId
     * @return
     */
    public static BaseRes delUser(String token, String userId) {
        BaseRes res = new BaseRes();
        String url = delUserUrl.replace("ACCESS_TOKEN", token).replace("USERID", userId);
        JSONObject jsonObject = SendRequest.sendGet(url);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            res.setCode(8888);
            res.setMessage(jsonObject.getString("errmsg"));
            log.error("删除用户失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            res.setCode(0);
            log.info("删除用户成功！ 返回内容errmsg：" + jsonObject.getString("errmsg"));
        }
        return res;
    }

    /**
     * 更新用户
     *
     * @param weiXinUserInfoDTO
     * @param token
     * @return
     */
    public static BaseRes updateUser(WeiXinUserInfoDTO weiXinUserInfoDTO, String token) {
        BaseRes res = new BaseRes();
        String url = updateUserUrl.replace("ACCESS_TOKEN", token);
        String weiXinUserJSON = JSONObject.toJSONString(weiXinUserInfoDTO);
        JSONObject jsonObject = SendRequest.sendPost(url, weiXinUserJSON);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            res.setCode(8888);
            res.setMessage(jsonObject.getString("errmsg"));
            log.error("更新用户失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            res.setCode(0);
            log.error("更新用户成功！errcode:{} errmsg:{}" + jsonObject.getLongValue("errcode"), jsonObject.getString("errmsg"));
        }
        return res;
    }


    /**
     * 添加用户
     *
     * @param weiXinUserInfoDTO
     * @param token
     * @return
     */
    public static BaseRes addUser(WeiXinUserInfoDTO weiXinUserInfoDTO, String token) {
        BaseRes res = new BaseRes();
        String url = addUserUrl.replace("ACCESS_TOKEN", token);
        String weiXinUserJSON = JSONObject.toJSONString(weiXinUserInfoDTO);
        JSONObject jsonObject = SendRequest.sendPost(url, weiXinUserJSON);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            res.setCode(8888);
            res.setMessage(jsonObject.getString("errmsg"));
            log.error("添加用户失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            res.setCode(0);
            log.error("添加用户成功！errcode:{} errmsg:{}" + jsonObject.getLongValue("errcode"), jsonObject.getString("errmsg"));
        }
        return res;
    }


    /**
     * 获取部门成员详情列表
     *
     * @param token
     * @param departmentId
     * @param fetch        1/0 是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
     * @return
     */
    public static List<WeiXinUserInfoDTO> getDepartmentUsersDetail(String token, Long departmentId, Integer fetch) {
        //1、获取url
        String url = getDepartmentUsersDetailUrl.replace("ACCESS_TOKEN", token).replace("DEPARTMENT_ID", departmentId + "").replace("FETCH_CHILD", fetch + "");
        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = SendRequest.sendGet(url);
        //3.错误消息处理
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            JSONArray jsonArray = jsonObject.getJSONArray("userlist");
            if (null != jsonArray) {
                List<WeiXinUserInfoDTO> weiXinUserInfoDTOList = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    WeiXinUserInfoDTO weiXinUserInfoDTO = new WeiXinUserInfoDTO();
                    weiXinUserInfoDTO.setUserid(jo.getString("userid"));
                    weiXinUserInfoDTO.setName(jo.getString("name"));
                    weiXinUserInfoDTO.setMobile(jo.getString("mobile"));
                    weiXinUserInfoDTO.setEmail(jo.getString("email"));
                    weiXinUserInfoDTO.setAvatar(jo.getString("avatar"));//头像
                    weiXinUserInfoDTO.setEnable(jo.getString("enables"));//成员启用状态。1表示启用的成员，0表示被禁用。注意，服务商调用接口不会返回此字段
                    weiXinUserInfoDTO.setAddress(jo.getString("address"));
                    JSONArray departArray = jo.getJSONArray("department");
                    List<Long> departmentIds = new ArrayList<>();
                    for (int b = 0; b < departArray.size(); b++) {
                        departmentIds.add(Long.parseLong(departArray.getString(b)));
                    }
                    weiXinUserInfoDTO.setDepartment(departmentIds);
                    weiXinUserInfoDTOList.add(weiXinUserInfoDTO);
                }
                return weiXinUserInfoDTOList;
            }
        }
        return null;
    }


    /**
     * 刪除部門
     *
     * @param token
     * @param departmentId
     * @return
     */
    public static BaseRes delDepartment(String token, String departmentId) {
        BaseRes res = new BaseRes();
        String url = delDepartmentUrl.replace("ACCESS_TOKEN", token).replace("DEPTID", departmentId);
        JSONObject jsonObject = SendRequest.sendGet(url);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            res.setCode(8888);
            res.setMessage(jsonObject.getString("errmsg"));
            log.error("删除部门失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            res.setCode(0);
            log.info("删除部门成功！ 返回内容errmsg：" + jsonObject.getString("errmsg"));
        }
        return res;
    }


    /**
     * 添加部门
     *
     * @param departmentWXDTO
     * @param token
     * @return
     */
    public static BaseRes addDepartmnet(DepartmentWXDTO departmentWXDTO, String token) {
        BaseRes res = new BaseRes();
        String url = addDepartmentUrl.replace("ACCESS_TOKEN", token);
        String departmentJSON = JSONObject.toJSONString(departmentWXDTO);
        JSONObject jsonObject = SendRequest.sendPost(url, departmentJSON);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            res.setCode(8888);
            res.setMessage(jsonObject.getString("errmsg"));
            log.error("添加部门失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            res.setCode(0);
            res.setData(jsonObject.getLongValue("id"));
            log.error("添加部门成功！id:{} errmsg:{}" + jsonObject.getLongValue("id"), jsonObject.getString("errmsg"));
        }
        return res;
    }

    /**
     * 更新部门
     *
     * @param departmentWXDTO
     * @param token
     * @return
     */
    public static BaseRes updateDepartmnet(DepartmentWXDTO departmentWXDTO, String token) {
        BaseRes res = new BaseRes();
        String url = updateDepartmentUrl.replace("ACCESS_TOKEN", token);
        String departmentJSON = JSONObject.toJSONString(departmentWXDTO);
        JSONObject jsonObject = SendRequest.sendPost(url, departmentJSON);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            res.setCode(8888);
            res.setMessage(jsonObject.getString("errmsg"));
            log.error("更新部门失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            res.setCode(0);
            log.info("更新部门成功！ 返回内容errmsg：" + jsonObject.getString("errmsg"));
        }
        return res;
    }

    /**
     * 获取部门列表
     */
    public static List<DepartmentWXDTO> getDepartmentList(String token, Long id) {
        //1、获取url
        String url = "";
        if (null == id || id.equals("")) {
            url = getDepartmentUrl2.replace("ACCESS_TOKEN", token);
        } else {
            url = getDepartmentUrl1.replace("ACCESS_TOKEN", token).replace("ID", id + "");
        }
        JSONObject jsonObject = SendRequest.sendGet(url);
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            log.error("获取部门列表失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            JSONArray jsonArray = jsonObject.getJSONArray("department");
            if (null != jsonArray) {
                List<DepartmentWXDTO> departmentDTOList = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jb = jsonArray.getJSONObject(i);
                    DepartmentWXDTO wxdto = new DepartmentWXDTO();
                    wxdto.setId(jb.getLongValue("id"));
                    wxdto.setName(jb.getString("name"));
                    wxdto.setOrder(jb.getInteger("order"));
                    wxdto.setParentid(jb.getLongValue("parentid"));
                    departmentDTOList.add(wxdto);
                }
                return departmentDTOList;
            }
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
     * 获取token
     *
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
     * 获取用户
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
