package com.welltech.water.equipmentreceiver.repository;

import com.welltech.water.equipmentreceiver.entity.WtDataRaw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by WangXin on 2017/7/21.
 */
public interface WtDataRawRepository extends JpaRepository<WtDataRaw, Integer>{

    /**
     * 根据时间和mcu查询一条数据
     * @param mcu
     * @param time
     * @return
     */
    WtDataRaw findFirstByMcuAndTime(String mcu, Date time);
}
