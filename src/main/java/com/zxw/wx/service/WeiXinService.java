package com.zxw.wx.service;

import com.zxw.wx.entity.common.BaseRes;
import com.zxw.wx.entity.dto.DepartmentWXDTO;
import com.zxw.wx.entity.dto.WeiXinLoginDTO;
import com.zxw.wx.entity.dto.WeiXinUserInfoDTO;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface WeiXinService {

    WeiXinLoginDTO loginGetErWeiMa() throws UnsupportedEncodingException;
    String getToken();
    String getTongXunLuToken();

    BaseRes addUser(WeiXinUserInfoDTO weiXinUserInfoDTO);
    BaseRes deleteUser(String userId);
    BaseRes updateUser(WeiXinUserInfoDTO weiXinUserInfoDTO);
    String getUserID(String code);
    WeiXinUserInfoDTO getUserInfo(String userId);
    List<WeiXinUserInfoDTO> getAllUser();


    BaseRes addDepartment(DepartmentWXDTO departmentWXDTO);
    void deleteDepartment(String departmentId);
    BaseRes updateDepartment(DepartmentWXDTO departmentWXDTO);
    List<DepartmentWXDTO> getDepartment(Long id);



}
