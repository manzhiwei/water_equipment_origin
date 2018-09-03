package com.welltech.water.equipmentreceiver.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class EquipmentReceiverParameter implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(EquipmentReceiverParameter.class);

    private static final Map<String, String> paramMap = new HashMap<>();

    public static Map<String, String> getParamMap() {
        return paramMap;
    }

    public static String getParam(String key) {
        return paramMap.get(key);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {

            Properties properties = new Properties();
            InputStream is = null;
            try {
                is = EquipmentReceiverParameter.class.getClassLoader().getResourceAsStream("parameter.properties");
                properties.load(is);
            } catch (IOException e) {
                logger.error("配置参数加载异常", e);
            } finally {
                if (is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }

            Set<String> set = properties.stringPropertyNames();
            for(String key : set) {
                String value = properties.getProperty(key);
                if(StringUtils.isNotBlank(value)) {
                    paramMap.put(value.trim(), key.trim());
                }
            }

            logger.info("【参数字段映射】加载完成：" + paramMap);

        }
    }
}
