package com.zxw.wx.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.persistence.GenerationType;

@Data
@Getter
@Entity
@Table(name = "wx_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

}
