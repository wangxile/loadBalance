package com.wangxile.random;

import com.wangxile.ServiceIp;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wangqi
 * @Description:
 * @Date:Created in 2019/7/15
 * @Modified by: 权重
 */
public class WeightRandom {
    public static String getServer(){
        List<String> ips = new ArrayList<>();
        for(String ip : ServiceIp.WEIGHT_LIST.keySet()){
            Integer weight = ServiceIp.WEIGHT_LIST.get(ip);
            for (Integer i = 0; i < weight; i++) {
                ips.add(ip);
            }
        }
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(ips.size());
        return ServiceIp.LIST.get(randomPos);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }
}
