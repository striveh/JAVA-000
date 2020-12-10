package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    private Long custId;

    private Long goodsId;

    private Integer payAmount;

    private Date payTime;

    private Date refundTime;

    private Integer state;

    private Date createTime;

    private Date updateTime;


}
