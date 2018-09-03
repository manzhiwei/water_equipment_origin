package com.welltech.water.equipmentreceiver.service;

import com.welltech.water.equipmentreceiver.common.EquipmentReceiverParameter;
import com.welltech.water.equipmentreceiver.common.bean.ProtocolDataSegment;
import com.welltech.water.equipmentreceiver.common.util.DateUtils;
import com.welltech.water.equipmentreceiver.common.util.UniverseVariable;
import com.welltech.water.equipmentreceiver.entity.*;
import com.welltech.water.equipmentreceiver.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据命令:实时数据
 */
@Service
public class RealtimeService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private WtDataRawRepository wtDataRawRepository;

    @Autowired
    private WtDataRepository wtDataRepository;

    @Autowired
    private WtProtocolSampleRepository wtProtocolSampleRepository;

    @Autowired
    private WtProtocolBottleReposity wtProtocolBottleReposity;

    @Autowired
    private WtProtocolPropertyRepository wtProtocolPropertyRepository;

    /**
     * 保存上传数据
     * @param segment
     */
    //@Async
    public void Resp2011(ProtocolDataSegment segment) {
        List<Map<String, String>> cp = segment.getCp();

        WtDataRaw dataRaw = new WtDataRaw();
        WtProtocolSample wtProtocolSample = new WtProtocolSample();
        WtProtocolBottle wtProtocolBottle = new WtProtocolBottle();
        // 设备唯一标识
        dataRaw.setMcu(segment.getMn());
        dataRaw.setType("数采仪");
        dataRaw.setSn(segment.getSt());
        dataRaw.setReceiveTime(new Date());
        if(cp != null) {
            for(Map<String, String> map : cp){ // 解析cp字段信息
                for(Map.Entry<String, String> entry : map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if(key.equals("DataTime")) {
                        // 数据时间信息
                        Date time = DateUtils.parseFullTime(value);
                        dataRaw.setTime(time);
                       // wtProtocolSample.setTime(time);
                        Map<String,Date> qtMap = new HashMap<String,Date>();
                        qtMap.put(segment.getMn(),time);
                        UniverseVariable.setQtMap(qtMap);
                    }

                    if(key.endsWith("-Rtd")
                            && StringUtils.isNotBlank(value)) {
                        String paramKey = key.replace("-Rtd", "").trim();
                        String param = EquipmentReceiverParameter.getParam(paramKey);
                        if(StringUtils.isNotBlank(param)) {
                            Field field = ReflectionUtils.findField(WtDataRaw.class, param);
                            ReflectionUtils.makeAccessible(field);
                            ReflectionUtils.setField(field, dataRaw, new BigDecimal(value));
                        }

                    }

                    if(key.endsWith("-Avg")
                            && StringUtils.isNotBlank(value)){
                        String paramKey = key.replace("-Avg", "").trim();
                        String param = EquipmentReceiverParameter.getParam(paramKey);
                        if(StringUtils.isNotBlank(param)) {
                            Field field = ReflectionUtils.findField(WtDataRaw.class, param);
                            ReflectionUtils.makeAccessible(field);
                            ReflectionUtils.setField(field, dataRaw, new BigDecimal(value));
                        }

                    }

                    if(key.endsWith("-Info")
                            && StringUtils.isNotBlank(value)) {
                        String paramKey = key.replace("-Info", "").trim();
                        if(StringUtils.isNotBlank(paramKey)) {
                            Field field = ReflectionUtils.findField(WtProtocolSample.class, paramKey);
                            if(field!=null){
                              ReflectionUtils.makeAccessible(field);
                              if(paramKey.equals("i43008"))
                              {
                                  ReflectionUtils.setField(field, wtProtocolSample, Double.parseDouble(value));
                              }
                              else if(paramKey.equals("i33002"))
                              {
                                  ReflectionUtils.setField(field, wtProtocolSample, value);
                              }
                              else {
                                  ReflectionUtils.setField(field, wtProtocolSample, Integer.parseInt(value));
                              }
                            }
                            field = ReflectionUtils.findField(WtProtocolBottle.class, paramKey);
                            if(field!=null){
                                ReflectionUtils.makeAccessible(field);
                                ReflectionUtils.setField(field, wtProtocolBottle, Integer.parseInt(value));
                            }

                        }

                    }

                }
            }
        }

        logger.info("处理结果：" + dataRaw+wtProtocolSample+wtProtocolBottle);

        try {
            WtDataRaw wtDataRaw =  wtDataRawRepository.save(dataRaw);
            wtProtocolSample.setWtDataRawId(wtDataRaw.getId());
            WtProtocolSample ProtocolSample = wtProtocolSampleRepository.save(wtProtocolSample);
            wtProtocolBottle.setWtProtocolSampleID(ProtocolSample.getId());
            wtProtocolBottleReposity.save(wtProtocolBottle);

        } catch (DataIntegrityViolationException e){
            logger.error(e);
            return;
        }

        List<WtData> wtDatas = wtDataRepository.findAllByMcu(dataRaw.getMcu());
        if(wtDatas == null || wtDatas.size() == 0){ //更新实时数据
            // 插入一条
            WtData wtData = new WtData();
            BeanUtils.copyProperties(dataRaw, wtData);
            wtDataRepository.save(wtData);
        } else{
            // 更新所有
            for(WtData wtData: wtDatas){
                int id = wtData.getId();
                BeanUtils.copyProperties(dataRaw, wtData);
                wtData.setId(id);
                wtDataRepository.save(wtData);
            }
        }

        List<WtProtocolProperty>  wtProtocolPropertys = wtProtocolPropertyRepository.findAllByMn(dataRaw.getMcu());
        if(wtProtocolPropertys == null||wtProtocolPropertys.size()==0)//更新用户列表
        {   //插入用户记录
            WtProtocolProperty wtProtocolProperty = new WtProtocolProperty();
            wtProtocolProperty.setMn(dataRaw.getMcu());
            wtProtocolProperty.setSt(dataRaw.getSn());
            wtProtocolProperty.setPw(segment.getPw());
            //wtProtocolProperty.setQt(dataRaw.getTime());
            wtProtocolPropertyRepository.save(wtProtocolProperty);

        }
        else
        {  //修改原有记录
            for(WtProtocolProperty wtProtocolProperty:wtProtocolPropertys)
            {
                Integer id = wtProtocolProperty.getId();
                wtProtocolProperty.setMn(dataRaw.getMcu());
                wtProtocolProperty.setSt(dataRaw.getSn());
                wtProtocolProperty.setPw(segment.getPw());
                //wtProtocolProperty.setQt(dataRaw.getTime());
                wtProtocolProperty.setId(id);
                wtProtocolPropertyRepository.save(wtProtocolProperty);


            }


        }



    }

    public void Resp2081(ProtocolDataSegment segment) {

        List<Map<String, String>> cp = segment.getCp();
        WtProtocolProperty wtProtocolProperty = new WtProtocolProperty();

        wtProtocolProperty.setMn(segment.getMn());
        wtProtocolProperty.setSt(segment.getSt());
        wtProtocolProperty.setPw(segment.getPw());

        if(cp != null) {
            for(Map<String, String> map : cp){ // 解析cp字段信息
                for(Map.Entry<String, String> entry : map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if(key.equals("RestartTime")) {
                        // 数据时间信息
                        wtProtocolProperty.setRestarttime(DateUtils.parseFullTime(value));
                    }



                }
            }
        }


        logger.info("处理结果：" + wtProtocolProperty);

        List<WtProtocolProperty>  wtProtocolPropertys = wtProtocolPropertyRepository.findAllByMn(segment.getMn());
        if(wtProtocolPropertys == null||wtProtocolPropertys.size()==0)//更新用户列表
        {   //插入用户记录
            wtProtocolPropertyRepository.save(wtProtocolProperty);

        }
        else
        {  //修改原有记录
            for(WtProtocolProperty each:wtProtocolPropertys)
            {
                Integer id = each.getId();
                BeanUtils.copyProperties(wtProtocolProperty, each);
                each.setId(id);
                wtProtocolPropertyRepository.save(each);


            }


        }



    }

    public void Resp9021(ProtocolDataSegment segment)
    {


    }

    public void Resp2061(ProtocolDataSegment segment) {

        List<Map<String, String>> cp = segment.getCp();

        WtDataRaw dataRaw = new WtDataRaw();

        // 设备唯一标识
        dataRaw.setMcu(segment.getMn());
        dataRaw.setType("数采仪");
        dataRaw.setSn(segment.getSt());
        dataRaw.setReceiveTime(new Date());

    }

    public void Resp2051(ProtocolDataSegment segment) {

    }
}
