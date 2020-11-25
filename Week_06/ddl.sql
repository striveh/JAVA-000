CREATE TABLE `cust` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `nick_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `state` tinyint(1) DEFAULT '0' COMMENT '账户状态0:有效1:注销',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `goods_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名称',
  `goods_describe` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品描述',
  `goods_price` int(10) NOT NULL COMMENT '商品价格单位（分）',
  `state` tinyint(1) DEFAULT '0' COMMENT '0:未上架1:已经上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `cust_id` bigint(20) NOT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `pay_amount` int(10) DEFAULT NULL COMMENT '支付价格(单位分)',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付状态0:未支付1:已经支付 2:已退款',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_cust` (`cust_id`) USING BTREE,
  KEY `idx_goods` (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;