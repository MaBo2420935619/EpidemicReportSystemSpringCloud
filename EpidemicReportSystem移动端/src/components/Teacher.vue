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
        <el-button type="primary" style=" height: 40px ;padding: 5px" plain
                   @click="showWitch(1);">上报历史</el-button>
        <el-button type="primary" style=" height: 40px ;padding: 5px" plain
                   @click="showWitch(2)">疫情上报</el-button>
        <el-button type="primary" style=" height: 40px ;padding: 5px" plain
                   @click="showWitch(3)">个人信息</el-button>
        <el-button type="primary" style="width: 40%; height: 40px ;padding: 5px" plain
                   @click="showWitch(4)">班级上报统计</el-button>
        <el-button type="primary" style="width: 40%; height: 40px ;padding: 5px" plain
                   @click="showWitch(5)">班级上报详情</el-button>
      </div>
      <!--      显示历史信息-->
      <el-main >

        <el-table
            v-show="this.showTableName===1"
            :data="tableData"
            style="width: 100%">
          <el-table-column label="操作"  width="120">
            <template slot-scope="scope" >
              <el-button
                  size="mini"
                  width="120"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="reportDate" label="上报日期" width="120"></el-table-column>
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
          <el-table-column prop="isArea" label="最近14天是否途经中高风险地区" width="120">
            <template scope="scope">
              <span  v-if="scope.row.isArea==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isArea }}</span>
              <span  v-else >{{ scope.row.isArea }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="上报地点"></el-table-column>
        </el-table>


        <!--      显示上报内容-->
        <el-form :model="ruleForm"
                 v-show="this.showTableName===2"
                 :rules="rules" ref="ruleForm" label-width="30%"  class="demo-ruleForm">
          <el-form-item label="是否确诊新冠" >
            否<el-switch v-model="ruleForm.isConfirm2"></el-switch>是
          </el-form-item>
          <el-form-item label="途经风险地区" >
            否<el-switch v-model="ruleForm.isArea2"></el-switch>是
          </el-form-item>

          <el-form-item label="当前体温" prop="bodyTemperature2" >
            <el-input v-model="ruleForm.bodyTemperature2" style="width: 200px"></el-input>
          </el-form-item>

          <el-form-item label="上报地点" prop="address2" >
            <el-select v-model="ruleForm.address2" style="width: 200px">
              <el-option v-for="item in this.addresses"
                         :value="item" :key="item">
                {{item}}
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="上报日期" >
            <el-form-item prop="date2">
              <el-date-picker
                  v-model="ruleForm.date2"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  :disabled="true"
                  value-format="yyyy-MM-dd">
              </el-date-picker>

            </el-form-item>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">立即上报</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>

        <!--        完善个人信息-->
        <el-form :model="ruleForm"
                 style="width: 300px;"
                 v-show="this.showTableName===3">
          <el-form-item label="出生日期" >
            <el-date-picker
                v-model="userBase.birthday"
                :disabled="displayUserBase"
                style="width: 220px; "
                type="date"
                placeholder="选择日期"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input v-model="userBase.name"
                      :disabled="displayUserBase">
            </el-input>
          </el-form-item >

          <el-form-item label="电话">
            <el-input @change="isvalidPhone(userBase.phone)" v-model="userBase.phone"
                      :disabled="displayUserBase">
            </el-input>
          </el-form-item>

          <el-form-item label="性别">
            <el-select v-model="userBase.sex"  :disabled="displayUserBase">
              <el-option v-for="item in this.sexs"
                         :value="item" :key="item">
                {{item}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input @change="isCardID(userBase.idCardNumber)" v-model="userBase.idCardNumber"
                      :disabled="displayUserBase">
            </el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="userBase.email"
                      :disabled="true">
            </el-input>
          </el-form-item>
          <el-form-item :disabled=true>
            <el-button type="primary" v-show="!displayUserBase" @click="submitUserBaseInfo(userBase)">保存</el-button>
            <el-button type="primary" v-show="displayUserBase" @click="displayUserBase=false">编辑</el-button>
          </el-form-item>
        </el-form>
<!--        学生上统计-->
        <div  v-show="this.showTableName===4||this.showTableName===5">
          <div style="margin-bottom: 9%;">选择日期
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

        <div v-show="this.showTableName===4">
          <span style="display: flex;margin-bottom: 10px">
            <el-button style="height: 40px;margin-right : 10%"
                       type="primary" @click="directToClassInfo('全部','已上报')">全部已上报{{todayStudentReportInfoYes.length}}人
            </el-button>

            <el-progress :stroke-width="7"
                         type="circle"
                         :width="70"
                         :height="70"
                         style="margin-right: 10%"
                         v-if="parseInt(100*todayStudentReportInfoYes.length/(todayStudentReportInfoYes.length+todayStudentReportInfoNo.length))"
                         :percentage="parseInt(100*todayStudentReportInfoYes.length/(todayStudentReportInfoYes.length+todayStudentReportInfoNo.length))">
            </el-progress>
          </span>
          <span style="display: flex;margin-bottom: 10px">
             <el-button style="height: 40px;margin-right : 10%;background-color: red"
                        type="primary" @click="directToClassInfo('全部','未上报')">全部未上报{{todayStudentReportInfoNo.length}}人
            </el-button>

            <el-progress :stroke-width="7"
                         type="circle"
                         :width="70"
                         :height="70"
                         style="margin-right: 10%"
                         :percentage="parseInt(100*todayStudentReportInfoNo.length/(todayStudentReportInfoYes.length+todayStudentReportInfoNo.length))"
                         v-if="parseInt(100*todayStudentReportInfoNo.length/(todayStudentReportInfoYes.length+todayStudentReportInfoNo.length))"
                         color="red">
            </el-progress>
          </span>
          <div  v-for="item in selectClasses" v-show="item.classId!='全部'" >
            <span style="display: flex;margin-bottom: 50px">

              <el-button style="height: 40px;margin-right : 10%;"
                         type="primary" @click="directToClassInfo(item.classId,'已上报')"> {{item.classId}}已上报{{item.yesNumber}}人
              </el-button>
              <el-progress :stroke-width="7"
                           type="circle"
                           :width="70"
                           :height="70"
                           style="margin-right: 10%"
                           v-if="parseInt(100*item.yesNumber/(item.yesNumber+item.noNumber))"
                           :percentage="parseInt(100*item.yesNumber/(item.yesNumber+item.noNumber))">
              </el-progress>
            </span>
            <span style="display: flex;margin-bottom: 50px">
               <el-button style="height: 40px;margin-right : 10%;background-color: red"
                          type="primary" @click="directToClassInfo(item.classId,'未上报')"> {{item.classId}}未上报{{item.noNumber}}人
              </el-button>
              <el-progress :stroke-width="7"
                           type="circle"
                           :width="70"
                           :height="70"
                           style="margin-right: 10%"
                           v-if="parseInt(100*item.noNumber/(item.yesNumber+item.noNumber))"
                           :percentage="parseInt(100*item.noNumber/(item.yesNumber+item.noNumber))" color="red">
              </el-progress>
             </span>
          </div>
        </div>
<!--        学生上报详细信息-->
        <div  v-show="this.showTableName===5" style="width:100% ">
          <el-form >
            <el-form-item label="查询:" >
              <el-select v-model="todayStudentReportInfoShowType"  style="width: 33%;margin-right: 5%" >
                <el-option v-for="item in this.todayStudentReportInfoShowTypes"
                           :value="item" :key="item">
                  {{item}}
                </el-option>
              </el-select>
              <el-select v-model="selectClass" style="width: 40%;">
                <el-option v-for="item in this.selectClasses"
                           :value="item.classId" :key="item.classId">
                  {{item.classId}}
                </el-option>
              </el-select>
              <el-button type="primary" @click="searchReportInfo()" style="width: 60%">点击查询</el-button>
            </el-form-item>
          </el-form>
          <div v-show="this.todayStudentReportInfoShowType!='未上报'">
            <el-table
                :data="todayStudentReportInfo"
                style="width: 100%">
              <el-table-column label="操作"  width="120">
                <template slot-scope="scope">
                  <el-button
                      size="mini"
                      width="120" @click="warn(scope.$index, scope.row)">提醒</el-button>
                </template>
              </el-table-column>
              <el-table-column prop="id" label="学号" width="120"></el-table-column>
              <el-table-column  prop="isConfirm" label="是否确诊" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.isConfirm==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isConfirm }}</span>
                  <span  v-else >{{ scope.row.isConfirm }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="isArea" label="风险地区" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.isArea==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isArea }}</span>
                  <span  v-else >{{ scope.row.isArea }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="bodyTemperature" label="体温" width="120">
                <template scope="scope">
                  <span  v-if="scope.row.bodyTemperature>37.3" style="color: red">{{ scope.row.bodyTemperature }}</span>
                  <span  v-else >{{ scope.row.bodyTemperature }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="120"></el-table-column>
              <el-table-column prop="classId" label="班级" width="120"></el-table-column>
              <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            </el-table>
          </div>
          <div v-show="this.todayStudentReportInfoShowType==='未上报'">
            <el-table
                :data="todayStudentReportInfo"
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
              <el-table-column prop="classId" label="班级" width="120"></el-table-column>
              <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            </el-table>
          </div>
        </div>

      </el-main>

    </el-container>
  </el-container>

</template>


<script>
import axios from "axios";

export default {
  name: "Teacher",
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
      reportDate: this.dateFormatter(new Date()),
      time: new Date(),
      classID:[],
      todayStudentReportInfoShowTypes:["未上报","已上报"],
      // 显示的上报内容
      todayStudentReportInfo:[],
      //后台请求的上报内容
      todayStudentReportInfoYes:[],
      todayStudentReportInfoNo:[],
      todayStudentReportInfoShowType:"已上报",
      selectClass:"全部",
      selectClasses:[],
      sexs:['男','女'],
      addresses :['北京市','天津市','上海市','重庆市',
        '河北省','山西省','辽宁省','吉林省','黑龙江省','江苏省',
        '浙江省','安徽省','福建省','江西省','山东省','河南省','湖北省',
        '湖南省','广东省','海南省','四川省','贵州省','云南省','陕西省',
        '甘肃省','青海省','台湾省','内蒙古自治区','广西壮族自治区','西藏自治区',
        '宁夏回族自治区','新疆维吾尔自治区','香港特别行政区','澳门特别行政区'],
      tableData:[],
      name:this.$route.params.uname,
      dialogVisible: false,
      msg:"",
      showTableName:4,
      ruleForm: {
        address2: '上海市',
        bodyTemperature2:36.5,
        isConfirm2:false,
        isArea2:false,
        date2:this.dateFormatter(new Date()) ,
      },
      displayUserBase:true,
      userBase: {
        name:'',
        birthday:null,
        email:'',
        phone:'',
        sex:'',
        idCardNumber:''
      },
      rules: {
        bodyTemperature2: [
          { required: true, message: '请输入体温', trigger: 'blur' }
        ],
        date2: [
          {  required: true, message: '请选择日期', trigger: 'blur' }
        ]
      }
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
    // 身份证验证
    isCardID ( code){
      var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
      var tip = ""
      var pass = true
      if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
        tip = "身份证号格式错误"
        pass = false;
      } else if (!city[code.substr(0, 2)]) {
        tip = "地址编码错误"
        pass = false
      }
      else {
        // 18位身份证需要验证最后一位校验位
        if (code.length === 18) {
          code = code.split('')
          // ∑(ai×Wi)(mod 11)
          // 加权因子
          var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
          // 校验位
          var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2]
          var sum = 0
          var ai = 0
          var wi = 0
          for (var i = 0; i < 17; i++) {
            ai = code[i]
            wi = factor[i]
            sum += ai * wi
          }
          var last = parity[sum % 11];
          if (parity[sum % 11] != code[17]) {
            tip = "校验位错误"
            pass = false
          }
        }
      }

      if (!pass) {
        this.showDialog("你输入的身份证错误"+tip+"输入的身份证:"+code);
      }

    },
    // 手机号验证
    isvalidPhone(str) {
      const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
      let string =reg.test(str);
      if (string!==true){
        this.showDialog("请输入正确的电话号"+str);
      }

    },
    directToClassInfo(clas,type){
      // console.log(clas);
      this.selectClass=clas;
      // console.log(this.selectClass);
      this.showTableName=5;
      this.todayStudentReportInfoShowType=type;
      this.searchReportInfo();
    },
    datePick(){
      // this.teacherStudentTodayReportInfo();
      // this.selectClass='全部';
      // this.todayStudentReportInfoShowType='已上报';
      this.searchReportInfo();
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
    //判断学生上报的查询
    searchReportInfo(){
      this.teacherStudentTodayReportInfo();
      if (this.selectClass=="全部"){
        if (this.todayStudentReportInfoShowType=="未上报"){
          this.todayStudentReportInfo=this.todayStudentReportInfoNo;
        }
        else  if (this.todayStudentReportInfoShowType=="已上报"){
          this.todayStudentReportInfo=this.todayStudentReportInfoYes;
        }
      }
      else {
        var a;
        if (this.todayStudentReportInfoShowType=="未上报"){
          a=this.todayStudentReportInfoNo;
        }
        else  if (this.todayStudentReportInfoShowType=="已上报"){
          a=this.todayStudentReportInfoYes;
        }
        for (var i=a.length-1;i>=0;i--){
          var student=a[i];
          if (student.classId!=this.selectClass)
            a.splice(i,1);
        }
        // console.log(a);
        this.todayStudentReportInfo=a;
      }
    },
    //学生班级上报情况
    teacherStudentTodayReportInfo(){
      var that=this;
      axios.post('/teacherStudentTodayReportInfo'+
          "?id="+this.name+"&date="+this.reportDate)
          .then(response=>{
            console.log(response);
            // console.log(response.data.noReportStudent);
            // this.todayStudentReportInfo=response.data.noReportStudent;
            this.todayStudentReportInfoYes=response.data.reportStudent;
            this.todayStudentReportInfoNo=response.data.noReportStudent;
            this.selectClasses=response.data.teacherAllClass;
            // console.log(this.selectClasses);
            // this.classID=this.selectClasses.splice(this.selectClasses.length-1,1);
          })
          .catch(error=> {
            console.log(error);
            // that.showDialog("服务器已下线，请求失败");
          });
    },
    getUserBaseInfo(){
      var that=this;
      axios.post('/getUserBaseInfo'+
          "?id="+this.name+
          "&type=teacher")
          .then(response=>{
            // console.log(response);
            // console.log(response.data.getUserBaseInfo[0]);
            var s=response.data;
            that.userBase.name=s.name;
            if (s.birthday==="undefined"){
              that.userBase.birthday=null;
            }
            else {
              that.userBase.birthday=s.birthday;
            }
            // that.userBase.birthday=s.birthday;
            that.userBase.email=s.email;
            that.userBase.phone=s.phone;
            that.userBase.sex=s.sex;
            that.userBase.idCardNumber=s.idCardNumber;

          })
          .catch(error=> {
            console.log(error);
            that.showDialog("服务器已下线，请求失败")
          });
    },
    submitUserBaseInfo(form){
      var that=this;
      this.displayUserBase=true;
      axios.post('/submitUserBaseInfo'+
          "?id="+this.name+
          "&name="+form.name+
          "&birthday="+form.birthday+
          "&email="+form.email+
          "&phone="+form.phone+
          "&sex="+form.sex+
          "&idCardNumber="+form.idCardNumber+
          "&type=teacher")
          .then(response=>{
            console.log(response);
            // console.log(response.data.message);
            if(response.data.message==='true'){
              that.showDialog("保存成功");
            }
            else if (response.data.message==='false'){
              that.showDialog("保存失败");
            }
          })
          .catch(error=> {
            console.log(error);
            that.showDialog("服务器已下线，上报失败")
          });
    },
    showDialog(msg){
      this.dialogVisible=true;
      this.msg=msg;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var that=this;
          axios.post('/setReportInfo?reportDate='+this.ruleForm.date2+
              "&bodyTemperature="+this.ruleForm.bodyTemperature2+
              "&isConfirm="+this.ruleForm.isConfirm2+
              "&isArea="+this.ruleForm.isArea2+
              "&address="+this.ruleForm.address2+
              "&uameId="+this.name)
              .then(response=>{
                console.log(response);
                // console.log(response.data.message);
                if(response.data.message==='true'){
                  that.showDialog("提交成功");
                  this.getStudentReportHistory(this.name);
                }
                else if (response.data.message==='false'){
                  that.showDialog("提交失败");
                }
                else if (response.data.message==='已上报无需重复上报'){
                  that.showDialog("已上报，更新上报内容成功");
                  this.getStudentReportHistory(this.name);
                }
                else if (response.data.message==='不能超期上报'){
                  that.showDialog("请正确填报上报日期");
                }

              })
              .catch(error=> {
                console.log(error);
                that.showDialog("服务器已下线，上报失败")
              });
        } else {
          console.log('error submit!!');
          this.showDialog("请完善提交信息");
          return false;
        }
      });

    },
    handleDelete(index, row) {
      // console.log(index, row);
      var that=this;
      // console.log( row.uameId);
      // console.log( row.reportDate);
      axios.post('/deleteStudentReport?uameId='+row.uameId+"&reportDate="+row.reportDate)
          .then(response=>{
            // console.log(response);
            // console.log(response.data.message);
            if(response.data.message===true){
              that.showDialog("删除成功");
              that.getStudentReportHistory(row.uameId);
            }
            else if (response.data.message===false){
              that.showDialog("删除失败");
              // console.log("删除失败");
            }
          })
          .catch(error=> {
            console.log(error);
            that.showDialog("请联系管理员,服务器已下线！！")
          });


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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getStudentReportHistory(name){
      var that=this;
      axios.post('/getStudentReportHistory?uname='+name)
          .then(response=>{
            console.log(response);
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
    this.getStudentReportHistory(this.name);
    this.getUserBaseInfo();
    this.teacherStudentTodayReportInfo();
    this.todayStudentReportInfo=this.todayStudentReportInfoNo;
    // console.log("初始化成功");
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
