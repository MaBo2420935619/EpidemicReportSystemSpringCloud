<template>
  <el-container style="height: 100%; border: 1px solid #eee">

    <el-container>

      <el-header style="text-align: right; font-size: 25px; height: 60px;">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><el-button type="text" @click="toLogin">注销</el-button></el-dropdown-item>
            <el-dropdown-item><el-button type="text" @click="showDialog('本系统由 mabo 独家创作，翻版必究！！')">关于</el-button></el-dropdown-item>
            <el-dropdown-item><el-button type="text" @click="showDialog('客服邮箱:2420935619@qq.com')">客服</el-button></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span style="margin-right: 30px"> 账号:{{this.name}}</span>
      </el-header>

      <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="90%"
          :before-close="handleClose">
        <span>{{this.msg}}</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>

      <div>
        <el-button type="primary" style="width: 40%; height: 40px ;margin: 5px" plain
                   @click="showWitch(1)">上报统计</el-button>
        <el-button type="primary" style="width: 40%; height: 40px ;margin: 5px" plain
                   @click="showWitch(2)">学生上报详情</el-button>
        <el-button type="primary" style="width: 40%; height: 40px ;margin: 5px" plain
                   @click="showWitch(3)">教师上报详情</el-button>
        <el-button type="primary" style="width: 40%; height: 40px ;margin: 5px" plain
                   @click="showWitch(4)">教师班级分配</el-button>
<!--        <el-button type="primary" style="width: 30%; height: 40px ;padding: 5px" plain-->
<!--                   @click="showWitch(6)">系统性能日志</el-button>-->
      </div>
      <el-main >
        <div  v-show="this.showTableName===1||this.showTableName===2||this.showTableName===3">
          <div style="margin-bottom: 20px">选择日期
            <el-date-picker
                @change="datePick()"
                v-model="reportDate"
                type="date"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </div>
        </div>
