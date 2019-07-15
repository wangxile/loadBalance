package com.wangxile.random;

import com.wangxile.ServiceIp;

/**
 * @Author:wangqi
 * @Description:
 * @Date:Created in 2019/7/15
 * @Modified by:随机
 */

public class Random {
    public static String getServer(){
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(ServiceIp.LIST.size());
        return ServiceIp.LIST.get(randomPos);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }
}
