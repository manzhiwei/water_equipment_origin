package com.welltech.water.equipmentreceiver.common.util;

/**
 * 字节数组工具类
 */
public class ByteUtils {

    /**
     * 字节数组转化为16进制数组字符串
     * 空格分开，一般用于打印内容
     * @param buffer
     * @return
     */
    public static String byte2hex(byte[] buffer){
        StringBuffer h = new StringBuffer();
        if(buffer != null){
            for(int i = 0; i < buffer.length; i++){
                String temp = Integer.toHexString(buffer[i] & 0xFF);
                if(temp.length() == 1){
                    temp = "0" + temp;
                }
                h.append(" ").append(temp);
            }
        }
        return h.toString();
    }

    /**
     * 字节数组转化为16进制数组字符串
     * 空格分开，一般用于打印内容
     * @param buffer
     * @return
     */
    public static String byte2hex(byte[] buffer, int offset, int length){
        StringBuffer h = new StringBuffer();
        if(buffer != null){
            for(int i = offset; i < offset + length; i++){
                String temp = Integer.toHexString(buffer[i] & 0xFF);
                if(temp.length() == 1){
                    temp = "0" + temp;
                }
                h.append(" ").append(temp);
            }
        }
        return h.toString();
    }

    /**
     * 字节数组转化为16进制数组字符串
     * @param bytes
     * @return
     */
    public static String byte2hexString(byte[] bytes){

        String hexStr =  "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for(int i=0;i<bytes.length;i++){
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));
            result += hex + "";  //这里可以去掉空格，或者添加0x标识符。
        }
        return result;
    }

    /**
     * 转置字节数组
     * @param bytes
     * @return
     */
    public static byte[] reverse(byte[] bytes){
        if(bytes == null){
            return null;
        }
        int length = bytes.length;
        if(length == 0){
            return bytes;
        }
        byte[] result = new byte[length];
        for(int i = 0; i < result.length; i ++){
            result[i] = bytes[length - 1 - i];
        }
        return result;
    }
}
