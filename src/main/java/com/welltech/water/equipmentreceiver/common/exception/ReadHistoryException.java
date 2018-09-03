package com.welltech.water.equipmentreceiver.common.exception;

public class ReadHistoryException extends Exception{
    public ReadHistoryException(String msg) {
        super("【读取历史数据异常】" + msg);
    }
}
