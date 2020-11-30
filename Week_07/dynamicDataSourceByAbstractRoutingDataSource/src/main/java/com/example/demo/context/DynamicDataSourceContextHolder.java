package com.example.demo.context;

import com.example.demo.constants.DataSourceConstants;

import java.util.concurrent.atomic.AtomicInteger;

public class DynamicDataSourceContextHolder {

    /**
     * 动态数据源名称上下文
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    /**
     * 设置数据源
     * @param key
     */
    public static void setContextKey(String key){

        if (key.equals(DataSourceConstants.DS_KEY_SLAVE)){
            //  读库负载均衡(轮询方式)
            int index = counter.getAndIncrement() % 2;
            if (index == 0) {
                key = DataSourceConstants.DS_KEY_SLAVE1;
            } else {
                key = DataSourceConstants.DS_KEY_SLAVE2;
            }
        }

        System.out.println("切换数据源"+key);
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    /**
     * 获取数据源名称
     * @return
     */
    public static String getContextKey(){
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null? DataSourceConstants.DS_KEY_MASTER:key;
    }

    /**
     * 删除当前数据源名称
     */
    public static void removeContextKey(){
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}