package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    private String username;

    private String password;

    private String nickName;

    private Integer state;

    private Date createTime;

    private Date updateTime;


}