<!--        上报统计-->
        <div v-show="this.showTableName===1" style="font-size: 15px">
          <span style="display: flex;margin-bottom: 10%">
            全校师生已上报人数：{{form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length}}人
            <el-progress :stroke-width="7"
                         type="circle"
                         :width="70"
                         :height="70"
                         style="margin-right: 10%"
                         v-if="parseInt(100*(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         )/(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         +form3.todayStudentReportInfoNo.length+form2.todayStudentReportInfoNo.length))"
                         :percentage="parseInt(100*(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         )/(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         +form3.todayStudentReportInfoNo.length+form2.todayStudentReportInfoNo.length))">
            </el-progress>
            全校师生未上报人数：{{form3.todayStudentReportInfoNo.length+form2.todayStudentReportInfoNo.length}}人
            <el-progress :stroke-width="7"
                         type="circle"
                         :width="70"
                         :height="70"
                         style="margin-right: 10%"
                         v-if="parseInt(100-100*(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         )/(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         +form3.todayStudentReportInfoNo.length+form2.todayStudentReportInfoNo.length))"
                         :percentage="parseInt(100-100*(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         )/(form3.todayStudentReportInfoYes.length+form2.todayStudentReportInfoYes.length
                         +form3.todayStudentReportInfoNo.length+form2.todayStudentReportInfoNo.length))"
                         color="red">
            </el-progress>
          </span>
          <span style="display: flex;margin-bottom: 10%">
             <el-button style="height: 40px;;margin-right: 10%"
                        type="primary" @click="directToTeacher('已上报')">教师已上报{{form3.todayStudentReportInfoYes.length}}人
            </el-button>
            <el-progress :stroke-width="7"
                         type="circle"
                         :width="70"
                         :height="70"
                         style="margin-right: 10%"
                         v-if="parseInt(100*form3.todayStudentReportInfoYes.length/
                         (form3.todayStudentReportInfoYes.length+
                         form3.todayStudentReportInfoNo.length))"
                         :percentage="parseInt(100*form3.todayStudentReportInfoYes.length/
                         (form3.todayStudentReportInfoYes.length+
                         form3.todayStudentReportInfoNo.length))">
            </el-progress>

          </span>
          <span  style="display: flex;margin-bottom: 10%">
            <el-button style="height: 40px;background-color: red;margin-right: 10%"
                       type="primary" @click="directToTeacher('未上报')">教师未上报{{form3.todayStudentReportInfoNo.length}}人
            </el-button>
            <el-progress :stroke-width="7"
                         type="circle"
                         :width="70"
                         :height="70"
                         style="margin-right: 10%"
                         :percentage="parseInt(100*form3.todayStudentReportInfoNo.length/
                         (form3.todayStudentReportInfoYes.length+
                         form3.todayStudentReportInfoNo.length))"
                         v-if="parseInt(100*form3.todayStudentReportInfoNo.length/
                         (form3.todayStudentReportInfoYes.length+
                         form3.todayStudentReportInfoNo.length))"
                         color="red">
            </el-progress>
          </span>
          <div v-for="item in form2.selectClasses" v-show="item.classId!='全部'" >
             <span style="display: flex;margin-bottom: 10%" >
                 <el-button style="height: 40px ;margin-right: 10%"
                            type="primary" @click="directToStudent('已上报',item.classId)">{{item.classId}}已上报{{item.yesNumber}}人
                  </el-button>
                <el-progress :stroke-width="7"
                           type="circle"
                           :width="70"
                           :height="70"
                           style="margin-right: 10%"
                           v-if="parseInt(100*item.yesNumber/(item.yesNumber+item.noNumber))"
                           :percentage="parseInt(100*item.yesNumber/(item.yesNumber+item.noNumber))"
                  >
              </el-progress>
             </span>
               <span style="display: flex;margin-bottom: 50px" >
                   <el-button style="height: 40px;background-color: red;margin-right: 10%"
                              type="primary" @click="directToStudent('未上报',item.classId)">{{item.classId}}未上报{{item.noNumber}}人
                  </el-button>
                  <el-progress :stroke-width="7"
                           type="circle"
                           :width="70"
                           :height="70"
                           style="margin-right: 10%"
                           v-if="parseInt(100*item.noNumber/(item.yesNumber+item.noNumber))"
                           :percentage="parseInt(100*item.noNumber/(item.yesNumber+item.noNumber))"
                           color="red">
                </el-progress>
              </span>

          </div>
        </div>
        <!--        学生上报详细信息-->
        <div  v-show="this.showTableName===2">
          <el-form >
            <el-form-item label="查询:">
              <el-select v-model="form2.todayStudentReportInfoShowType" style="width: 30%; margin-right: 3%">
                <el-option label="未上报" value="未上报"></el-option>
                <el-option label="已上报" value="已上报"></el-option>
              </el-select>
              班级：
              <el-select v-model="form2.selectClass" style="width: 35%">
                <el-option v-for="item in this.form2.selectClasses"
                           :value="item.classId" :key="item.classId">
                  {{item.classId}}
                </el-option>
              </el-select>
              <el-button type="primary" style="width: 80%" @click="searchReportInfo2()">查询</el-button>
            </el-form-item>

          </el-form>
          <div v-show="form2.todayStudentReportInfoShowType==='未上报'">
            <el-table
                :data="form2.todayStudentReportInfo"
                style="width: 100%">
              <div>
              </div>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope">
                  <el-button
                      size="mini"
                      width="120" @click="warn(scope.$index, scope.row)">提醒</el-button>
                </template>
              </el-table-column>
              <el-table-column prop="id" label="学号" width="120"></el-table-column>
              <el-table-column prop="name" label="姓名" width="120"></el-table-column>
              <el-table-column prop="sex" label="性别" width="120"></el-table-column>
              <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            </el-table>
          </div>
          <div v-show="form2.todayStudentReportInfoShowType!='未上报'">
            <el-table
                :data="form2.todayStudentReportInfo"
                style="width: 100%">
              <div>
              </div>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope">
                  <el-button
                      size="mini"
                      width="120" @click="warn(scope.$index, scope.row)">提醒</el-button>
                </template>
              </el-table-column>
              <el-table-column prop="id" label="学号" width="120"></el-table-column>
              <el-table-column prop="bodyTemperature" label="体温" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.bodyTemperature>37.3" style="color: red">{{ scope.row.bodyTemperature }}</span>
                  <span  v-else >{{ scope.row.bodyTemperature }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="isConfirm" label="是否确诊新冠" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.isConfirm==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isConfirm }}</span>
                  <span  v-else >{{ scope.row.isConfirm }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="isArea" label="途经风险地区" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.isArea==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isArea }}</span>
                  <span  v-else >{{ scope.row.isArea }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="120"></el-table-column>
              <el-table-column prop="sex" label="性别" width="120"></el-table-column>
              <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            </el-table>
          </div>
        </div>
<!--        教师上报详细信息-->
        <div  v-show="this.showTableName===3">
          <el-form >
            <el-form-item label="查询条件" >
              <el-select v-model="form3.todayStudentReportInfoShowType" style="width: 30%; padding-right: 5%" >
                <el-option label="未上报" value="未上报"></el-option>
                <el-option label="已上报" value="已上报"></el-option>
              </el-select>
              <el-button type="primary" style="width: 30%" @click="searchReportInfo3()">查询</el-button>
            </el-form-item>
          </el-form>
          <div v-show="form3.todayStudentReportInfoShowType==='未上报'">
            <el-table
                :data="form3.todayStudentReportInfo"
                style="width: 100%">
              <div>
              </div>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope" >
                  <el-button
                      size="mini"
                      width="120" @click="warn(scope.$index, scope.row)">提醒</el-button>

                </template>
              </el-table-column>
              <el-table-column prop="id" label="工号" width="120"></el-table-column>
              <el-table-column prop="name" label="姓名" width="120"></el-table-column>
              <el-table-column prop="sex" label="性别" width="120"></el-table-column>
              <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            </el-table>
          </div>
          <div v-show="form3.todayStudentReportInfoShowType!='未上报'">
            <el-table
                :data="form3.todayStudentReportInfo"
                style="width: 100%">
              <div>
              </div>
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope" >
                  <el-button
                      size="mini"
                      width="120" @click="warn(scope.$index, scope.row)">提醒</el-button>

                </template>
              </el-table-column>

              <el-table-column prop="id" label="工号" width="120"></el-table-column>
              <el-table-column prop="bodyTemperature" label="体温" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.bodyTemperature>37.3" style="color: red">{{ scope.row.bodyTemperature }}</span>
                  <span  v-else >{{ scope.row.bodyTemperature }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="isConfirm" label="是否确诊新冠" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.isConfirm==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isConfirm }}</span>
                  <span  v-else >{{ scope.row.isConfirm }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="isArea" label="途经风险地区" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.isArea==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isArea }}</span>
                  <span  v-else >{{ scope.row.isArea }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="120"></el-table-column>
              <el-table-column prop="sex" label="性别" width="120"></el-table-column>
              <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            </el-table>
          </div>
        </div>
<!--        教师班级管理-->
        <div  v-show="this.showTableName===4">
          <el-table
              :data="form4.teacherClassesInfo"
              style="width: 100%">
            <div>
            </div>
            <el-table-column label="操作"  width="120">
              <template slot-scope="scope">
                <el-button
                    @click="deleteTeacherClass(scope.$index, scope.row)"
                    size="mini"
                    width="120"
                    >
                  删除
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="操作"  width="120">
              <template slot-scope="scope">
                <el-button
                    @click="toSetTeacherClass(scope.$index, scope.row)"
                    size="mini"
                    width="120">新建</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="教师姓名" width="120"></el-table-column>
            <el-table-column prop="userId" label="教师工号" width="120"></el-table-column>
            <el-table-column prop="classId" label="班级ID" width="120"></el-table-column>
            <el-table-column prop="className" label="班级名称" width="120"></el-table-column>
          </el-table>


        </div>
        <div  v-show="this.showTableName===5" >
          <el-form>
            <el-form-item label="教师工号:" >
              <el-input v-model="form4.teacherId"
                        style="width: 30%"
                        :disabled="true">
              </el-input>
            </el-form-item>
            <el-form-item label="班级：" >

              <el-select v-model="form4.setClassId" style="width: 40%;margin-right: 5%" >
                <el-option v-for="item in this.form2.selectClasses"
                           v-show="item.classId!=='全部'&&item.classId!==form4.classId"
                           :value="item.classId" :key="item.classId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item :disabled=true>
              <el-button type="primary"  @click="setTeacherClassesInfo()">提交</el-button>
              <el-button type="primary"  @click="showWich(4)">返回</el-button>
            </el-form-item>
          </el-form>


        </div>
<!--        系统日志-->
        <div  v-show="this.showTableName===6">
          <el-form :model="form6.form">
            <el-form-item label="日期" >
              <el-date-picker
                  v-model="form6.form.date"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  :picker-options="timeChange"
                  style="width: 60%;padding-right: 5%"
                  value-format="yyyy-MM-dd">
              </el-date-picker>
              <el-button type="primary"
                         @click="getSystemLog()">查询</el-button>
            </el-form-item>
          </el-form>

          <el-table
              :data="form6.systemLog"
              border
              show-overflow
              highlight-hover-row
              style="width: 100%">

<!--            <el-table-column prop="ip" label="请求客户Ip地址" width="150"></el-table-column>-->
<!--            <el-table-column prop="port" label="客户端口" width="100"></el-table-column>-->
            <el-table-column prop="requestype" label="请求类型" ></el-table-column>
            <el-table-column prop="issuccess" label="是否请求成功" ></el-table-column>
            <el-table-column prop="time" label="请求响应用时/ms" ></el-table-column>
            <el-table-column prop="date" label="请求时间" ></el-table-column>
          </el-table>




        </div>
      </el-main>

    </el-container>
  </el-container>

</template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "Admin",
  mounted() {
    let that= this;
    this.timer = setInterval(function() {
      that.time = new Date().toLocaleString();
    });
  },
  beforeDestroy: function() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  data() {
    return {
      timeChange: {
        // disabledDate(time){
        //   console.log(time)
        //   this.time
        //   // return time && time >moment().subtract(1, "days");
        // }
      },

      form2:{
        // 显示的上报内容
        todayStudentReportInfo:[],
        //后台请求的上报内容
        todayStudentReportInfoYes:[],
        todayStudentReportInfoNo:[],
        todayStudentReportInfoShowType:"未上报",
        selectClass:"全部",
        selectClasses:[],
      },
      form3:{
        // 显示的上报内容
        todayStudentReportInfo:[],
        //后台请求的上报内容
        todayStudentReportInfoYes:[],
        todayStudentReportInfoNo:[],
        todayStudentReportInfoShowType:"未上报",
      },
      form4:{
        // 显示的上报内容
        teacherClassesInfo:[],
        setClassId:"",
        classId:"",
        teacherId:"",
      },
      form6:{
        // 显示的上报内容
        systemLog:[],
        form:{
          date:null
        }
      },
      reportDate: this.dateFormatter(new Date()),
      time: this.dateFormatter(new Date()),
      name:this.$route.params.uname,
      dialogVisible: false,
      msg:"",
      showTableName:1,

    };

  },
  methods:{
    dateFormatter(str){//默认返回yyyy-MM-dd HH-mm-ss
      // var hasTime = arguments[1] != false ? true : false;//可传第二个参数false，返回yyyy-MM-dd
      var d = new Date(str);
      var year = d.getFullYear();
      var month = (d.getMonth()+1)<10 ? '0'+(d.getMonth()+1) : (d.getMonth()+1);
      var day = d.getDate()<10 ? '0'+d.getDate() : d.getDate();
      var hour = d.getHours()<10 ? '0'+d.getHours() : d.getHours();
      var minute = d.getMinutes()<10 ? '0'+d.getMinutes() : d.getMinutes();
      var second = d.getSeconds()<10 ? '0'+d.getSeconds() : d.getSeconds();
      return [year, month, day].join('-');
    },
    directToStudent(type,clas){
      // console.log(type);
      this.showTableName=2;
      if (type==='未上报'){
        this.form2.todayStudentReportInfoShowType='未上报';
      }
      else {
        this.form2.todayStudentReportInfoShowType='已上报';
      }
      this.form2.selectClass=clas;
      this.searchReportInfo2();
    },
    directToTeacher(type){
      // console.log(type);
      this.showTableName=3;
      if (type==='未上报'){
        this.form3.todayStudentReportInfoShowType='未上报';
      }
      else {
        this.form3.todayStudentReportInfoShowType='已上报';
      }

      this.searchReportInfo3();
    },
    datePick(){
      this.searchReportInfo3();
    },
    warn(a,b){
      var that=this;
      axios.post('/warnReport'
          +"?id="+b.id
      ).then(response=>{
        console.log(a);
        console.log(b.id);
        console.log(response);
        if (response.data.message==='失败'){
          that.showDialog("提醒失败");
        }else{
          that.showDialog("提醒成功");
        }
      })
          .catch(error=> {
            console.log(error);
            that.showDialog("服务器已下线，请求失败");
          });

    },


    getSystemLog(){
      var that=this;
      if(that.form6.form.date!==null){
        axios.post('/getSystemLog'
            +"?date="+that.form6.form.date
        )
            .then(response=>{
              console.log(response);
              if (response.data.message==='false'){
                that.showDialog("查询失败");
              }else{
                if (response.data.getSystemLog.length===0){
                  that.showDialog("历史数据为空");
                  that.form6.systemLog=response.data.getSystemLog;
                }
                else  {
                  that.showDialog("查询成功");
                  that.form6.systemLog=response.data.getSystemLog;
                  console.info(that.form6.systemLog.length)
                }
              }
            })
            .catch(error=> {
              console.log(error);
              that.showDialog("服务器已下线，请求失败");
            });
      }
      else {
        that.showDialog("请选择日期");
      }

    },
    setTeacherClassesInfo(){
      var that=this;
      var flag=true;
      if (this.form4.setClassId===""&&this.form4.setClassId===undefined){
        flag=false;
      }
      else {
        for (var i=that.form4.teacherClassesInfo.length-1;i>=0;i--){
          var a=that.form4.teacherClassesInfo[i];
          if (a.userId===this.form4.teacherId&&a.classId===this.form4.setClassId){
            flag=false;
            break;
          }
        }
      }
      if (flag===true){
        axios.post('/setTeacherClassesInfo'
            +"?id="+that.form4.teacherId
            +"&classId="+that.form4.setClassId
        )
            .then(response=>{
              console.log(response);
              // console.log(response.data.message);
              if(response.data.message==='true'){
                that.showDialog("提交成功");
                that.teacherClassesInfo();
                this.showTableName=4;
              }
              else if (response.data.message==='false'){
                that.showDialog("提交失败");
              }
            })
            .catch(error=> {
              console.log(error);
              that.showDialog("服务器已下线，请求失败")
            });
      }
      else if(flag===false){
        console.log(flag);
        that.showDialog("提交错误,请重新选择班级");
      }
    },
    toSetTeacherClass(index, row){
      this.showTableName=5;
      this.form4.teacherId=row.userId;
      this.form4.setClassId="";
      this.form4.classId=row.classId;
    },
    deleteTeacherClass(index, row){
      // console.log(index, row);
      // console.log(row.userId);
      // console.log(row.classId);
      if (row.classId===undefined){
        this.showDialog("该老师暂未分配班级！！");
      }
      else {
        var that= this;
        axios.post('/deleteTeacherClass'
            +"?id="+row.userId
            +"&classId="+row.classId
        )
            .then(response=>{
              console.log(response);
              // console.log(response.data.message);
              if(response.data.message==='true'){
                that.showDialog("删除成功");
                that.getStudentReportHistory(row.uameId);
                that.teacherClassesInfo();
              }
              else if (response.data.message==='false'){
                that.showDialog("删除失败");
              }
            })
            .catch(error=> {
              console.log(error);
              that.showDialog("服务器已下线，请求失败")
            });
      }

    },
    teacherClassesInfo(){
      axios.post('/getTeacherClassesInfo')
          .then(response=>{
            console.log(response);
            // 获取所有班级
            this.form4.teacherClassesInfo =response.data.getTeacherClassesInfo;
          })
          .catch(error=> {
            console.log(error);
            that.showDialog("服务器已下线，请求失败")
          });
    },
    //判断学生上报的查询
    searchReportInfo2(){
      this.todayReportInfo();
      if (this.form2.selectClass==="全部"){
        if (this.form2.todayStudentReportInfoShowType==="未上报"){
          this.form2.todayStudentReportInfo=this.form2.todayStudentReportInfoNo;
        }
        else  if (this.form2.todayStudentReportInfoShowType==="已上报"){
          this.form2.todayStudentReportInfo=this.form2.todayStudentReportInfoYes;
        }
      }
      else {
        var a=null;
        if (this.form2.todayStudentReportInfoShowType==="未上报"){
          a=this.form2.todayStudentReportInfoNo;
        }
        else  if (this.form2.todayStudentReportInfoShowType==="已上报"){
          a=this.form2.todayStudentReportInfoYes;
        }
        for (var i=a.length-1;i>=0;i--){
          var student=a[i];
          if (student.classId!==this.form2.selectClass)
            a.splice(i,1);
        }
        this.form2.todayStudentReportInfo=a;
      }
    },
    searchReportInfo3(){
      this.todayReportInfo();
      if (this.form3.todayStudentReportInfoShowType==="未上报"){
        this.form3.todayStudentReportInfo=this.form3.todayStudentReportInfoNo;
      }
      else  if (this.form3.todayStudentReportInfoShowType==="已上报"){
        this.form3.todayStudentReportInfo=this.form3.todayStudentReportInfoYes;
      }
    },
    //今日上报情况
    todayReportInfo(){
      var that=this;
      axios.post('/todayReportInfo?date='+this.reportDate)
          .then(response=>{
            console.log(response);
            // 获取所有班级
            this.form2.selectClasses=response.data.student.allClasses;
            //展示上报信息
            // this.form2.todayStudentReportInfo=response.data.student.noReport;
            this.form2.todayStudentReportInfoNo=response.data.student.noReport;
            this.form2.todayStudentReportInfoYes=response.data.student.report;
            this.form3.todayStudentReportInfoNo=response.data.teacher.noReport;
            // this.form3.todayStudentReportInfo=response.data.teacher.noReport;
            this.form3.todayStudentReportInfoYes=response.data.teacher.report;
          })
          .catch(error=> {
            console.log(error);
            this.showDialog("服务器已下线，请求失败")
          });
    },
    showDialog(msg){
      this.dialogVisible=true;
      this.msg=msg;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    toLogin(){
      this.$router.push('/');
    },
    showWitch(e){
      this.showTableName=e;
    },
    showWich(e){
      this.showTableName=e;
    },
    getStudentReportHistory(name){
      var that=this;
      axios.post('/getStudentReportHistory?uname='+name)
          .then(response=>{
            var a=response.data.getStudentReportHistory;
            that.tableData=a;
          })
          .catch(error=> {
            console.log(error);
            that.showDialog("请联系管理员,服务器已下线！！")
          });
    }
  },
  created() {
    if (this.$route.params.uname===undefined){
      this.$router.push('/');
    }
    this.name= this.$route.params.uname;
    this.todayReportInfo();
    this.todayStudentReportInfo=this.todayStudentReportInfoNo;
    this.teacherClassesInfo();
  },
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.timeShow{
  background: #86c7ff;
  width: 100%;
  height: 30px;
  font-size: 18px;
  color: #5f84a8;
  border: 1px #d7edff solid;
  border-radius: 5px;
  margin: 0px 0px 7px 0px;
  margin-bottom: 30px;
  box-shadow:5px 5px 10px gray;
  background: -webkit-linear-gradient(left,#d7edff, #a7d6ff); /* Safari 5.1 - 6.0 */
  background: -o-linear-gradient(right,#d7edff, #a7d6ff); /* Opera 11.1 - 12.0 */
  background: -moz-linear-gradient(right,#d7edff, #a7d6ff); /* Firefox 3.6 - 15 */
  background: linear-gradient(to right,#d7edff, #a7d6ff); /* 标准的语法（必须放在最后） */}

</style>
