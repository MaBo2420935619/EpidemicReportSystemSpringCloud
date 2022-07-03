package com.mabo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.ReportinfoDao;
import com.mabo.dao.UserbaseDao;
import com.mabo.entity.Reportinfo;
import com.mabo.entity.Userbase;
import com.mabo.feign.FeignEmailService;
import com.mabo.rabbitMQ.sender.MQEmailSender;
import com.mabo.utils.StringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Data
@Service
public class StudentService extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    UserbaseDao userbaseDao;

    @Autowired
    ReportinfoDao reportinfoDao;
    @Autowired
    FeignEmailService feignEmailService;

    @Autowired
    MQEmailSender mqEmailSender;
    /**
     * @Author mabo
     * @Description   获取用户基本信息
     */
    public JSONObject getUserBaseInfo(String id){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String history=null;
        if (valueOperations!=null){
            Object o = valueOperations.get("getUserBaseInfo" + id);
            if (o!=null){
                history= o.toString();
                JSONObject jsonObject = JSONObject.parseObject(history);
                return jsonObject;
            }
        }
        Userbase userbase = userbaseDao.queryById(id);
        if (userbase!=null) {
            String s = JSON.toJSONString(userbase);
            JSONObject jsonObject = JSONObject.parseObject(s);
            BoundValueOperations<String, String> nameValueOperations = redisTemplate.boundValueOps("getUserBaseInfo" + id);
            nameValueOperations.set(s);
            return jsonObject;
        }else {
            return null;
        }
    }

    /**
     * @Author mabo
     * @Description   完善学生基本信息
     */
    public int submitUserBaseInfo(Map map){
        String id = StringUtil.getMapValueString(map, "id");
        redisTemplate.delete("getUserBaseInfo"+id);
        redisTemplate.delete("getTeacherClassesInfo");
        String name = StringUtil.getMapValueString(map, "name");
        String birthday = StringUtil.getMapValueString(map, "birthday");
        String email = StringUtil.getMapValueString(map, "email");
        String phone = StringUtil.getMapValueString(map, "phone");
        String sex = StringUtil.getMapValueString(map, "sex");
        String idCardNumber = StringUtil.getMapValueString(map, "idCardNumber");
        String type = StringUtil.getMapValueString(map, "type");
        Userbase userBaseInfo=new Userbase();
        userBaseInfo.setId(id);
        userBaseInfo.setName(name);
        userBaseInfo.setBirthday(birthday);
        userBaseInfo.setEmail(email);
        userBaseInfo.setPhone(phone);
        userBaseInfo.setSex(sex);
        userBaseInfo.setIdcardnumber(idCardNumber);
        userBaseInfo.setType(type);
        Userbase userbase = userbaseDao.queryById(id);
        if (userbase!=null){
            int update = userbaseDao.update(userBaseInfo);
            if (update>0)
                return 1;
            return 0;
        }
        else {
            int insert = userbaseDao.insert(userBaseInfo);
            if (insert>0)
                return 1;
            return 0;
        }
    }

    /**
     * @Author mabo
     * @Description   学生上报内容
     */
    public int setReportInfo(Map map){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String uameId = StringUtil.getMapValueString(map, "uameId");
        String reportDate = StringUtil.getMapValueString(map, "reportDate");
        boolean reportToday = false;
        List<Reportinfo> reportTodayList = reportinfoDao.isReportToday(reportDate, uameId);

        if(reportTodayList.size()>0){
            reportToday=true;
        }
        if (reportToday){
            //已经上报
            try {
                Date date = simpleDateFormat.parse(reportDate);
                if (date.getTime()>new Date().getTime())
                    return 3;
                reportDate=simpleDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String realReportDate = sdf.format(new Date());
            String bodyTemperature = StringUtil.getMapValueString(map, "bodyTemperature");
            String isConfirm = StringUtil.getMapValueString(map, "isConfirm");
            isConfirm= StringUtil.getBoolean(isConfirm);
            String isArea = StringUtil.getMapValueString(map, "isArea");
            isArea= StringUtil.getBoolean(isArea);
            String address = StringUtil.getMapValueString(map, "address");
            Reportinfo entity=new Reportinfo(
                    reportDate,realReportDate,bodyTemperature
                    ,isConfirm,isArea,address,uameId);
            sendMessageToTeacher(entity);
            boolean b = false;
            redisTemplate.delete("getStudentReportHistory" + uameId);
            if(reportinfoDao.update(entity)>0){
                b = true;
            };
            return 2;
        }
        else {
            try {
                Date date = simpleDateFormat.parse(reportDate);
                if (date.getTime()>new Date().getTime())
                    return 3;
                reportDate=simpleDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String realReportDate = sdf.format(new Date());
            String bodyTemperature = StringUtil.getMapValueString(map, "bodyTemperature");
            String isConfirm = StringUtil.getMapValueString(map, "isConfirm");
            isConfirm= StringUtil.getBoolean(isConfirm);
            String isArea = StringUtil.getMapValueString(map, "isArea");
            isArea= StringUtil.getBoolean(isArea);
            String address = StringUtil.getMapValueString(map, "address");
            Reportinfo entity=new Reportinfo(
                    reportDate,realReportDate,bodyTemperature
                    ,isConfirm,isArea,address,uameId);
            sendMessageToTeacher(entity);
            boolean b = false;
            redisTemplate.delete("getStudentReportHistory" + uameId);
            int insert = reportinfoDao.insert(entity);
            if (insert>0){
                return 1;
            }

            //上报失败
            return 0;
        }

    }
    /**
     * @Author mabo
     * @Description   判断当前上报人是否为学生，是否有异常上报信息
     * 如果为学生，将其上报异常信息发送给对应老师和管理员
     * 如果上报人为教师，异常信心推送给管理员
     */
    public void sendMessageToTeacher(Reportinfo reportInfo){
        String isArea = reportInfo.getIsarea();
        String isConfirm = reportInfo.getIsconfirm();
        String bodyTemperature = reportInfo.getBodytemperature();
        double v = Double.parseDouble(bodyTemperature);
        if (v>37.3||isArea.equals("是")||isConfirm.equals("是")){
            String sql="SELECT email,login.type,class_user.classId FROM userbase left JOIN login ON userbase.id=login.uname left join class_user ON userId=userbase.id where userbase.id=?";
            List<Map<String, Object>> m = jdbcTemplate.queryForList(sql, reportInfo.getUameid());
            Map<String, Object> map = m.get(0);
            String type = map.get("type").toString();
            String classId = map.get("classId").toString();
            if (type.equals("student")){
                String s="SELECT userbase.email,userbase.id,class_user.classId FROM userbase left JOIN login ON userbase.id=login.uname left join class_user ON userId=userbase.id where class_user.classId=? AND login.type=?";
                List<Map<String, Object>> mapList = jdbcTemplate.queryForList(s, classId, "teacher");
                for (Map teacherMap: mapList) {
                    String email = teacherMap.get("email").toString();
                    try {
                        //发送邮件
                        mqEmailSender.send(email,"学生/教师上报信息异常推送",reportInfo.toString());
//                        feignEmailService.sendMail(email,"学生/教师上报信息异常推送",reportInfo.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(type.equals("teacher")){
                try {
                    //发送邮件
                    mqEmailSender.send("1536532129@qq.com","学生/教师上报信息异常推送",reportInfo.toString());
//                     feignEmailService.sendMail("1536532129@qq.com","学生/教师上报信息异常推送",reportInfo.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
//
//
/**
 * @Author mabo
 * @Description   获取当前学生上报历史
 */
    public JSONArray getStudentReportHistory(String  uname){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get("getStudentReportHistory" + uname);
        String history =null;
        if (o!=null){
            history=o.toString();
            JSONArray jsonArray = JSONObject.parseArray(history);
            return jsonArray;
        }
        else {
            List<Reportinfo> studentReportHistory =
                    reportinfoDao.getStudentReportHistory(uname);
            String s = JSON.toJSONString(studentReportHistory);
            JSONArray jsonArray = JSONObject.parseArray(s);
            valueOperations.set("getStudentReportHistory"+uname,s);
            return jsonArray;
        }
    }
//
    /**
     * @Author mabo
     * @Description   删除一条学生上报信息
     */
    public JSONObject deleteStudentReport(Map map){
        JSONObject json=new JSONObject();
        String uameId = StringUtil.getMapValueString(map, "uameId");
        String reportDate = StringUtil.getMapValueString(map, "reportDate");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=simpleDateFormat.parse(reportDate);
            reportDate=simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = reportinfoDao.deleteById(uameId, reportDate);

        if (i>0){
            try {
                redisTemplate.delete("getStudentReportHistory"+uameId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        json.put("message",i>0);
        return json;
    }

}
