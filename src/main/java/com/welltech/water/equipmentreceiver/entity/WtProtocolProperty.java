package com.welltech.water.equipmentreceiver.entity;


import javax.persistence.*;
import java.util.Date;
/**
 * Created by YangYong on 2018/5/14.
 */
@Entity
@Table(name = "wt_protocol_property")
public class WtProtocolProperty {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String mn;

    @Column
    private String st;

    @Column
    private String pw;

    @Column
    private Date qt; //最新数据的时间

    @Column
    private Integer overtime;

    @Column
    private Integer recount;

    @Column//(columnDefinition = "INT default 60")
    private Integer rtdinterval;

    @Column
    private Integer mininterval;

    @Column
    private String ip;

    @Column
    private  Integer port;

    @Column
    private  Integer issurvive;

    @Column
    private Date restarttime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Date getQt() {
        return qt;
    }

    public void setQt(Date qt) {
        this.qt = qt;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public Integer getRecount() {
        return recount;
    }

    public void setRecount(Integer recount) {
        this.recount = recount;
    }

    //@Column(columnDefinition = "INT default 60",nullable=false)
    public Integer getRtdinterval() {
        return rtdinterval;
    }

    public void setRtdinterval(Integer rtdinterval) {
        this.rtdinterval = rtdinterval;
    }

    public Integer getMininterval() {
        return mininterval;
    }

    public void setMininterval(Integer mininterval) {
        this.mininterval = mininterval;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getIssurvive() {
        return issurvive;
    }

    public void setIssurvive(Integer issurvive) {
        this.issurvive = issurvive;
    }

    public Date getRestarttime() { return restarttime; }

    public void setRestarttime(Date restarttime) { this.restarttime = restarttime; }

    @Override
    public String toString() {
        return "WtProtocolProperty{" +
                "id=" + id +
                ", mn='" + mn + '\'' +
                ", st='" + st + '\'' +
                ", pw='" + pw + '\'' +
                ", qt=" + qt +
                ", overtime=" + overtime +
                ", recount=" + recount +
                ", rtdinterval=" + rtdinterval +
                ", mininterval=" + mininterval +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", issurvive=" + issurvive +
                ", restarttime=" + restarttime +
                '}';
    }
}
