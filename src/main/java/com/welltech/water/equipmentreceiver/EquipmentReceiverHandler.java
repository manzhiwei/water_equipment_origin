package com.welltech.water.equipmentreceiver;

import com.welltech.water.equipmentreceiver.common.bean.ProtocolDataSegment;
import com.welltech.water.equipmentreceiver.common.exception.BizProtocolException;
import com.welltech.water.equipmentreceiver.common.util.CRC16Utils;
import com.welltech.water.equipmentreceiver.common.util.ProtocolUtils;
import com.welltech.water.equipmentreceiver.service.CommandRouterService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class EquipmentReceiverHandler implements Runnable {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     *
     */
    private Socket socket;

    /**
     * Spring上下文
     */
    private ApplicationContext context;

    public EquipmentReceiverHandler(Socket socket, ApplicationContext context) {
        this.socket = socket;
        this.context = context;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            while(socket.isConnected()
                    && !socket.isInputShutdown()
                    && !socket.isOutputShutdown())
            {

                char[] headChars = new char[2];
                if( reader.read(headChars) != 2) {
                    throw new BizProtocolException("链路已经关闭/包头小于2位");
                }
                String headStr = new String(headChars);
                logger.info("\t\t包头\t\t" + headStr);
                if(!"##".equals(headStr)) {
                    throw new BizProtocolException("包头不等于##");
                }
                char[] lengthChars = new char[4];
                if(reader.read(lengthChars) < 4) {
                    throw new BizProtocolException("数据段长度字段小于4位");
                }
                // 取数据段长度
                String lengthStr = new String(lengthChars);
                logger.info("\t\t数据段长度\t\t" + lengthStr);
                int length = Integer.parseInt(lengthStr);
                if(length < 0 || length > 1024) {
                    throw new BizProtocolException("数据段长度不满足0≤n≤1024");
                }
                // 数据段
                char[] dataChars = new char[length];
                if(reader.read(dataChars) < length) {
                    throw new BizProtocolException("取不到规定长度的数据段");
                }
                String data = new String(dataChars);
                logger.info("\t\t数据段\t\t" + data);
                // CRC校验位
                char[] crcChars = new char[4];
                if(reader.read(crcChars) < 4) {
                    throw new BizProtocolException("CRC校验字段小于4位");
                }
                String crcStr = new String(crcChars);
                logger.info("\t\tCRC校验\t\t" + crcStr);
                int crc = Integer.parseInt(crcStr, 16);
                int calcCrc = CRC16Utils.cac16CheckOut(dataChars);
                // 取数据段
                if (crc != calcCrc) {
                    throw new BizProtocolException("CRC校验不正确，包值：" + crc + " ；计算值：" + calcCrc);
                }
                // 结尾字符
                char[] endChars = new char[2];
                reader.read(endChars);

                ProtocolDataSegment segment = ProtocolUtils.parseData(data); //处理数据段，得到各个字段

                CommandRouterService commandRouterService = context.getBean(CommandRouterService.class);
                ProtocolDataSegment backSegment = commandRouterService.process(segment);//存储数据，并得到返回字段

                if(backSegment != null) { //响应命令
                    String backData = ProtocolUtils.getData(backSegment);//得到返回的字符串
                    logger.info("\t\t回复\t\t" + backData);
                    //writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                    writer.write(backData);
                    writer.write( "\r\n");
                    writer.flush();
                }

                //commandRouterService.readHistory(socket,segment,writer,reader,context);//检测数据是否有丢失，如是则读取历史数据

            }

        } catch (Exception e){
            if( e instanceof BizProtocolException) {
                logger.error(e);
            } else {
                logger.error("处理请求发生异常", e);
            }
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

    }
}
