<template>
    <div id="home">    
      <el-container style="height:100%; min-height:500px ; min-width: 800px; background-color: rgb(107, 130, 130);">
        <el-header style="height:10%; background-color: rgb(176, 195, 195); display: flex;">
            <div class="logo_name">
              <i class="el-icon-watch"></i>
              <span style="color:rgb(7, 14, 13);">时间银行</span>
                <img class="userImg" src="./l2.png">
                <i class="username">乐观阿索克</i>
                <i class="level">管理员</i>
            </div>
        </el-header>
        <el-container style="height:90%; min-height: 450px; background-color: rgb(46, 78, 78);">
          <el-aside style="height: 100%; width: 20%; background-color:rgb(84,92,100);">
            <el-menu style="border: 5px;" 
                    background-color="#545c64" text-color="#ffffff"
                    active-text-color="#ffd04b" :default-active="activeIndex" @select="selectIndex">
                    <!-- index 里换成 字符串，转化时 用名字显示 -->
                <el-menu-item index="CUM"><i class="el-icon-user"></i>普通用户管理</el-menu-item>
                <el-menu-item index="AM"><i class="el-icon-user"></i>审核员管理</el-menu-item>
                <el-menu-item index="CSM"><i class="el-icon-user"></i>客服管理</el-menu-item>
                <el-menu-item index="TCM"><i class="el-icon-coin"></i>时间币管理</el-menu-item>
                <el-menu-item index="ICC"><i class="el-icon-chat-round"></i>内部交流通道</el-menu-item>
                <el-menu-item index="AP"><i class="el-icon-message"></i>公告发布</el-menu-item>
                <el-menu-item index="TAM"><i class="el-icon-s-data"></i>任务监控</el-menu-item>
                <el-menu-item index="TRM"><i class="el-icon-s-data"></i>流量监控</el-menu-item>
                <el-menu-item index="SOPF"><i class="el-icon-document"></i>往期反馈汇总</el-menu-item>
                <el-menu-item index="SOPT"><i class="el-icon-document"></i>往期任务汇总</el-menu-item>
                <el-menu-item index="SOPA"><i class="el-icon-document"></i>往期审核汇总</el-menu-item>
                <el-menu-item index="MA"><i class="el-icon-setting"></i>我的账号</el-menu-item>
                
                <el-menu-item index="TR"><i class="el-icon-setting"></i>任务审核</el-menu-item>
                <el-menu-item index="AROPT"><i class="el-icon-setting"></i>往期任务审核记录</el-menu-item>
                <el-menu-item index="FP"><i class="el-icon-setting"></i>反馈处理</el-menu-item>
                <el-menu-item index="ROPF"><i class="el-icon-setting"></i>往期反馈处理记录</el-menu-item>
                
            </el-menu>
          </el-aside>
          <el-main style="height: 100%;width: 80%;">
            main
            <!-- 用动态组件切换，根据activeIndex -->
            <component :is="activeIndex"></component>
          </el-main>
        </el-container>
      </el-container>  
      
      <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
      <!-- <HelloWorld msg="Welcome"/> -->
    </div>
  </template>
  
  <script>
  // import HelloWorld from './components/HelloWorld.vue'
  import CUM from './components/CUM.vue'
  import AM from './components/AM.vue'
  import AP from './components/AP.vue'
  import CSM from './components/CSM.vue'
  import ICC from './components/ICC.vue'
  import MA from './components/MA.vue'
  import SOPA from './components/SOPA.vue'
  import SOPT from './components/SOPT.vue'
  import SOPF from './components/SOPF.vue'
  import TAM from './components/TAM.vue'
  import TCM from './components/TCM.vue'
  import TRM from './components/TRM.vue'
  import TR from './components/TR.vue'
  import AROPT from './components/AROPT.vue'
  import FP from './components/FP.vue'
  import ROPF from './components/ROPF.vue'
  
  
  export default {
    name: 'home',
    components: {
      CUM,
      AM,
      AP,
      CSM,
      ICC,
      MA,
      SOPA,
      SOPT,
      SOPF,
      TAM,
      TCM,
      TRM,
      TR,
      AROPT,
      FP,
      ROPF
    },
    data () {
      return {
        activeIndex:'CUM'//默认第一个即普通用户管理为展示主页
      }
    },
    mounted () {
      console.log(new Date());//证明刷新后vue实例也刷新
    },
    methods:{
      selectIndex:function(key){
        this.activeIndex=key;//这里之后变成组件名的赋值
      }
    }
  }
  </script>
  
  <style>
  
  html{
    height: 100%;
  }
  body{
    margin:auto;
    height: 100%;
  }
  #home{
    margin: 0px;
    width: 100%;
    height: 100%;
  }
  .logo_name{
    font-size :30px;
    font-family: Georgia, serif;
    width: 100%;
    height: auto;
    margin:auto 0;
    padding-left: 2%;
  }
  .userImg{
    position: absolute;
    right: 30px;  
    width: 40px;
    height: 40px;
    border-radius:50%;
  
  }
  .username{
    width: fit-content;
    position: absolute;
    right: 86px;  
    font-size: medium;
    color: rgb(55, 87, 116);
  }
  .level{
    width: fit-content;
    position: absolute;
    margin-top: 22px;
    right: 86px;  
    font-size: small;
    color: rgb(115, 143, 170);
  }
  </style>
  