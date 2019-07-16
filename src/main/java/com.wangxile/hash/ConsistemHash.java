package com.wangxile.hash;

import com.wangxile.ServiceIp;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author:wangqi
 * @Description:
 * @Date:Created in 2019/7/16
 * @Modified by:
 */
public class ConsistemHash {

    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();
    private static final int VIRTUAL_NODES = 160;

    static {
        // 对每个真实节点添加虚拟节点,虚拟节点会根据哈希算法进行散列
        for(String ip : ServiceIp.LIST){
            for(int i = 0; i < VIRTUAL_NODES; i++){
                int hash = getHash(ip + "VN" + i);
                virtualNodes.put(hash, ip);
            }
        }
    }

    private static String getServer(String client){
        int hash = getHash(client);

        // 得到大于该hash值得排好序的map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);

        // 取到第一个大于的值
        Integer nodeIndex = subMap.firstKey();

        //如果找不到大于他的节点，返回根节点
        if(nodeIndex == null){
            nodeIndex = virtualNodes.firstKey();
        }

        //返回
        return virtualNodes.get(nodeIndex);
    }

    private static int getHash(String str){
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for(int i = 0; i < str.length(); i++){
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        //如果计算出来的值为负数则取其绝对值
        if(hash < 0){
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer("client" + 1));
        }
    }
}
