<template>

  <center >
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="90%"
        >
      <span>{{this.messsgae}}</span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
    </el-dialog>
    <img alt="Vue logo" src="../assets/logo_mobile.jpg" width="90%" height="300px" >
    <div style="width: 100%;" >
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="20%"  class="demo-ruleForm" label-position="left">
        <el-form-item label="登录身份" >
          <el-select v-model="ruleForm.type" style="width: 70%">
            <el-option label="学生" value="student"></el-option>
            <el-option label="教师" value="teacher"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="class" v-show="ruleForm.type==='student'">
          <el-select v-model="ruleForm.class"  style="width: 70%">
            <el-option v-for="item in ruleForm.classes"
                       :value="item.classId" :key="item.className" @change="">
              {{item.className}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名" prop="uname">
          <el-input v-model.number="ruleForm.uname" style="width: 70%"></el-input>
        </el-form-item>

        <el-form-item
            prop="email"
            label="邮箱"
            :rules="[{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }]">
          <el-input v-model="ruleForm.email" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="邮箱令牌" prop="emailPwd">
          <el-input v-model.number="ruleForm.emailPwd" style="width: 70%"></el-input>
        </el-form-item>


        <el-form-item label="密码" prop="pass">
          <el-input style="width: 70%" type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input style="width: 70%" type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button type="primary" @click="getEmailPwd">获取令牌</el-button>
<!--          <el-button @click="resetForm('ruleForm')">重置</el-button>-->
          <el-button type="primary"  @click="toLogin()">返回登录</el-button>
        </el-form-item>

      </el-form>
    </div>

  </center>
</template>

<script>

import axios from "axios";
import router from "@/router";

export default {
  name: "Register",
  data() {
    var checkClass = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('班级不能为空'));
      }
      else
        callback();
    };
    var checkUname = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('用户名不能为空'));
      }
      else if(value === 'isHave'){
        return callback(new Error('改用户已经存在'));
      }
      else
        callback();
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        pass: '',
        checkPass: '',
        uname: '',
        type:'',
        class:"",
        email:"",
        emailPwd:"",
        loadEmailPwd:"",
        classes: []
      },
      //dialog
        dialogVisible:false,
        messsgae:"",
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        uname: [
          { validator: checkUname, trigger: 'blur' }
        ],
        class: [
          { validator: checkClass, trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    var that=this;
    axios.post('/getAllclasses')
        .then(response=>{
          var data=response.data;
          // console.log(data);
          var getAllClasses=data.getAllClasses;
          // console.log(getAllClasses);
          that.ruleForm.classes=getAllClasses;
        })
        .catch(error=> {
          console.log(error);
          that.showDialog("请联系管理员,服务器已下线！！")
        });
  },
  methods: {
    toLogin(){
      this.$router.push('/');
    },
    getEmailPwd(){
      var that=this;
      if (this.ruleForm.email===''){
        this.showDialog("请正确填入邮箱!");
      }
      else{
        axios.post('/getEmailPwd?email='+this.ruleForm.email+"&uname="+this.ruleForm.uname)
            .then(response=>{
              console.log(response);
              console.log(response.data.pwd);
              that.ruleForm.loadEmailPwd=response.data.pwd;
              if (that.ruleForm.loadEmailPwd==="已存在该用户，无法注册"){
                that.showDialog("已存在该用户，无法注册！");
                that.ruleForm.uname='';
              }
              else {
                that.showDialog("邮件已发送令牌，3分钟内有效！");
              }

            })
            .catch(error=> {
              console.log(error);
              that.showDialog("请联系管理员,服务器已下线！！");
            });
      }
    },
    checkEmailPwd(){
      var that=this;
      if (this.ruleForm.uname===''||this.ruleForm.pass===''){
        this.showDialog("请正确填入账户信息！");
      }
      if (this.ruleForm.class===''&&this.ruleForm.type==='student'){
        this.showDialog("请正确填入账户信息！");
      }
      if (this.ruleForm.email===''||this.ruleForm.emailPwd===''){
        this.showDialog("请正确填入邮箱和邮箱中获取的令牌！");
      }
      else{
        axios.post('/checkEmailPwd?emailPwd='+this.ruleForm.emailPwd+"&uname="+this.ruleForm.uname)
            .then(response=>{
              console.log(response);
              console.log("checkEmailPwd"+response.data.message);
             if (response.data.message==='成功'){
               that.showDialog("注册成功")
               this.ruleForm.loadEmailPwd='成功';
             }
             else if(response.data.message==='失败'){
               this.ruleForm.loadEmailPwd='失败';
               that.showDialog("输入的令牌有误")
             }
            })
            .catch(error=> {
              console.log(error);
              that.showDialog("请联系管理员,服务器已下线！！")
            });
      }
    },

    submitForm() {
      var that=this;
      this.checkEmailPwd();
      var success="是";
      console.log(" this.ruleForm.loadEmailPwd    "+ this.ruleForm.loadEmailPwd);
     if (this.ruleForm.uname===''||this.ruleForm.pass===''){
       this.showDialog("请正确填入账户信息！");
       success="否";
     }
     if (this.ruleForm.class===''&&this.ruleForm.type==='student'){
       this.showDialog("请正确填入账户信息！");
       success="否";
     }
      if (this.ruleForm.email===''||this.ruleForm.emailPwd===''){
        this.showDialog("请正确填入邮箱和邮箱中获取的令牌！");
        success="否";
      }
      if (this.ruleForm.loadEmailPwd==='失败'){
        console.log("loadEmailPwd    "+this.ruleForm.loadEmailPwd);
        this.showDialog("令牌错误,请核对邮箱后重新发送！");
        success="否";
      }
      console.log(success);
      if ( success==="是"&&this.ruleForm.loadEmailPwd==='成功'){
       axios.post('/register?uname='+this.ruleForm.uname+"&upwd="+this.ruleForm.pass+"&type="+this.ruleForm.type+"&class="+
           this.ruleForm.class+"&email="+this.ruleForm.email)
           .then(response=>{
             console.log(response);
             console.log(response.data.message);
             if(response.data.message=='true'&&that.ruleForm.type==='student'){
               router.push({ name: 'Student', params: { uname: this.ruleForm.uname }});
             }
             if(response.data.message=='true'&&that.ruleForm.type==='teacher'){
               // that.showDialog("教师端现在还未开发完成。")
               router.push({ name: 'Teacher', params: { uname: this.ruleForm.uname }});
             }
             else if (response.data.message=='false'){
               that.showDialog("该用户已经存在");
             }
           })
           .catch(error=> {
             console.log(error);
             that.showDialog("请联系管理员,服务器已下线！！")
           });
     }



    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    showDialog(msg){
      this.dialogVisible=true;
      this.messsgae=msg;
    }
  }
}
</script>

<style scoped>

</style>
