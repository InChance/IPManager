package com.leo.utils;

/*
* @author 卢伟
* @version 1.0 2008-03-06
*
*/
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class IPMask {

    public static void main(String args[]) {
        IPMaskFrame frame = new IPMaskFrame();
        frame.init();
    }
}

/**
 * 主界面
 */
class IPMaskFrame extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    //创建窗口对象
    private JLabel addrLabel = new JLabel("IP地址：");
    private JTextField addrField = new JTextField(9);
    private JLabel maskLabel = new JLabel("子网掩码：");
    private JTextField maskField = new JTextField(9);
    private JPanel outputPanel = new JPanel();
    private JTextArea outputArea = new JTextArea(7, 31);
    private JButton calculateButton = new JButton("计算");
    private JButton copyButton = new JButton("复制");
    private JButton resetButton = new JButton("重置");
    private JButton helpButton = new JButton("帮助");
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //关于对话框
    private JLabel about = new JLabel("<html>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp子网掩码计算器ver1.2.5 (2008-03-9)<br><br>该工具采用的计算公式是：<br>&nbsp&nbsp&nbsp&nbsp网络地址 ＝ IP地址 & 子网掩码<br>&nbsp&nbsp&nbsp&nbsp广播地址 ＝ ～广播地址＋256 | IP地址<br>&nbsp&nbsp&nbsp&nbsp可用主机数 ＝ pow(2, 主机位数)-2<br>如果口算，可用如下快速计算公式：<br>&nbsp&nbsp&nbsp&nbsp A ＝ 256 －异常掩码<br>&nbsp&nbsp&nbsp&nbsp B ＝ 异常掩码对应的IP<br>&nbsp&nbsp&nbsp&nbsp网络地址 ＝ A*n（取最接近于B但小于B的值）<br>&nbsp&nbsp&nbsp&nbsp广播地址 ＝ 网络地址＋A－1<br><br>        &nbsp&nbsp&nbsp&nbsp作者：地图<br>        &nbsp&nbsp&nbsp&nbsp邮件：godmap@sohu.com</html>");
    private JLabel url = new JLabel(("<html>        &nbsp&nbsp&nbsp&nbsp博客：<a href=\"http://hi.baidu.com/godmap\">http://hi.baidu.com/godmap</a><br></html>"));
    private JPanel aboutPanel = new JPanel();
    private String os = System.getProperty ("os.name");

    //初始化窗口
    public void init() {
        //设置窗口组件基本属性
        this.setTitle("子网掩码计算器V1.2.5");
        addrField.setToolTipText("格式：172.168.1.2 ……");
        addrField.setActionCommand("addr");
        addrField.addActionListener(this);
        addrField.addKeyListener(this);
        maskField.setToolTipText("格式：1～31或255.255.255.0 ……");
        maskField.setActionCommand("mask");
        maskField.addActionListener(this);
        maskField.addKeyListener(this);
        calculateButton.setToolTipText("计算结果。");
        calculateButton.setActionCommand("calculate");
        calculateButton.addActionListener(this);
        copyButton.setToolTipText("将计算结果复制到系统剪贴板。");
        copyButton.setActionCommand("copy");
        copyButton.addActionListener(this);
        resetButton.setToolTipText("将输入框清零。");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(this);
        helpButton.setToolTipText("更多帮助。");
        helpButton.setActionCommand("help");
        helpButton.addActionListener(this);
        outputArea.setToolTipText("Ctrl+A/Ctrl+C复制计算结果。");
        outputArea.setEditable(false);
        outputPanel.setBorder(new TitledBorder("计算结果"));
        outputPanel.add(outputArea);
        JScrollPane scrollBar = new JScrollPane(outputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputPanel.add(scrollBar);
        //设置关于窗口属性
        url.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        url.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (os.startsWith ("Windows")) {
                    try {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://hi.baidu.com/godmap");
                    } catch (Exception urlException) {
                        System.out.println("启动浏览器错误:".concat(urlException.toString().concat("\n请手动启动浏览器访问：http://hi.baidu.com/godmap")));
                    }
                }
                else
                    try {
                        Runtime.getRuntime().exec("mozila http://hi.baidu.com/godmap");
                    } catch (Exception urlException) {
                        System.out.println("启动浏览器错误:".concat(urlException.toString().concat("\n请手动启动浏览器访问：http://hi.baidu.com/godmap")));
                    }
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
        });
        GroupLayout aboutLayout = new GroupLayout(aboutPanel);
        aboutLayout.setHorizontalGroup(aboutLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(about)
                .addComponent(url));
        aboutLayout.setVerticalGroup(aboutLayout.createSequentialGroup()
                .addComponent(about)
                .addComponent(url));
        aboutPanel.setLayout(aboutLayout);
        aboutPanel.add(about);
        aboutPanel.add(url);
        //添加布局管理器
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(groupLayout.createParallelGroup()
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(addrLabel)
                                        .addComponent(addrField)
                                        .addComponent(maskLabel)
                                        .addComponent(maskField))
                                .addComponent(outputPanel))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(calculateButton)
                                .addComponent(copyButton)
                                .addComponent(resetButton)
                                .addComponent(helpButton))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup()
                                .addComponent(addrLabel)
                                .addComponent(addrField)
                                .addComponent(maskLabel)
                                .addComponent(maskField))
                        .addComponent(outputPanel)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(calculateButton)
                                .addComponent(copyButton)
                                .addComponent(resetButton)
                                .addComponent(helpButton))
        );
        getContentPane().setLayout(groupLayout);
        //输出窗口
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation((screenSize.width-getWidth())/2, (screenSize.height-getHeight())/2);
        setVisible(true);
    }

    //事件监听
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("calculate")) {
            outputArea.setText(this.getIPMask(deSpace(addrField), deSpace(maskField)));
            outputArea.setCaretPosition(0);
        }
        else if(e.getActionCommand().equals("copy")) {
            this.outputArea.selectAll();
            this.outputArea.copy();
        }
        else if(e.getActionCommand().equals("reset")) {
            this.addrField.setText("");
            this.maskField.setText("");
        }
        else if(e.getActionCommand().equals("help")) {
            JOptionPane.showMessageDialog(null, aboutPanel, "关于", JOptionPane.INFORMATION_MESSAGE );
        }
        else ;
    }

    //键盘监听
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==10) {
            this.calculateButton.doClick();
        }
    }
    public void keyTyped(KeyEvent e) {
    }

    //主方法
    public String getIPMask(String ipAddr, String maskAddr) {
        String outputMaskInfo = "非法IP或子网掩码地址。";
        //非法IP或子网掩码地址，不进行计算。
        if(!isValidIP(ipAddr) || !isValidMask(maskAddr))
            return outputMaskInfo;
        //合法IP、子网掩码，开始计算。
        if(maskAddr.indexOf(".") == -1)
            maskAddr = getMask(Byte.parseByte(maskAddr));
        String[] ipSplit = {"0","0","0","0"};
        String[] maskSplit = {"0","0","0","0"};
        ipSplit = ipAddr.split("\\.");
        maskSplit = maskAddr.split("\\.");
        String ip = "";
        String mask = "";
        String netIP = "";
        String broadcastIP = "";
        String startIP = "";
        String endIP = "";
        String binaryMask = "";
        String addrType = "";
        //String hostIP = "";
        int hostNum = 0;
        //int subNetNum = 0;
        int subNetMaxNum = 0;

        for(int i = 0; i < 4; i++) {
            int ipTemp = Integer.parseInt(ipSplit[i]);
            int maskTemp = Integer.parseInt(maskSplit[i]);
            //地址类型
            if(i == 0) {
                if(ipTemp == 127)
                    addrType = "回环地址：";
                else if(ipTemp < 127)
                    addrType = "A类地址：";
                else if(ipTemp < 192)
                    addrType = "B类地址：";
                else if(ipTemp < 224)
                    addrType = "C类地址：";
                else if(ipTemp < 240)
                    addrType = "D类（组播）地址：";
                else if(ipTemp < 255)
                    addrType = "E类（保留）地址：";
            }
            //用户输入的IP
            ip = ip.concat(Integer.toString(ipTemp)).concat(".");
            //用户输入的子网掩码
            mask = mask.concat(Integer.toString(maskTemp)).concat(".");
            //网络地址
            netIP = netIP.concat(Integer.toString(ipTemp & maskTemp)).concat(".");
            //广播地址
            broadcastIP = broadcastIP.concat(Integer.toString(~maskTemp+256 | ipTemp)).concat(".");
            //主机地址
            //hostIP = hostIP.concat(Integer.toString(~maskTemp & ipTemp)).concat(".");
            //可分配主机地址
            if(i < 3) {
                startIP = startIP.concat(Integer.toString(ipTemp & maskTemp)).concat(".");
                endIP = endIP.concat(Integer.toString(~maskTemp+256 | ipTemp)).concat(".");
            }
            else if(i == 3) {
                if(maskTemp != 254) {
                    startIP = startIP.concat(Integer.toString((ipTemp & maskTemp) + 1)).concat(".");
                    endIP = endIP.concat(Integer.toString((~maskTemp+256 | ipTemp) - 1)).concat(".");
                }
                else {
                    startIP = "无.";
                    endIP = "无.";
                }
            }
            //生成连续的二进制子网掩码，以计算可用主机数
            binaryMask = binaryMask.concat(Integer.toBinaryString(maskTemp));
        }
        //可用主机数
        hostNum = (int)Math.pow(2, 32 - this.getMaskBit(binaryMask)) - 2;
        //可划分子网数
        subNetMaxNum = (int)(32-getMaskBit(binaryMask)-2 > 0 ? Math.pow(2, 32-getMaskBit(binaryMask)-2):0);
        //计算子网
        outputMaskInfo = addrType.concat(ip.substring(0, ip.length() - 1)).concat("/").concat(mask.substring(0, mask.length() - 1))
                .concat("的\n网络地址是：").concat(netIP.substring(0, netIP.length() - 1))
                .concat("\n广播地址是：".concat(broadcastIP.substring(0, broadcastIP.length() - 1))
                        .concat("\n可分配主机地址包括：").concat(startIP.substring(0, startIP.length() - 1)).concat("～").concat(endIP.substring(0, endIP.length() - 1))
                        .concat("\n可用主机数共：").concat(Integer.toString(hostNum)).concat("台")
                        .concat("\n最多可划分：")    .concat(Integer.toString(subNetMaxNum).concat("个子网\n")));
        //.concat(this.getIPMask(false)));
        return outputMaskInfo;
    }

    //计算子网分配方案
    public String getSubNet() {
        String subNetInfo = "子网分配方案如下：\n";
        for(int i=0; i<this.getIPMask("","").indexOf("a"); i++) {

        }
        return subNetInfo;
    }

    //转换十进制掩码为IP地址格式掩码
    public String getMask(byte maskBit) {
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

    //判断IP是否合法
    public boolean isValidIP(String ip) {
        if(ip.indexOf(".") == -1)
            return false;
        String[] ipSplit = ip.split("\\.");
        int ipNum = 0;
        if (ipSplit.length != 4)
            return false;
        for (int i = 0; i < ipSplit.length; i++) {
            try {
                ipNum = Integer.parseInt(ipSplit[i]);
            }catch(Exception e) {
                return false;
            }
            if(ipNum < 0 || ipNum > 255)
                return false;
            if(i == 0)
                if(ipNum == 0 || ipNum == 255)
                    return false;
        }
        return true;
    }

    //判断子网掩码是否合法
    public boolean isValidMask(String mask) {
        int maskNum = 0;
        int maskBit = 0;
        //十进制掩码
        if(mask.indexOf(".") == -1) {
            try {
                maskBit = Byte.parseByte(mask);
            }catch(Exception e) {
                return false;
            }
            if(maskBit > 31 || maskBit < 1) {
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
        for(int i=0; i<maskSplit.length; i++) {
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

    //识别掩码位数
    public int getMaskBit(String binaryMask) {
        return binaryMask.lastIndexOf("1") + 1;
    }

    //过滤空格
    public String deSpace(JTextField textField) {
        String curStr = null;
        String outStr = "";
        for (int i = 0; i < textField.getText().length(); i++) {
            curStr = textField.getText().substring(i, i + 1);
            if (!curStr.equals(" ")) {
                outStr += curStr;
            }
        }
        return outStr;
    }

}