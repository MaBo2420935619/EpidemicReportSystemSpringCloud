<template>
  <el-container style="height: 100%; border: 1px solid #eee">


    <el-container>

      <el-header style="text-align: right; font-size: 25px; height: 60px;">
        <el-dropdown>
          <i class="el-icon-setting" ></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><el-button type="text" @click="toLogin">注销</el-button></el-dropdown-item>
            <el-dropdown-item><el-button type="text" @click="showDialog('本系统由 mabo 独家创作，翻版必究！！')">关于</el-button></el-dropdown-item>
            <el-dropdown-item><el-button type="text" @click="showDialog('客服邮箱:2420935619@qq.com')">客服</el-button></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span style="margin-right: 30px"> 用户名:{{this.name}}</span>
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

      <div >
        <el-button type="primary" style=" margin-top: 5px ;width: 34%" plain
                   @click="showWitch(1);">查看历史数据</el-button>
        <el-button type="primary" style="margin-top: 5px;width: 25%" plain
                   @click="showWitch(2)">疫情上报</el-button>
        <el-button type="primary" style=" margin-top: 5px;width: 34%" plain
                   @click="showWitch(3)">完善个人信息</el-button>


      </div>
<!--      显示历史信息-->
      <el-main style="width: 100%;" >
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
          <el-table-column prop="reportdate" label="上报日期" width="120"></el-table-column>
          <el-table-column prop="bodytemperature" label="体温" width="120">
            <template scope="scope">
              <span  v-if="scope.row.bodytemperature>37.3" style="color: red">{{ scope.row.bodytemperature }}</span>
              <span  v-else >{{ scope.row.bodytemperature }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="isconfirm" label="是否确诊新冠" width="120">
            <template scope="scope">
              <span  v-if="scope.row.isconfirm==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isconfirm }}</span>
              <span  v-else >{{ scope.row.isconfirm }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="isarea" label="途经风险地区" width="120">
            <template scope="scope">
              <span  v-if="scope.row.isarea==='是'" style="background-color: red; color: #d7edff" >{{ scope.row.isarea }}</span>
              <span  v-else >{{ scope.row.isarea }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="上报地点"></el-table-column>
        </el-table>


        <!--      显示上报内容-->
        <el-form :model="ruleForm"
                 v-show="this.showTableName===2"
                 :rules="rules" ref="ruleForm" label-width="30%"  class="demo-ruleForm"
        style="font-size: 18px">
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
            <el-select v-model="ruleForm.address2" >
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
          <el-form-item label="班级">
            <el-input v-model="userBase.class"
                      :disabled="true">
            </el-input>
          </el-form-item>
          <el-form-item :disabled=true>
            <el-button type="primary" v-show="!displayUserBase" @click="submitUserBaseInfo(userBase)">保存</el-button>
            <el-button type="primary" v-show="displayUserBase" @click="displayUserBase=false">编辑</el-button>
          </el-form-item>

        </el-form>

      </el-main>

    </el-container>
  </el-container>

</template>

<script>


import router from "@/router";
import axios from "axios";

export default {
  name:"Student",
  data() {
    return {
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
      showTableName:1,
      ruleForm: {
        address2: '上海市',
        bodyTemperature2:36.5,
        isConfirm2:false,
        isArea2:false,
        date2:this.dateFormatter(new Date()),
      },
      displayUserBase:true,
      userBase: {
        name:'',
        birthday:null ,
        email:'',
        phone:'',
        sex:'',
        idCardNumber:'',
        class:''
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
    getUserBaseInfo(){
      var that=this;
      axios.post('/order-service/getUserBaseInfo'+
          "?id="+this.name+
          "&type=student"+'&token='+this.GLOBAL.token)
          .then(response=>{
            console.log(response);
            // console.log(response.data.getUserBaseInfo[0]);
            var s=response.data;
            that.userBase.name=s.name;
            if (s.birthday==="undefined"){
              that.userBase.birthday=null;
            }
            else {
              that.userBase.birthday=s.birthday;
            }
            that.userBase.email=s.email;
            that.userBase.phone=s.phone;
            that.userBase.sex=s.sex;
            that.userBase.idCardNumber=s.idcardnumber;
            that.userBase.class=s.classId;
          })
          .catch(error=> {
            console.log(error);
            that.showDialog("服务器已下线，请求失败")
          });
    },
    submitUserBaseInfo(form){
      var that=this;
      this.displayUserBase=true;
      axios.post('/order-service/submitUserBaseInfo'+
          "?id="+this.name+
          "&name="+form.name+
          "&birthday="+form.birthday+
          "&email="+form.email+
          "&phone="+form.phone+
          "&sex="+form.sex+
          "&idCardNumber="+form.idCardNumber+
          "&type=student"+'&token='+this.GLOBAL.token)
          .then(response=>{
            // console.log(response);
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
          axios.post('/order-service/setReportInfo?reportDate='+this.ruleForm.date2+
              "&bodyTemperature="+this.ruleForm.bodyTemperature2+
              "&isConfirm="+this.ruleForm.isConfirm2+
              "&isArea="+this.ruleForm.isArea2+
              "&address="+this.ruleForm.address2+
              "&uameId="+this.name+'&token='+this.GLOBAL.token)
              .then(response=>{
                // console.log(response);
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
      console.log(index, row);
      var that=this;
      // console.log( row.uameId);
      // console.log( row.reportDate);
      axios.post('/order-service/deleteStudentReport?uameId='+row.uameid+"&reportDate="+row.reportdate+'&token='+this.GLOBAL.token)
          .then(response=>{
            console.log(response);
            // console.log(response.data.message);
            if(response.data.message===true){
              that.showDialog("删除成功");
              that.getStudentReportHistory(row.uameid);
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
      axios.post('/order-service/getStudentReportHistory?uname='+name+'&token='+this.GLOBAL.token)
          .then(response=>{
            console.log(response);
            var a=response.data;
            that.tableData=a;
          })
          .catch(error=> {
            console.log(error);
            that.showDialog("请联系管理员,服务器已下线！！")
          });
      this.showTableName=1;
    }
  },
  created() {
    if (this.$route.params.uname===undefined){
      this.$router.push('/');
    }
    this.name= this.$route.params.uname;
    this.getStudentReportHistory(this.name);
    this.getUserBaseInfo()
  },

};
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

</style>
