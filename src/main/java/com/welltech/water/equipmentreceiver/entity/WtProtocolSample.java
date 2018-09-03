package com.welltech.water.equipmentreceiver.entity;

import javax.persistence.*;

@Entity
@Table(name = "wt_protocol_sample")
public class WtProtocolSample {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Integer i42001;

    @Column
    private Integer i43006;

    @Column
    private Integer i43007;

    @Column
    private Double i43008;

    @Column
    private Integer i43901;

    @Column
    private Integer i43911;

    @Column
    private Integer i43921;

    @Column
    private Integer i43931;

    @Column
    private Integer i43941;

    @Column
    private Integer i43951;

    @Column
    private Integer i43961;

    @Column
    private Integer i32001;

    @Column
    private Integer i33001;

    @Column
    private String i33002;

    @Column(name = "RawId")
    private Integer wtDataRawId;





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getI42001() {
        return i42001;
    }

    public void setI42001(Integer i42001) {
        this.i42001 = i42001;
    }

    public Integer getI43006() {
        return i43006;
    }

    public void setI43006(Integer i43006) {
        this.i43006 = i43006;
    }

    public Integer getI43007() {
        return i43007;
    }

    public void setI43007(Integer i43007) {
        this.i43007 = i43007;
    }

    public Double getI43008() {
        return i43008;
    }

    public void setI43008(Double i43008) {
        this.i43008 = i43008;
    }

    public Integer getI43901() {
        return i43901;
    }

    public void setI43901(Integer i43901) {
        this.i43901 = i43901;
    }

    public Integer getI43911() {
        return i43911;
    }

    public void setI43911(Integer i43911) {
        this.i43911 = i43911;
    }

    public Integer getI43921() {
        return i43921;
    }

    public void setI43921(Integer i43921) {
        this.i43921 = i43921;
    }

    public Integer getI43931() {
        return i43931;
    }

    public void setI43931(Integer i43931) {
        this.i43931 = i43931;
    }

    public Integer getI43941() {
        return i43941;
    }

    public void setI43941(Integer i43941) {
        this.i43941 = i43941;
    }

    public Integer getI43951() {
        return i43951;
    }

    public void setI43951(Integer i43951) {
        this.i43951 = i43951;
    }

    public Integer getI43961() {
        return i43961;
    }

    public void setI43961(Integer i43961) {
        this.i43961 = i43961;
    }

    public Integer getI32001() {
        return i32001;
    }

    public void setI32001(Integer i32001) {
        this.i32001 = i32001;
    }

    public Integer getI33001() {
        return i33001;
    }

    public void setI33001(Integer i33001) {
        this.i33001 = i33001;
    }

    public String getI33002() {
        return i33002;
    }

    public void setI33002(String i33002) {
        this.i33002 = i33002;
    }

    public Integer getWtDataRawId() {
        return wtDataRawId;
    }

    public void setWtDataRawId(Integer wtDataRawId) {
        this.wtDataRawId = wtDataRawId;
    }

    @Override
    public String toString() {
        return "WtProtocolSample{" +
                "id=" + id +
                ", i42001=" + i42001 +
                ", i43006=" + i43006 +
                ", i43007=" + i43007 +
                ", i43008=" + i43008 +
                ", i43901=" + i43901 +
                ", i43911=" + i43911 +
                ", i43921=" + i43921 +
                ", i43931=" + i43931 +
                ", i43941=" + i43941 +
                ", i43951=" + i43951 +
                ", i43961=" + i43961 +
                ", i32001=" + i32001 +
                ", i33001=" + i33001 +
                ", i33002='" + i33002 + '\'' +
                ", wtDataRawId=" + wtDataRawId +
                '}';
    }
}
