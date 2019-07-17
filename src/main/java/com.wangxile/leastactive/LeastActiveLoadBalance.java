package com.wangxile.leastactive;

import com.wangxile.ServiceIp;
import com.wangxile.random.WeightRandomV2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Author:wangqi
 * @Description:
 * @Date:Created in 2019/7/17
 * @Modified by: 最近最少活跃
 */
public class LeastActiveLoadBalance {

    private static String getServer(){
        //找出当前活跃数最小的服务器
        Optional<Integer> minValueOptional = ServiceIp.ACTIVITY_LIST.values().stream().min(Comparator.naturalOrder());
        if(minValueOptional.isPresent()){
            List<String> minActivityIps = new ArrayList<>();
            //遍历获取与最小活跃数相等的ip
            ServiceIp.ACTIVITY_LIST.forEach((ip, activity) -> {
                if(activity.equals(minValueOptional.get())){
                    minActivityIps.add(ip);
                }
            });

            //最近最小活跃数的ip有多个,则根据权重来选，权重大的优先
            if(minActivityIps.size() > 1){
                return WeightRandomV2.getServer();
            }
            return  minActivityIps.get(0);
        } else {
            return (String )ServiceIp.WEIGHT_LIST.keySet().toArray()[new java.util.Random().nextInt(ServiceIp.WEIGHT_LIST.size())];
        }
    }
}
