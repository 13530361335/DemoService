package com.joker.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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

    private static final int THREE = 3;
    private static final int FOUR = 4;

    /**
     * @param ipAddress
     * @return
     */
    public static long ipToLong(String ipAddress) {
        long result = 0;
        String[] ips = ipAddress.split("\\.");
        // 用点分开的每个字节的数值范围是0~255,计算时，使用位移计算
        for (int i = 0; i < ips.length; i++) {
            long ip = Long.parseLong(ips[i]);
            int moveLeftCount = (ips.length - 1 - i) * 8;
            result |= ip << moveLeftCount;
        }
        return result;
    }

    public static boolean isIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * @param ip
     * @return
     */
    public static String longToIp(long ip) {
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