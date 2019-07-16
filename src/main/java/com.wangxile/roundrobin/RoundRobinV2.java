package com.wangxile.roundrobin;

import com.wangxile.ServiceIp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:wangqi
 * @Description:
 * @Date:Created in 2019/7/15
 * @Modified by: 平滑加权轮询
 */
public class RoundRobinV2 {

    public  static Map<String, Weight> weightMap = new HashMap<>();

    public static String getServer(){
        //计算总权重
        int totalWeight = ServiceIp.WEIGHT_LIST.values().stream().reduce(0, (w1,w2) -> w1 + w2);

        //初始化
        if(weightMap.isEmpty()){
            ServiceIp.WEIGHT_LIST.forEach((ip, value) -> {
                weightMap.put(ip, new Weight(ip, value, value));
            });
        }

        //找到当前map中最大的currentWeight
        Weight maxCurrentWeight = null;
        for(Weight weight : weightMap.values()){
            if(maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()){
                maxCurrentWeight = weight;
            }
        }

        //将最大的值减去总权重
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);
        //然后遍历加回原来的值，等待下次使用
        for(Weight weight : weightMap.values()){
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
        }

        return maxCurrentWeight.getIp();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }
}
