package com.welltech.water.equipmentreceiver.common.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProtocolDataSegment implements Serializable{

    /**
     * 请求编码
     */
    private String qn;

    /**
     * 系统编码
     */
    private String st;

    /**
     * 命令编码
     */
    private String cn;

    /**
     * 访问密码
     */
    private String pw;

    /**
     * 设备唯一标识
     */
    private String mn;

    /**
     * 拆分包及应答标志
     */
    private String flag;

    /**
     * 总包数
     */
    private String pnum;

    /**
     * 包号
     */
    private String pno;

    /**
     * 指令参数
     */
    private List<Map<String, String>> cp;

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public List<Map<String, String>> getCp() {
        return cp;
    }

    public void setCp(List<Map<String, String>> cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("QN=").append(qn).append(";");
        sb.append("ST=").append(st).append(";");
        sb.append("CN=").append(cn).append(";");
        sb.append("PW=").append(pw).append(";");
        sb.append("MN=").append(mn).append(";");
        sb.append("Flag=").append(flag).append(";");
        if(StringUtils.isNotBlank(pnum)) {
            sb.append("PNUM=").append(pnum).append(";");
        }
        if(StringUtils.isNotBlank(pno)) {
            sb.append("PNO=").append(pno).append(";");
        }
        sb.append("CP=&&");
        if(cp != null){
            Iterator<Map<String, String>> iterator = cp.iterator();
            while(iterator.hasNext()) {
                Map<String, String> map = iterator.next();
                Set<Map.Entry<String, String>> set = map.entrySet();
                Iterator<Map.Entry<String, String>> setIt = set.iterator();

                while (setIt.hasNext()) {
                    Map.Entry<String, String> entry = setIt.next();
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                    if(setIt.hasNext()) {
                        sb.append(",");
                    }
                }

                if(iterator.hasNext()) {
                    sb.append(";");
                }
            }
        }
        sb.append("&&");
        return sb.toString();
    }
}
