package com.welltech.water.equipmentreceiver.common.util;

import java.util.Date;
import java.util.Map;

public class UniverseVariable {

   private static Map<String,Date> qtMap = null;//当前接收到的数据的时间

    public static Map<String, Date> getQtMap() {
        return qtMap;
    }

    public static void setQtMap(Map<String, Date> qtMap) {
        UniverseVariable.qtMap = qtMap;
    }
}
