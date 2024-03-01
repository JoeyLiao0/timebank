# timebank 
 
## 描述
  一个简单的C2C javaweb项目 

## 环境配置、软件 
1、前端vue项目：
    vscode、node.js(20.11.0)  
    利用node.js自带的npm下载以下依赖  
    "dependencies": {  
    "axios": "^1.6.7",  
    "core-js": "^3.8.3",  
    "element-ui": "^2.15.14",  
    "vue": "^2.6.14",  
    "vue-router": "^3.6.5"  
  }  
2、后端java项目  
    IDEA、maven(3.9.6)、tomcat（9.0.85）、jdk17  
3、其他  
    PDManer(数据库建模工具)、MySQL(8.0.35)、MySQL Workbench、Git Bash  

## 2024/2/21
  优化servlet层设计，通过用户类别划分servlet，通用的放到common，单个展示模块对应单个servlet<br>
## 2024/2/22      
  优化service层设计，舍弃loginService ，因为如果把login和其他混在一起不伦不类，有点像微服务和常规模式混合<br>
  优化后，单张表对应单个逻辑实体对应单个mapper对应单个service<br>
## 2024/2/23
  使后端逻辑划分更合理，同时画了登录流程图，大致实现了登录流程相关代码<br>
  基本设计并实现了dao的接口，待测试<br>
## 2024/3/1
  加入了fastjson库，后端部分实现了涉及账号管理的接口，并用apifox测试通过<br>

