package com.welltech.water.equipmentreceiver.repository;

import com.welltech.water.equipmentreceiver.entity.WtData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by WangXin on 2017/7/21.
 */
public interface WtDataRepository extends JpaRepository<WtData, Integer>{

    /**
     * 根据mcu编号查询数据列表
     * @param mcu
     * @return
     */
    List<WtData> findAllByMcu(String mcu);
}
