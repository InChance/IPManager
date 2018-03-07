package utils;

import java.util.regex.Pattern;

public class IPUtils3 {

    // IpV4的正则表达式，用于判断IpV4地址是否合法
    private static final String IPV4_REGEX = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";

    // 系统子网掩码，它与ip组成一个地址
    private int mask;

    // 1代表A类，2代表B类，3代表C类；4代表其它类型
    public final static int IP_A_TYPE = 1;
    public final static int IP_B_TYPE = 2;
    public final static int IP_C_TYPE = 3;
    public final static int IP_OTHER_TYPE = 4;


    // A类地址范围：1.0.0.1---126.255.255.254
    private static int[] IpATypeRange;
    // B类地址范围：128.0.0.1---191.255.255.254
    private static int[] IpBTypeRange;
    // C类地址范围：192.168.0.0～192.168.255.255
    private static int[] IpCTypeRange;

    // A,B,C类地址的默认mask
    private static int DefaultIpAMask;
    private static int DefaultIpBMask;
    private static int DefaultIpCMask;


    // 初始化
    static
    {
        IpATypeRange = new int[2];
        IpATypeRange[0] = getIpV4Value("1.0.0.1");
        IpATypeRange[1] = getIpV4Value("126.255.255.254");

        IpBTypeRange = new int[2];
        IpBTypeRange[0] = getIpV4Value("128.0.0.1");
        IpBTypeRange[1] = getIpV4Value("191.255.255.254");

        IpCTypeRange = new int[2];
        IpCTypeRange[0] = getIpV4Value("192.168.0.0");
        IpCTypeRange[1] = getIpV4Value("192.168.255.255");

        DefaultIpAMask = getIpV4Value("255.0.0.0");
        DefaultIpBMask = getIpV4Value("255.255.0.0");
        DefaultIpCMask = getIpV4Value("255.255.255.0");
    }


    public static int getIpV4Value(String ipOrMask)
    {
        byte[] addr = getIpV4Bytes(ipOrMask);
        int address1  = addr[3] & 0xFF;
        address1 |= ((addr[2] << 8) & 0xFF00);
        address1 |= ((addr[1] << 16) & 0xFF0000);
        address1 |= ((addr[0] << 24) & 0xFF000000);
        return address1;
    }

    public static byte[] getIpV4Bytes(String ipOrMask)
    {
        try
        {
            String[] addrs = ipOrMask.split("\\.");
            int length = addrs.length;
            byte[] addr = new byte[length];
            for (int index = 0; index < length; index++)
            {
                addr[index] = (byte) (Integer.parseInt(addrs[index]) & 0xff);
            }
            System.err.println(addr);
            return addr;
        }
        catch (Exception e)
        {
        }
        return new byte[4];
    }

    public static boolean checkSameSegment(String ip1,String ip2)
    {
        // 判断IPV4是否合法
        if(!ipV4Validate(ip1))
        {
            return false;
        }
        if(!ipV4Validate(ip2))
        {
            return false;
        }
        int ipValue1 = getIpV4Value(ip1);
        int ipValue2 = getIpV4Value(ip2);
        int mask1 = getDefaultMaskValue(ip1);
        int mask2 = getDefaultMaskValue(ip2);
        System.out.println(ipValue1);
        System.out.println(ipValue2);
        System.out.println(mask1);
        System.out.println(mask2);
        System.out.println(mask1 & ipValue1);
        System.out.println(mask2 & ipValue2);
        return (mask1 & ipValue1) == (mask2 & ipValue2);
    }


    /**
     * 判断ipV4或者mask地址是否合法，通过正则表达式方式进行判断
     * @param ipv4
     */
    public static boolean ipV4Validate(String ipv4)  {
        return ipv4Validate(ipv4,IPV4_REGEX);
    }

    private static boolean ipv4Validate(String addr,String regex)
    {
        if(addr == null)
        {
            return false;
        }
        else
        {
            return Pattern.matches(regex, addr.trim());
        }
    }


    /**
     * 检测ipV4 的类型，包括A类，B类，C类，其它（C,D和广播）类等
     * @param ipV4
     * @return     返回1代表A类，返回2代表B类，返回3代表C类；返回4代表D类
     */
    public static int checkIpV4Type(String ipV4)
    {
        int inValue = getIpV4Value(ipV4);
        if(inValue >= IpCTypeRange[0] && inValue <= IpCTypeRange[1])
        {
            return IP_C_TYPE;
        }
        else if(inValue >= IpBTypeRange[0] && inValue <= IpBTypeRange[1])
        {
            return IP_B_TYPE;
        }
        else if(inValue >= IpATypeRange[0] && inValue <= IpATypeRange[1])
        {
            return IP_A_TYPE;
        }
        return IP_OTHER_TYPE;
    }


    /**
     * 获取默认mask值，如果IpV4是A类地址，则返回{@linkplain #DefaultIpAMask}，
     * 如果IpV4是B类地址，则返回{@linkplain #DefaultIpBMask}，以此类推
     * @param anyIpV4 任何合法的IpV4
     * @return mask 32bit值
     */
    public static int getDefaultMaskValue(String anyIpV4)
    {
        int checkIpType = checkIpV4Type(anyIpV4);
        int maskValue = 0;
        switch (checkIpType)
        {
            case IP_C_TYPE:
                maskValue = DefaultIpCMask;
                break;
            case IP_B_TYPE:
                maskValue = DefaultIpBMask;
                break;
            case IP_A_TYPE:
                maskValue = DefaultIpAMask;
                break;
            default:
                maskValue = DefaultIpCMask;
        }
        return maskValue;
    }


    public static void main(String[] args) {
        System.out.println(IPUtils3.getIpV4Value("192.168.0.1"));
    }

}
