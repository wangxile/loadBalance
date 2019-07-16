package com.wangxile.roundrobin;

import com.wangxile.ServiceIp;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:wangqi
 * @Description:
 * @Date:Created in 2019/7/15
 * @Modified by:轮询
 */
public class RoundRobin {

    public  static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static String getServer(){
        if(atomicInteger.get() > ServiceIp.LIST.size()){
            atomicInteger.set(0);
        }
        return ServiceIp.LIST.get(atomicInteger.getAndIncrement());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }
}
