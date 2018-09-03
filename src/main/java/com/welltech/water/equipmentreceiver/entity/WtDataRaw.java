package com.welltech.water.equipmentreceiver.entity;

import com.welltech.water.equipmentreceiver.common.util.DateUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by WangXin on 2017/7/21.
 */
@Entity
@Table(name = "wt_data_raw")
public class WtDataRaw {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Date time;

    @Column
    private String type;

    @Column
    private String mcu;

    @Column
    private String sn;

    @Column
    private Date receiveTime;

    @Column
    private BigDecimal p1;

    @Column
    private BigDecimal p2;

    @Column
    private BigDecimal p3;

    @Column
    private BigDecimal p4;

    @Column
    private BigDecimal p5;

    @Column
    private BigDecimal p6;

    @Column
    private BigDecimal p7;

    @Column
    private BigDecimal p8;

    @Column
    private BigDecimal p9;

    @Column
    private BigDecimal p10;

    @Column
    private BigDecimal p11;

    @Column
    private BigDecimal p12;

    @Column
    private BigDecimal p13;

    @Column
    private BigDecimal p14;

    @Column
    private BigDecimal p15;

    @Column
    private BigDecimal p16;

    @Column
    private BigDecimal p17;

    @Column
    private BigDecimal p18;

    @Column
    private BigDecimal p19;

    @Column
    private BigDecimal p20;

    @Column
    private BigDecimal p21;

    @Column
    private BigDecimal p22;

    @Column
    private BigDecimal p23;

    @Column
    private BigDecimal p24;

    @Column
    private BigDecimal p25;

    @Column
    private BigDecimal p26;

    @Column
    private BigDecimal p27;

    @Column
    private BigDecimal p28;

    @Column
    private BigDecimal p29;

    @Column
    private BigDecimal p30;

    @Column
    private BigDecimal p31;

    @Column
    private BigDecimal p32;


    @Override
    public String toString() {
        return "WtDataRaw{" +
                "id=" + id +
                ", time=" + DateUtils.printDate(time) +
                ", type='" + type + '\'' +
                ", mcu='" + mcu + '\'' +
                ", sn='" + sn + '\'' +
                ", receiveTime=" + DateUtils.printDate(receiveTime) +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                ", p6=" + p6 +
                ", p7=" + p7 +
                ", p8=" + p8 +
                ", p9=" + p9 +
                ", p10=" + p10 +
                ", p11=" + p11 +
                ", p12=" + p12 +
                ", p13=" + p13 +
                ", p14=" + p14 +
                ", p15=" + p15 +
                ", p16=" + p16 +
                ", p17=" + p17 +
                ", p18=" + p18 +
                ", p19=" + p19 +
                ", p20=" + p20 +
                ", p21=" + p21 +
                ", p22=" + p22 +
                ", p23=" + p23 +
                ", p24=" + p24 +
                ", p25=" + p25 +
                ", p26=" + p26 +
                ", p27=" + p27 +
                ", p28=" + p28 +
                ", p29=" + p29 +
                ", p30=" + p30 +
                ", p31=" + p31 +
                ", p32=" + p32 +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMcu() {
        return mcu;
    }

    public void setMcu(String mcu) {
        this.mcu = mcu;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public BigDecimal getP1() {
        return p1;
    }

    public void setP1(BigDecimal p1) {
        this.p1 = p1;
    }

    public BigDecimal getP2() {
        return p2;
    }

    public void setP2(BigDecimal p2) {
        this.p2 = p2;
    }

    public BigDecimal getP3() {
        return p3;
    }

    public void setP3(BigDecimal p3) {
        this.p3 = p3;
    }

    public BigDecimal getP4() {
        return p4;
    }

    public void setP4(BigDecimal p4) {
        this.p4 = p4;
    }

    public BigDecimal getP5() {
        return p5;
    }

    public void setP5(BigDecimal p5) {
        this.p5 = p5;
    }

    public BigDecimal getP6() {
        return p6;
    }

    public void setP6(BigDecimal p6) {
        this.p6 = p6;
    }

    public BigDecimal getP7() {
        return p7;
    }

    public void setP7(BigDecimal p7) {
        this.p7 = p7;
    }

    public BigDecimal getP8() {
        return p8;
    }

    public void setP8(BigDecimal p8) {
        this.p8 = p8;
    }

    public BigDecimal getP9() {
        return p9;
    }

    public void setP9(BigDecimal p9) {
        this.p9 = p9;
    }

    public BigDecimal getP10() {
        return p10;
    }

    public void setP10(BigDecimal p10) {
        this.p10 = p10;
    }

    public BigDecimal getP11() {
        return p11;
    }

    public void setP11(BigDecimal p11) {
        this.p11 = p11;
    }

    public BigDecimal getP12() {
        return p12;
    }

    public void setP12(BigDecimal p12) {
        this.p12 = p12;
    }

    public BigDecimal getP13() {
        return p13;
    }

    public void setP13(BigDecimal p13) {
        this.p13 = p13;
    }

    public BigDecimal getP14() {
        return p14;
    }

    public void setP14(BigDecimal p14) {
        this.p14 = p14;
    }

    public BigDecimal getP15() {
        return p15;
    }

    public void setP15(BigDecimal p15) {
        this.p15 = p15;
    }

    public BigDecimal getP16() {
        return p16;
    }

    public void setP16(BigDecimal p16) {
        this.p16 = p16;
    }

    public BigDecimal getP17() {
        return p17;
    }

    public void setP17(BigDecimal p17) {
        this.p17 = p17;
    }

    public BigDecimal getP18() {
        return p18;
    }

    public void setP18(BigDecimal p18) {
        this.p18 = p18;
    }

    public BigDecimal getP19() {
        return p19;
    }

    public void setP19(BigDecimal p19) {
        this.p19 = p19;
    }

    public BigDecimal getP20() {
        return p20;
    }

    public void setP20(BigDecimal p20) {
        this.p20 = p20;
    }

    public BigDecimal getP21() {
        return p21;
    }

    public void setP21(BigDecimal p21) {
        this.p21 = p21;
    }

    public BigDecimal getP22() {
        return p22;
    }

    public void setP22(BigDecimal p22) {
        this.p22 = p22;
    }

    public BigDecimal getP23() {
        return p23;
    }

    public void setP23(BigDecimal p23) {
        this.p23 = p23;
    }

    public BigDecimal getP24() {
        return p24;
    }

    public void setP24(BigDecimal p24) {
        this.p24 = p24;
    }

    public BigDecimal getP25() {
        return p25;
    }

    public void setP25(BigDecimal p25) {
        this.p25 = p25;
    }

    public BigDecimal getP26() {
        return p26;
    }

    public void setP26(BigDecimal p26) {
        this.p26 = p26;
    }

    public BigDecimal getP27() {
        return p27;
    }

    public void setP27(BigDecimal p27) {
        this.p27 = p27;
    }

    public BigDecimal getP28() {
        return p28;
    }

    public void setP28(BigDecimal p28) {
        this.p28 = p28;
    }

    public BigDecimal getP29() {
        return p29;
    }

    public void setP29(BigDecimal p29) {
        this.p29 = p29;
    }

    public BigDecimal getP30() {
        return p30;
    }

    public void setP30(BigDecimal p30) {
        this.p30 = p30;
    }

    public BigDecimal getP31() {
        return p31;
    }

    public void setP31(BigDecimal p31) {
        this.p31 = p31;
    }

    public BigDecimal getP32() {
        return p32;
    }

    public void setP32(BigDecimal p32) {
        this.p32 = p32;
    }

}
