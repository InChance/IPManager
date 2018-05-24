package com.leo.utils;

import com.leo.model.IPDetailDto;
import org.springframework.util.Assert;

public class IPMaskUtil {

    public String getIPMask(String ipAddr, String maskAddr) {
        return "";
    }

    /**
     * 通过“掩码位”获取“子网掩码”
     * @param maskBit byte
     * @return String
     */
    public static String getMask(byte maskBit){
        if(maskBit == 1)
            return "128.0.0.0";
        else if(maskBit == 2)
            return "192.0.0.0";
        else if(maskBit == 3)
            return "224.0.0.0";
        else if(maskBit == 4)
            return "240.0.0.0";
        else if(maskBit == 5)
            return "248.0.0.0";
        else if(maskBit == 6)
            return "252.0.0.0";
        else if(maskBit == 7)
            return "254.0.0.0";
        else if(maskBit == 8)
            return "255.0.0.0";
        else if(maskBit == 9)
            return "255.128.0.0";
        else if(maskBit == 10)
            return "255.192.0.0";
        else if(maskBit == 11)
            return "255.224.0.0";
        else if(maskBit == 12)
            return "255.240.0.0";
        else if(maskBit == 13)
            return "255.248.0.0";
        else if(maskBit == 14)
            return "255.252.0.0";
        else if(maskBit == 15)
            return "255.254.0.0";
        else if(maskBit == 16)
            return "255.255.0.0";
        else if(maskBit == 17)
            return "255.255.128.0";
        else if(maskBit == 18)
            return "255.255.192.0";
        else if(maskBit == 19)
            return "255.255.224.0";
        else if(maskBit == 20)
            return "255.255.240.0";
        else if(maskBit == 21)
            return "255.255.248.0";
        else if(maskBit == 22)
            return "255.255.252.0";
        else if(maskBit == 23)
            return "255.255.254.0";
        else if(maskBit == 24)
            return "255.255.255.0";
        else if(maskBit == 25)
            return "255.255.255.128";
        else if(maskBit == 26)
            return "255.255.255.192";
        else if(maskBit == 27)
            return "255.255.255.224";
        else if(maskBit == 28)
            return "255.255.255.240";
        else if(maskBit == 29)
            return "255.255.255.248";
        else if(maskBit == 30)
            return "255.255.255.252";
        else if(maskBit == 31)
            return "255.255.255.254";
        else if(maskBit == 32)
            return "255.255.255.255";
        return "";
    }

    public static int getMaskbitByMaskAddress(String mask){
        Assert.isTrue(mask != null && mask.contains("."), "子网掩码格式错误，mask=" + mask);
        String binaryMask = "";
        for (String str : mask.split("\\.")){
            int maskTmp = Integer.parseInt(str);
            binaryMask = binaryMask.concat(Integer.toBinaryString(maskTmp));
        }
        return getMaskbit(binaryMask);
    }

