package com.welltech.water.equipmentreceiver;

import com.welltech.water.equipmentreceiver.common.EquipmentReceiverParameter;
import com.welltech.water.equipmentreceiver.common.EquipmentReceiverProperties;
import com.welltech.water.equipmentreceiver.common.exception.BizProtocolException;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

@SpringBootApplication
@EnableAsync
public class EquipmentReceiverApplication {

    private static final Logger logger = Logger.getLogger(EquipmentReceiverApplication.class);
    private static Integer socketLinks = 0;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EquipmentReceiverApplication.class, args);

        EquipmentReceiverProperties properties = context.getBean(EquipmentReceiverProperties.class);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(properties.getPort());
            logger.info("监听端口：" + properties.getPort());

            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new EquipmentReceiverHandler(socket, context)).start();
                socketLinks++;
                logger.info("-----------------------------------------------------socket连接数为："+socketLinks);
            }

        } catch (Exception e) {
            logger.error("监听端口异常", e);
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }

    }
}
