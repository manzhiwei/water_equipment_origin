package com.welltech.water.equipmentreceiver.repository;

import com.welltech.water.equipmentreceiver.entity.WtProtocolProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WtProtocolPropertyRepository extends JpaRepository<WtProtocolProperty, Integer> {
    /**
     * 根据mcu编号查询数据列表
     * @param mn
     * @return
     */
    List<WtProtocolProperty> findAllByMn(String mn);
    WtProtocolProperty findFirstByMn(String mn);
}
