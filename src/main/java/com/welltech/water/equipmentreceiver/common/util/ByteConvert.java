package com.welltech.water.equipmentreceiver.common.util;

/**
 * 字节数组转换工具类
 */
public class ByteConvert {

    /**
     * 转换byte数组为int（小端）
     *
     * @return
     * @note 数组长度至少为2，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
     */
    public static int bytes2Int_LE(byte[] bytes) {
        int length = bytes.length;
        int iRst = (bytes[0] & 0xFF);
        if(length > 1){
            iRst |= (bytes[1] & 0xFF) << 8;
            if (bytes.length > 2) {
                iRst |= (bytes[2] & 0xFF) << 16;
                if (bytes.length > 3) {
                    iRst |= (bytes[3] & 0xFF) << 24;
                }
            }
        }
        return iRst;
    }

    /**
     * 转换byte数组为int（大端）
     *
     * @return
     * @note 数组长度至少为4，按小端方式转换，即传入的bytes是大端的，按这个规律组织成int
     */
    public static int bytes2Int_BE(byte[] bytes) {
        if (bytes.length < 4)
            return -1;
        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;

        return iRst;
    }

    /**
     * int转换为小端byte[]（高位放在高地址中）
     *
     * @param iValue
     * @return
     */
    public static byte[] int2Bytes_LE(int iValue) {
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte) (iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((iValue & 0xFF00) >> 8);
        // int 倒数第三个字节
        rst[2] = (byte) ((iValue & 0xFF0000) >> 16);
        // int 第一个字节
        rst[3] = (byte) ((iValue & 0xFF000000) >> 24);
        return rst;
    }

    /**
     * int转换为小端byte[]（高位放在高地址中）
     *
     * @param iValue
     * @return
     */
    public static byte[] int2Bytes_LE2(int iValue) {
        byte[] rst = new byte[2];
        // 先写int的最后一个字节
        rst[0] = (byte) (iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((iValue & 0xFF00) >> 8);
        return rst;
    }

    /**
     * 默认格式 0123
     * IEEE754
     *
     * @param bytes
     * @return
     */
    public static float bytes2Float_0123(byte[] bytes) {
        int num = bytes[0] & 0xff;
        num |= (bytes[1] & 0xff) << 8;
        num |= (bytes[2] & 0xff) << 16;
        num |= (bytes[3] & 0xff) << 24;
        return Float.intBitsToFloat(num);
    }

    public static float bytes2Float_1032(byte[] bytes) {
        int num = bytes[1] & 0xff;
        num |= (bytes[0] & 0xff) << 8;
        num |= (bytes[3] & 0xff) << 16;
        num |= (bytes[2] & 0xff) << 24;
        return Float.intBitsToFloat(num);
    }

    public static float bytes2Float_3210(byte[] bytes) {
        int num = bytes[3] & 0xff;
        num |= (bytes[2] & 0xff) << 8;
        num |= (bytes[1] & 0xff) << 16;
        num |= (bytes[0] & 0xff) << 24;
        return Float.intBitsToFloat(num);
    }

    public static float bytes2Float_2301(byte[] bytes) {
        int num = bytes[2] & 0xff;
        num |= (bytes[3] & 0xff) << 8;
        num |= (bytes[0] & 0xff) << 16;
        num |= (bytes[1] & 0xff) << 24;
        return Float.intBitsToFloat(num);
    }

    public static byte[] float2Bytes_0123(float f) {
        int num = Float.floatToIntBits(f);
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte) (num & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((num & 0xFF00) >> 8);
        // int 倒数第三个字节
        rst[2] = (byte) ((num & 0xFF0000) >> 16);
        // int 第一个字节
        rst[3] = (byte) ((num & 0xFF000000) >> 24);
        return rst;
    }

    public static double bytes2Double(byte[] bytes) {
        long num = bytes[0] & 0xff;
        num |= (long)(bytes[1] & 0xff) << 8;
        num |= (long)(bytes[2] & 0xff) << 16;
        num |= (long)(bytes[3] & 0xff) << 24;
        num |= (long)(bytes[4] & 0xff) << 32;
        num |= (long)(bytes[5] & 0xff) << 40;
        num |= (long)(bytes[6] & 0xff) << 48;
        num |= (long)(bytes[7] & 0xff) << 56;
        return Double.longBitsToDouble(num);
    }

    public static byte[] double2Bytes_0123(double d) {
        long num = Double.doubleToLongBits(d);
        byte[] rst = new byte[8];
        rst[0] = (byte) (num & 0xFF);
        rst[1] = (byte) ((num & 0xFF00l) >> 8);
        rst[2] = (byte) ((num & 0xFF0000l) >> 16);
        rst[3] = (byte) ((num & 0xFF000000l) >> 24);
        rst[4] = (byte) ((num & 0xFF00000000l) >> 32);
        rst[5] = (byte) ((num & 0xFF0000000000l) >> 40);
        rst[6] = (byte) ((num & 0xFF000000000000l) >> 48);
        rst[7] = (byte) ((num & 0xFF00000000000000l) >> 56);
        return rst;
    }

}
