package utils;

import lombok.Data;

import java.util.List;

public class IPUtils {

    @Data
    private static class Netword{
        private int canUseIp;       // 可用IP
        private String mask;        // 子网掩码
        private String net;         // 网络
        private String firstIp;     // 第一可用
        private String lastIp;      // 最后可用
        private String broadcast;   // 广播
        private List<String> ips;   // 可用IP列表
    }

    public static void main(String[] args){
        String ip = "192.168.0.1";
        int maskBit = 21;
        Netword net = IPUtils.calculate(ip, maskBit);
        System.out.println(net.getMask());
    }


    private static Netword calculate(String ip, int maskBit) {
        Netword netword = new IPUtils.Netword();
        String mask = maskBit2Str(maskBit);
        netword.setMask(mask);

        return netword;
    }

    private static String maskBit2Str(int maskBit) {
        byte[] maskTmp = new byte[]{0x00,0x00,0x00,0x00};
        for (int i = 0; i < 32; i++) {
            int index = i / 8;
            if (i <= maskBit) {
                int bei = i * 10;
                maskTmp[index] = (byte) (maskTmp[index] * bei + 1);
            }
        }
        for (int i = 0; i < maskTmp.length; i++) {
            System.out.println("nimenhao");
        }
        return "";
    }

    private static Netword calculate(String ip, String mask) {

        return new IPUtils.Netword();
    }


}
