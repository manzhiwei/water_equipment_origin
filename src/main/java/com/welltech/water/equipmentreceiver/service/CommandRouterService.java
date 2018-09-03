package com.welltech.water.equipmentreceiver.service;

import com.welltech.water.equipmentreceiver.common.bean.ProtocolDataSegment;
import com.welltech.water.equipmentreceiver.common.exception.BizProtocolException;
import com.welltech.water.equipmentreceiver.common.exception.ReadHistoryException;
import com.welltech.water.equipmentreceiver.common.util.CRC16Utils;
import com.welltech.water.equipmentreceiver.common.util.ProtocolUtils;
import com.welltech.water.equipmentreceiver.common.util.UniverseVariable;
import com.welltech.water.equipmentreceiver.entity.WtProtocolProperty;
import com.welltech.water.equipmentreceiver.repository.WtProtocolPropertyRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author WangXin
 */
@Service
public class CommandRouterService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private RealtimeService realtimeService;

    @Autowired
    private WtProtocolPropertyRepository wtProtocolPropertyRepository;

    private Calendar calendar = Calendar.getInstance();

    /**
     * 处理现场机向上位机请求
     * @param segment
     * @return backment
     * <li>null：表示不需要应答</li>
     */
    public ProtocolDataSegment process(ProtocolDataSegment segment) {

        handleData(segment);

        if(StringUtils.isEmpty(segment.getFlag())) {
            // 不需要应答
            return null;
        }

        String flagStr = "00000000" + Integer.toBinaryString(Integer.valueOf(segment.getFlag()));
        char[] flag = flagStr.substring(flagStr.length() - 8, flagStr.length()).toCharArray();

        if('0' == flag[7]) { //Flag的bit0位
            // 不需要应答
            return null;
        }

        ProtocolDataSegment result = new ProtocolDataSegment();
        // 请求编码
        result.setQn(segment.getQn());
        // 唯一标识
        result.setMn(segment.getMn());
        // 用于现场机和上位机的交互
        result.setSt("91");
        // 数据应答命令
        result.setCn("9014");
        // 访问密码
        result.setPw(segment.getPw());
        // 拆分包以应答标志
        result.setFlag("4");

        return result;
    }

    /**
     * 根据系统编码和命令,调用不同服务
     * @param segment
     */
    private void handleData(ProtocolDataSegment segment) {
        if("21".equals(segment.getSt())
                || "32".equals(segment.getSt())
                || "42".equals(segment.getSt())) {
            // 地表水质量检测 || 地表水体环境污染源
            if("2011".equals(segment.getCn()))  //实时数据
            {
                realtimeService.Resp2011(segment);
            }
            else if("2051".equals(segment.getCn())) //分钟数据
            {
//                realtimeService.Resp2011(segment);
                  realtimeService.Resp2051(segment);

            }
            else if("2061".equals(segment.getCn()))//小时数据
            {
                realtimeService.Resp2061(segment);
            }
            else if("2081".equals(segment.getCn())) //开机时间
            {
                realtimeService.Resp2081(segment);

            }
            else if("9021".equals(segment.getCn()))
            {
                realtimeService.Resp9021(segment);

            }
        }
    }

    public void readHistory(Socket socket, ProtocolDataSegment segment, BufferedWriter writer, BufferedReader reader, ApplicationContext context)
    {
        Map<String,Date> qtMap = UniverseVariable.getQtMap();
        if (qtMap==null)
        {
            logger.info("本次开机还未收到数据");
            return;
        }
        Date qtMapone = qtMap.get(segment.getMn());//当前数据的时间标签
        WtProtocolProperty wtProtocolProperty = wtProtocolPropertyRepository.findFirstByMn(segment.getMn());
        if(wtProtocolProperty==null)
        {
            logger.info("property没有相应数采仪记录");
            return;

        }
        Date restarttime = wtProtocolProperty.getRestarttime();
        Date qt = wtProtocolProperty.getQt();
        Integer rtdinterval = wtProtocolProperty.getRtdinterval();
        if(rtdinterval==null) {//权宜之策
            rtdinterval = 60;
        }
        try {
            if (qtMapone.after(restarttime) == true)//当前收到的数据在开始之后，否则抛出异常
            {
               if(qt == null)
               {
                   wtProtocolProperty.setQt(qtMapone);//将当前数据的时间标签保存到qt中
                   wtProtocolPropertyRepository.save(wtProtocolProperty);//更新数据库
                   return;
               }
               else
               {
                   if(qtMapone.after(qt)==true)
                   {
                        long diffTime = (qtMapone.getTime()-qt.getTime())/1000;
                        if(diffTime==rtdinterval){
                            logger.info("不缺数据,不发读取历史数据命令");
                            wtProtocolProperty.setQt(qtMapone);//将当前数据的时间标签保存到qt中
                            wtProtocolPropertyRepository.save(wtProtocolProperty);//更新数据库
                            return;
                        }
                        else
                        {
                            ProtocolDataSegment command = new ProtocolDataSegment();
                            Date QN = new Date();
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                            command.setQn(df.format(QN));
                            command.setSt(segment.getSt());
                            command.setCn("2051");
                            command.setPw(segment.getPw());
                            command.setMn(segment.getMn());
                            command.setFlag("5");

                            Map<String,String> map1 = new HashMap<String, String>();
                            Map<String,String> map2 = new HashMap<String, String>();
                            SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");

                            calendar.setTime(qt);
                            calendar.add(Calendar.MINUTE,1);
                            map1.put("BeginTime",df1.format(calendar.getTime()));

                            calendar.setTime(qtMapone);
                            calendar.add(Calendar.MINUTE,-1);
                            map2.put("EndTime",df1.format(calendar.getTime()));

                            List<Map<String,String>> cp = new ArrayList<Map<String,String>>();
                            cp.add(map1);
                            cp.add(map2);
                            command.setCp(cp);
                            String commandString = ProtocolUtils.getData(command);
                            logger.info("发送读取分钟历史数据的命令");
                            Integer result = sendReadHistoryCommand(command,commandString,socket,writer,reader,context);
                            if(result==null)
                            {
                                logger.info("命令未能成功发送或执行");
                            }
                            else if(result == 1)
                            {
                                wtProtocolProperty.setQt(qtMapone);//将当前数据的时间标签保存到qt中
                                wtProtocolPropertyRepository.save(wtProtocolProperty);//更新数据库
                            }
                            else
                            {
                                logger.info("命令未能发送或执行成功，执行结果为："+result);
                            }


                        }

                   }
                   else if(qtMapone.equals(qt)==true)
                   {
                       throw new ReadHistoryException("当前数据的时间标签等于qt");

                   }
                   else if(qtMapone.before(qt)==true)
                   {
                       throw new ReadHistoryException("当前数据的时间标签小于qt");

                   }

               }

            }
            else
            {
                throw new ReadHistoryException("当数据的时间标签小于相应数采仪的开机时间");
            }
        }
        catch (Exception e)
        {
            if( e instanceof ReadHistoryException) {
                logger.error(e);
            } else {
                logger.error("处理请求发生异常", e);
            }

        }
        finally {

        }

    }

    public Integer sendReadHistoryCommand(ProtocolDataSegment command,String commandString,Socket socket, BufferedWriter writer,BufferedReader reader,ApplicationContext context)
    {
        try {
            WtProtocolProperty wtProtocolProperty = wtProtocolPropertyRepository.findFirstByMn(command.getMn());
            Integer recount = wtProtocolProperty.getRecount();
            if(recount==null) {//权宜之策
                recount = 3;
            }
            Integer recountCount = 0;
            Integer overtime = wtProtocolProperty.getOvertime();
            if(overtime==null) {//权宜之策
                overtime = 10;
            }
            Integer overtimeCount =0;

            while((recountCount<=recount)||(recountCount==10000)) {
                logger.info("\t\t发送\t\t"+commandString);
                writer.write(commandString);
                writer.write("\r\n");
                writer.flush();
                try {
                    socket.setSoTimeout(overtime * 1000);
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
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                        writer.write(backData);
                        writer.write( "\r\n");
                        writer.flush();
                    }

                    if("9011".equals(segment.getCn()))
                    {
                        recountCount = 10000;//代表收到应答
                        List<Map<String, String>> cp = command.getCp();

                        for(Map<String, String> map : cp)
                        {
                            String temp = map.get("QnRtn");
                            if(temp!=null)
                            {
                                logger.info("收到请求应答："+temp);
                                if("1".equals(temp))
                                {
                                    logger.info("准备执行请求");
                                }
                                else
                                {
                                    socket.setSoTimeout(0);
                                    if("2".equals(temp))
                                    {
                                        logger.info("请求被拒绝");
                                    }
                                    else if("3".equals(temp))
                                    {
                                        logger.info("PW错误");
                                    }
                                    else if("4".equals(temp))
                                    {
                                        logger.info("MN错误");
                                    }
                                    else if("5".equals(temp))
                                    {
                                        logger.info("ST错误");
                                    }
                                    else if("6".equals(temp))
                                    {
                                        logger.info("Flag错误");
                                    }
                                    else if("7".equals(temp))
                                    {
                                        logger.info("QN错误");
                                    }
                                    else if("8".equals(temp))
                                    {
                                        logger.info("CN错误");
                                    }
                                    else if("9".equals(temp))
                                    {
                                        logger.info("CRC错误");
                                    }
                                    else if("6".equals(temp))
                                    {
                                        logger.info("Flag错误");
                                    }
                                    else
                                    {
                                        logger.info("未知错误");
                                    }

                                    return null;//返回不再执行代码
                                }

                            }
                        }

                    }
                    else if("9012".equals(segment.getCn()))
                    {
                        socket.setSoTimeout(0);
                        List<Map<String, String>> cp = command.getCp();
                        for(Map<String,String> map:cp)
                        {
                            String temp = map.get("ExeRtn");
                            if(temp!=null)
                            {
                                logger.info("收到命令执行结果："+temp);

                                if("1".equals(temp))
                                {
                                    logger.info("执行成功");
                                }
                                else if("2".equals(temp))
                                {
                                    logger.info("执行失败但不知原因");
                                }
                                else if("3".equals(temp))
                                {
                                    logger.info("命令请求条件错误");
                                }
                                else if("4".equals(temp))
                                {
                                    logger.info("通讯超时");
                                }
                                else if("5".equals(temp))
                                {
                                    logger.info("系统繁忙不能执行");
                                }
                                else if("6".equals(temp))
                                {
                                    logger.info("系统故障");
                                }
                                else if("100".equals(temp))
                                {
                                    logger.info("没有数据");
                                }

                                return Integer.valueOf(temp);

                            }

                        }


                    }


                }
                catch (Exception e)
                {
                    if(e instanceof SocketTimeoutException) {  //
                        if(recountCount==10000)
                        {
                            logger.error("执行超时,命令执行失败");
                            return null;
                        }

                        logger.error("请求回应超时");
                        recountCount++;
                        if (recountCount <= recount) {
                            logger.info("进行第" + recountCount + "次重发");
                        }
                        else {
                            logger.info("命令发送失败;通讯不可用,通讯结束，");
                            socket.setSoTimeout(0);
                            return null;

                        }
                    }
                    else if(e instanceof BizProtocolException)
                    {
                        logger.error(e);

                    }
                    else
                    {
                        logger.error("处理请求发生异常", e);

                    }
                }
            }

        }
        catch(Exception e)
        {
            logger.error("发送命令发生异常", e);

        }

        return null;

    }


}
