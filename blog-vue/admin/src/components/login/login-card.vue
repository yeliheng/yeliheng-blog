<template>

      <div class="login-card">
        <div class="title-container">
          <span class="card-title">YNetwork 管理系统登录</span>
        </div>
          <!-- 登录表单 -->
          <el-form class="login-form" ref="login" :model="loginForm" :rules="loginRules">
            <el-form-item prop="username">
              <el-input placeholder="用户名" @keyup.enter="loginHandler" v-model="loginForm.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input type="password" placeholder="密码" autocomplete="off" v-model="loginForm.password" @keyup.enter="loginHandler"></el-input>
            </el-form-item>
            <div class="login-options">
              <el-checkbox label="记住密码" v-model="loginForm.rememberMe"></el-checkbox>
              <a href="" class="forgot-password">忘记密码?</a>
            </div>
            <el-form-item>
              <el-button type="primary" class="login-btn" @click="loginHandler">登录</el-button>
            </el-form-item>
          </el-form>  
      </div>
</template>


<script lang="ts">
import {useRouter} from "vue-router";
import { ref,reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { useStore } from "vuex";

export default {
  setup() {
    const router = useRouter();
    
    const store = useStore();

    const login: any = ref(null);

    const loginForm = reactive({
      username: "",
      password: "",
    });

    const loginRules = {
      username: [
        {
          required: true,
          message: "请输入用户名",
          trigger: "blur",
        }
      ],
      password: [
        {
          required: true,
          message: "请输入密码",
          trigger: "blur",
        }
      ],
    };

    const loginHandler = () => {
        login.value.validate((valid:any) => {
          if(valid){
            store.dispatch("Login",loginForm).then(() => {
                console.log("登录");
            });
          } else{
            return false;
          }
        });
      };

     return{
        loginForm,
        loginRules,
        login,
        loginHandler
      } 
  },

};
const login = ref(null);
</script>


<style lang="scss" scoped>

    $radius-value: 16px;


    .login-card{
      opacity: 0.95;
      display: flex;
      flex-direction: column;
      width: 22.8rem;
      height: 28.5rem;
      background: #fff;
      border-radius: $radius-value;
    }

    .title-container{
      display: flex;
      align-items: center;
      color: #758093;
      justify-content: center;
      width: 100%;
      height: 4.28rem;
      > .card-title{
        z-index: 2;
        background: #fff;
        font-size: 16px;
        font-weight: bold;
    }
    
  }
    
    .login-form{
      margin: {
        left: 1.7rem;
        right: 1.7rem;
      };
    }

    .login-btn{
      width: 100%;
      height: 3rem;
      margin-top: 1rem;
    }

    .login-options{
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    }

    a{
      color: #409EFF;
    }

@media screen and (max-width: 480px){
    .login-container {
        width: 100vw;
        padding-top: 6rem;
        padding-left: 2rem;
    }
}
</style>