    /**
     * 判断IP是否合法
     * @param ip string
     * @return  合法 true | 非法 false
     */
    public static boolean isValidIP(String ip){
        if (ip == null || "".equals(ip)) {
            return false;
        }
        if (!ip.contains(".")) {
            return false;
        }
        String[] ipSplit = ip.split("\\.");
        int ipNum = 0;
        if(ipSplit.length != 4) {
            return false;
        }
        for (int i = 0; i < ipSplit.length; i++) {
            try {
                ipNum = Integer.parseInt(ipSplit[i]);
            }catch (Exception e){
                return false;
            }
            if (ipNum < 0 || ipNum > 255) {
                return false;
            }
            if (i == 0){
                if (ipNum == 0 || ipNum == 255) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断子网掩码是否合法
     *
     * @param mask string
     * @return boolean
     */
    public static boolean isValidMask(String mask) {
        int maskNum = 0;
        int maskBit = 0;
        //十进制掩码
        if(!mask.contains(".")) {
            try {
                maskBit = Byte.parseByte(mask);
            }catch(Exception e) {
                return false;
            }
            if (maskBit > 31 || maskBit < 1) {
                return false;
            }
            return true;
        }
        //IP格式掩码
        String[] maskSplit = mask.split("\\.");
        String maskBinString = "";
        if(maskSplit.length != 4)
            return false;
        //将大于128的4个掩码段连成2进制字符串
        for(int i=0; i < maskSplit.length; i++) {
            try {
                maskNum = Integer.parseInt(maskSplit[i]);
            }catch(Exception e) {
                return false;
            }
            //首位为0，非法掩码
            if(i == 0 && Integer.numberOfLeadingZeros(maskNum) == 32)
                return false;
            //非0或128～255之间，非法掩码
            if(Integer.numberOfLeadingZeros(maskNum) != 24)
                if(Integer.numberOfLeadingZeros(maskNum) != 32)
                    return false;
            //将大于128的掩码段连接成完整的二进制字符串
            maskBinString = maskBinString.concat(Integer.toBinaryString(maskNum));
        }
        //二进制掩码字符串，包含非连续1时，是非法掩码
        if(maskBinString.indexOf("0") < maskBinString.lastIndexOf("1"))
            return false;
        //剩下的就是合法掩码
        return true;
    }

    // 识别掩码位数
    public static int getMaskbit(String binaryMask) {
        return binaryMask.lastIndexOf("1") + 1;
    }

    /** 通过IP和掩码计算网段详细信息 */
    public static IPDetailDto calculateIPMask(String ipAddr, String ipMask){
        // 计算十进制子网掩码
        if(!ipMask.contains(".")){
            ipMask = getMask(Byte.parseByte(ipMask));
        }
        String[] ipSplit   = ipAddr.split("\\.");
        String[] maskSplit = ipMask.split("\\.");

        IPDetailDto dto = new IPDetailDto();
        String netIp      = "";
        String mask       = "";
        String broadcast  = "";
        String startIp    = "";
        String endIp      = "";
        String binaryMask = "";

        // 循环计算网段信息
        for (int i = 0; i < 4; i++){
            int ipTmp   = Integer.parseInt(ipSplit[i]);
            int maskTmp = Integer.parseInt(maskSplit[i]);
            // 地址类型
            if( i == 0 ){
                if( ipTmp == 127 ){
                    dto.setIpType("回环地址");
                } else if( ipTmp < 127 ){
                    dto.setIpType("A类地址");
                } else if( ipTmp < 192 ){
                    dto.setIpType("B类地址");
                } else if( ipTmp < 224 ){
                    dto.setIpType("C类地址");
                } else if( ipTmp < 240 ){
                    dto.setIpType("D类地址（组播）");
                } else if( ipTmp < 255 ){
                    dto.setIpType("E类地址（保留）");
                }
            }
            // 网络地址
            netIp = netIp.concat(Integer.toString(ipTmp & maskTmp)).concat(".");
            // 子网掩码
            mask  = mask.concat(Integer.toString(maskTmp)).concat(".");
            // 广播地址
            broadcast = broadcast.concat(Integer.toString(~maskTmp + 256 | ipTmp )).concat(".");
            // 可分配主机地址
            if( i < 3 ) {
                startIp = startIp.concat(Integer.toString(ipTmp & maskTmp)).concat(".");
                endIp   = endIp.concat(Integer.toString(~maskTmp + 256 | ipTmp )).concat(".");
            } else if( i == 3 ) {
                if( maskTmp < 254 ) {
                    startIp = startIp.concat(Integer.toString((ipTmp & maskTmp) + 1));
                    endIp   = endIp.concat(Integer.toString((~maskTmp + 256 | ipTmp) - 1));
                } else {
                    startIp = "无";
                    endIp   = "无";
                }
            }
            binaryMask = binaryMask.concat(Integer.toBinaryString(maskTmp));
        }
        dto.setNetAddress(netIp.substring(0,netIp.length() - 1));
        dto.setMaskAddress(mask.substring(0,mask.length() - 1));
        dto.setBroadcast(broadcast.substring(0,broadcast.length() - 1));
        dto.setFirstUsable(startIp);
        dto.setLastUsable(endIp);
        dto.setUsableCount((int)Math.pow(2, 32 - getMaskbit(binaryMask)) - 2);
        return dto;
    }

    /** 判断两个IP是否在同一个网段 */
    public static boolean checkSameSegment(String mask, String ip1, String ip2){
        int ipMask   = getIpV4Value(mask);
        int ipValue1 = getIpV4Value(ip1);
        int ipValue2 = getIpV4Value(ip2);
        return (ipMask & ipValue1) == (ipMask & ipValue2);
    }

    public static int getIpV4Value(String ipOrMask)    {
        byte[] addr = getIpV4Bytes(ipOrMask);
        int address1  = addr[3] & 0xFF;
        address1 |= ((addr[2] << 8) & 0xFF00);
        address1 |= ((addr[1] << 16) & 0xFF0000);
        address1 |= ((addr[0] << 24) & 0xFF000000);
        return address1;
    }

    public static byte[] getIpV4Bytes(String ipOrMask) {
        try{
            String[] addrs = ipOrMask.split("\\.");
            int length = addrs.length;
            byte[] addr = new byte[length];
            for (int index = 0; index < length; index++) {
                addr[index] = (byte) (Integer.parseInt(addrs[index]) & 0xff);
            }
            return addr;
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

}
