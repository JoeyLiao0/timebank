import Vue from 'vue';  
import VueRouter from 'vue-router';  
import LoginPage from './components/login/login.vue'; // 导入登录页面组件  
import HomePage from './components/home/home.vue'; // 导入主页面组件  
  
Vue.use(VueRouter);  
  
const routes = [  
  {  
    path: '/',  
    name: 'Login',  
    component: LoginPage  
  },  
  {  
    path: '/home',  
    name: 'Home',  
    component: HomePage  
  }  
];  
  
const router = new VueRouter({  
  mode: 'hash',  
  routes  
});  
  
export default router;