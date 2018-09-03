package com.welltech.water.equipmentreceiver.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by YangYong on 2018/5/14.
 */
@Entity
@Table(name = "wt_protocol_cmd")
public class WtProtocolCmd {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    public String mn;

    @Column
    private String cn;

    @Column
    private String st;

    @Column
    private String pw;

    @Column
    private Integer flag;

    @Column
    private String cmddata;

    @Column
    private String qn;

    @Column
    private Integer isexe;

    @Column
    private  String recv_time;

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

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCmddata() {
        return cmddata;
    }

    public void setCmddata(String cmddata) {
        this.cmddata = cmddata;
    }

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public Integer getIsexe() {
        return isexe;
    }

    public void setIsexe(Integer isexe) {
        this.isexe = isexe;
    }

    public String getRecv_time() {
        return recv_time;
    }

    public void setRecv_time(String recv_time) {
        this.recv_time = recv_time;
    }
}
