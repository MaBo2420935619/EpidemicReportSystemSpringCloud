package com.mabo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mabo.service.StudentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

/**
 * @Author mabo
 * @Description   学生相关控制器
 */
@Data
@RestController
public class StudentController{
    @Autowired
    private StudentService studentService;

    /**
     * @Author mabo
     * @Description   获取用户基本信息
     */
    @PostMapping("/getUserBaseInfo")
    public Object getUserBaseInfo(@RequestParam("id")String id){
        JSONObject json=new JSONObject();
        JSONObject j = studentService.getUserBaseInfo( id);
        if(j==null){
            json.put("message","false");
            return  JSONObject.toJSON(json);
        }
        return  JSONObject.toJSON(j);

    }
//
    /**
     * @Author mabo
     * @Description   完善学生信息
     */
    @PostMapping("/submitUserBaseInfo")
    public Object submitUserBaseInfo(
            @RequestParam("id")String id,
            @RequestParam("name")String name,
            @RequestParam("birthday")String birthday,
            @RequestParam("email")String email,
            @RequestParam("phone")String phone,
            @RequestParam("sex")String sex,
            @RequestParam("idCardNumber")String idCardNumber,
            @RequestParam("type")String type
    ){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("birthday",birthday);
        map.put("email",email);
        map.put("phone",phone);
        map.put("sex",sex);
        map.put("idCardNumber",idCardNumber);
        map.put("type",type);
        int i = studentService.submitUserBaseInfo(map);
        JSONObject json=new JSONObject();
        if (i==1)
            json.put("message","true");
        else if (i==0)
            json.put("message","false");
        else
            json.put("message","error");
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description   填写学生上报信息
     */
    @PostMapping("/setReportInfo")
    public Object setReportInfo(
            @RequestParam("reportDate")String reportDate,
            @RequestParam("bodyTemperature")String bodyTemperature,
            @RequestParam("isConfirm")String isConfirm,
            @RequestParam("isArea")String isArea,
            @RequestParam("address")String address,
            @RequestParam("uameId")String uameId){
        JSONObject json=new JSONObject();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("reportDate",reportDate);
        map.put("bodyTemperature",bodyTemperature);
        map.put("isConfirm",isConfirm);
        map.put("isArea",isArea);
        map.put("address",address);
        map.put("uameId",uameId);
        int i = studentService.setReportInfo(map);
        if (i==1)
            json.put("message","true");
        else if (i==2)
            json.put("message","已上报无需重复上报");
        else if (i==0)
            json.put("message","false");
        else if (i==3)
            json.put("message","不能超期上报");
        else
            json.put("message","error");
        return  JSONObject.toJSON(json);
    }
//
    /**
     * @Author mabo
     * @Description   获取学生上报历史
     */
    @PostMapping("/getStudentReportHistory")
    public Object getStudentReportHistory(@RequestParam("uname")String uname){
        JSONArray studentReportHistory = studentService.getStudentReportHistory(uname);
        return  JSONObject.toJSON(studentReportHistory);
    }
    /**
     * @Author mabo
     * @Description   删除学生的一条上报记录
     */
    @PostMapping("/deleteStudentReport")
    public Object deleteStudentReport(
            @RequestParam("uameId")String uameId,
            @RequestParam("reportDate")String reportDate
    ){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("uameId",uameId);
        map.put("reportDate",reportDate);
        JSONObject json = studentService.deleteStudentReport(map);
        return  JSONObject.toJSON(json);
    }

}
