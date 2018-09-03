package com.welltech.water.equipmentreceiver.common.util;

import com.welltech.water.equipmentreceiver.common.bean.ProtocolDataSegment;
import com.welltech.water.equipmentreceiver.common.exception.BizProtocolException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 协议解析工具类
 */
public class ProtocolUtils {

    public static ProtocolDataSegment parseData(String data) throws BizProtocolException{
        ProtocolDataSegment result = new ProtocolDataSegment();
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isNotBlank(data)){
            String[] datas = data.split(";CP=&&");
            if( datas!= null) {
                if(datas.length >= 2){
                    map.put("CP", "&&" + datas[1]); //存储cp字段
                }
                for(String d : datas[0].split(";")){ //存储QN,ST,CN,PW,MN,Flag,PNUM,PNO
                    int index = d.indexOf('=');
                    int length = d.length();
                    if(index >= 0 && index < length - 1){
                        map.put(d.substring(0, index), d.substring(index + 1));
                    }
                }
            }
        }

        result.setQn(map.get("QN"));
        result.setSt(map.get("ST"));
        result.setCn(map.get("CN"));
        result.setPw(map.get("PW"));
        result.setMn(map.get("MN"));
        result.setFlag(map.get("Flag"));
        result.setPnum(map.get("PNUM"));
        result.setPno(map.get("PNO"));

        String cp = map.get("CP");
        if (cp.length() < 4
                || !cp.startsWith("&&")
                || !cp.endsWith("&&")) {
            throw new BizProtocolException("非法的指令参数CP：" + cp);
        }

        String cpData = cp.substring(2, cp.length() - 2);

        List<Map<String, String>> cpList = new ArrayList<>();

        String[] cpDatas = cpData.split(";");
        for(String d : cpDatas){
            Map<String, String> cpMap = new LinkedHashMap<>();
            String[] cpDatas2 = d.split(",");
            for(String d2: cpDatas2){
                int index = d2.indexOf('=');
                int length = d2.length();
                if(index >= 0 && index < length - 1){
                    cpMap.put(d2.substring(0, index), d2.substring(index + 1));
                }
            }
            cpList.add(cpMap);
        }
        result.setCp(cpList);
        return result;
    }

    public static String getData(ProtocolDataSegment segment) { //获得了除包尾之外的所有字符
        String data = "";
        if(data != null) {
            data = segment.toString();
        }
        // 计算CRC
        int crc = CRC16Utils.cac16CheckOut(data.toCharArray());
        String crcHex = "0000" + Integer.toHexString(crc).toUpperCase();
        int crcLength = crcHex.length();
        // 数据段长度
        int length = data.length();
        String lengthStr = "0000" + length;
        int lengthStrLength = lengthStr.length();
        String result = lengthStr.substring(lengthStrLength - 4, lengthStrLength) + data + crcHex.substring(crcLength - 4, crcLength);
        return "##" + result;
    }

}
