package com.zxw.wx.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zxw.wx.entity.QUser;
import com.zxw.wx.entity.User;
import com.zxw.wx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public User getWXLoginUserInfoByPhone(String phone) {
        QUser qUser = QUser.user;
        User user = jpaQueryFactory.selectFrom(qUser)
                .where(qUser.phone.eq(phone))
                .fetchFirst();
        return user;
    }
}
