package com.welltech.water.equipmentreceiver.common.exception;

/**
 * 协议格式异常类
 */
public class BizProtocolException extends Exception {

    public BizProtocolException(String message) {
        super("【通讯协议格式非法】" + message);
    }
}
