<template>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
      <div class="login-card">
        <div class="title-container">
          <span class="card-title">YNetwork 管理系统登录</span>
        </div>
          <!-- 登录表单 -->
          <el-form class="login-form" ref="login" :model="loginForm" :rules="loginRules">
            <el-form-item prop="username">
              <el-input placeholder="用户名" @keyup.enter="loginHandler" v-model="loginForm.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="password" placeholder="密码" autocomplete="off" v-model="loginForm.password" @keyup.enter="loginHandler"></el-input>
            </el-form-item>

              <el-form-item prop="captcha">
                <div class="captcha-wrap">
                  <el-input placeholder="验证码" autocomplete="off" v-model="loginForm.captcha" @keyup.enter="loginHandler"></el-input>
                  <img :src="captchaSrc" class="captcha-img" @click="getCaptcha"/>
                </div>
              </el-form-item>


            <div class="login-options">
              <el-checkbox label="7天内免登录" v-model="loginForm.rememberMe"></el-checkbox>
              <a href="" class="forgot-password">忘记密码?</a>
            </div>
            <el-form-item>
              <el-button type="primary" class="login-btn" :loading="loading" @click="loginHandler">
                 <span v-if="!loading">登录</span>
                 <span v-else>登录中...</span>
              </el-button>
            </el-form-item>
          </el-form>  
      </div>
</template>


<script lang="ts">
import { useRouter } from "vue-router";
import { ref,reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { useStore } from "vuex";

export default {
  setup() {
    const router = useRouter();

    const captchaSrc = ref(process.env.VUE_APP_BASE_API + "/captcha/render?" + new Date().getTime());

    const getCaptcha = () => {
        captchaSrc.value = process.env.VUE_APP_BASE_API + "/captcha/render?" + new Date().getTime();
    };

    let loading = ref(false);
    
    const store = useStore();

    const login: any = ref(null);

    const loginForm = reactive({
      username: "",
      password: "",
      captcha: "",
      rememberMe: false,
    });

    const loginRules = {
      username: [
        {
          required: true,
          message: "请输入用户名",
          trigger: "blur",
        },
        {
              min: 5,
              max: 12,
              message: '用户名长度不合法',
              trigger: 'blur',
        }
      ],
      password: [
        {
          required: true,
          message: "请输入密码",
          trigger: "blur",
        },
        {
              min: 6,
              max: 18,
              message: '密码长度不合法',
              trigger: 'blur',
        }
      ],
      captcha: [
        {
          required: true,
          message: "请输入验证码"
        }
      ]
    };

    const loginHandler = () => {

        login.value.validate((valid:any) => {
          if(valid){
            loading.value = true;

            store.dispatch("Login",loginForm).then(() => {
              router.push({path: '/'});
            }).catch(() => {
              getCaptcha();
              loading.value = false;
            });


          }
        });
        
      };

     return{
        loginForm,
        loginRules,
        login,
        loginHandler,
        loading,
        captchaSrc,
       getCaptcha,
      } 
  },

};
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
    .captcha-wrap {
      display: flex;
      .captcha-img {
        margin-left: 1rem;
        width: 7rem;
        &:hover {
          cursor: pointer;
        }
      }
    }

@media screen and (max-width: 480px){
    .login-container {
        width: 100vw;
        padding-top: 6rem;
        padding-left: 2rem;
    }
}
</style>