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
public class WeightRandomV2 {

    public static String getServer(){
        //计算当前权重的合
        int totalWeight = 0;
        //判断权重值是否一样
        boolean sameWeight = true;

        Integer[] weightArr = (Integer[]) ServiceIp.WEIGHT_LIST.values().toArray();
        for(int i = 0; i < weightArr.length; i++){
            Integer weight = weightArr[i];
            totalWeight += weight;
            if(sameWeight && i > 0 && !weight.equals(weightArr[i-1])){
                sameWeight = false;
            }
        }

        //根据总数获取随机值
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(totalWeight);

        //循环权重map
        if(!sameWeight){
            for(String ip : ServiceIp.WEIGHT_LIST.keySet()){
                Integer weightValue = ServiceIp.WEIGHT_LIST.get(ip);
                //每次比较大小如果小于则命中
                if(randomPos < weightValue){
                    return ip;
                }
                //如果大于则减去权重值继续循环
                randomPos -= weightValue;
            }
        }

        return (String )ServiceIp.WEIGHT_LIST.keySet().toArray()[new java.util.Random().nextInt(ServiceIp.WEIGHT_LIST.size())];
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }
}
