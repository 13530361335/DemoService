package com.joker.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Joker Jing
 * @date: 2019/8/22
 */
@Slf4j
public class IpUtil {

    /**
     * @param ipAddress
     * @return
     */
    public static long ipToLong(String ipAddress) {
        long result = 0;
        String[] ipAddressInArray = ipAddress.split("\\.");
        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            // left shifting 24,16,8,0 and bitwise OR
            // 1. 192 << 24
            // 1. 168 << 16
            // 1. 1 << 8
            // 1. 2 << 0
            result |= ip << (i * 8);
        }
        return result;
    }

    /**
     * @param ip
     * @return
     */
    public static String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            result.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                result.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return result.toString();
    }

    /**
     * @param ip
     * @return
     */
    public static String longToIp2(long ip) {
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
    }

    /**
     * 获取当前机器的IP
     *
     * @return
     */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enumNic = NetworkInterface.getNetworkInterfaces();
                 enumNic.hasMoreElements(); ) {
                NetworkInterface ifc = enumNic.nextElement();
                if (ifc.isUp()) {
                    for (Enumeration<InetAddress> enumAddr = ifc.getInetAddresses();
                         enumAddr.hasMoreElements(); ) {
                        InetAddress address = enumAddr.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 对比方法
     *
     * @param serviceUrl
     * @return
     */
    public static boolean ipCompare(List<URI> serviceUrl) {
        try {
            String localIpStr = IpUtil.getIpAddress();
            long localIpLong = IpUtil.ipToLong(localIpStr);
            int size = serviceUrl.size();
            if (size == 0) {
                return false;
            }

            Long[] longHost = new Long[size];
            for (int i = 0; i < serviceUrl.size(); i++) {
                String host = serviceUrl.get(i).getHost();
                longHost[i] = IpUtil.ipToLong(host);
            }
            Arrays.sort(longHost);
            if (localIpLong == longHost[0]) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

}