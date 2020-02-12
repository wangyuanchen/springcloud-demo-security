package com.zhao.cloud.gateway.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 处理IP地址工具
 *
 * @author zhaoliang
 */
public class IpAddrUtil {
    private static final String HEAD_X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HEAD_PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String HEAD_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String IP_UNKNOWN = "unknown";
    private static final String IP_V4_LOCAL = "127.0.0.1";
    private static final String IP_V6_LOCAL = "0:0:0:0:0:0:0:1";
    private static final String IP_SEPARATOR = ",";

    /**
     * 获取客户端真实ip
     *
     * @param request Http请求
     * @return 真实ip
     */
    public static String getClientIpAddrFromHttpRequest(HttpServletRequest request) {
        String ipAddress = request.getHeader(HEAD_X_FORWARDED_FOR);
        if (isInvalidIpAddr(ipAddress)) {
            ipAddress = request.getHeader(HEAD_PROXY_CLIENT_IP);
        }
        if (isInvalidIpAddr(ipAddress)) {
            ipAddress = request.getHeader(HEAD_WL_PROXY_CLIENT_IP);
        }
        if (isInvalidIpAddr(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(IP_V4_LOCAL) || ipAddress.equals(IP_V6_LOCAL)) {
                //根据网卡取本机配置的IP
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (null != inetAddress) {
                    ipAddress = inetAddress.getHostAddress();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.contains(IP_SEPARATOR)) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(IP_SEPARATOR));
        }
        return ipAddress;
    }

    private static boolean isInvalidIpAddr(String ipAddress) {
        return StringUtils.isEmpty(ipAddress) || IP_UNKNOWN.equalsIgnoreCase(ipAddress);
    }
}
