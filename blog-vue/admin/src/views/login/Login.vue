<template>

  <div class="login-header">
      <span class="header-title">YNetwork-后台管理系统</span>
  </div>
  <div class="login-container">
      <div class="login-card">
        <div class="title-container">
          <span class="card-title">管理员登录</span>
        </div>
          <!-- 登录表单 -->
          <el-form class="login-form">
            <el-form-item prop="username">
              <el-input placeholder="用户名" @keyup.enter="login"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input type="password" placeholder="密码"></el-input>
            </el-form-item>
            <div class="login-options">
              <el-checkbox label="记住密码"></el-checkbox>
              <a href="" class="forgot-password">忘记密码?</a>
            </div>
            <el-form-item>
              <el-button type="primary" class="login-btn">登录</el-button>
            </el-form-item>
          </el-form>
          
          
        
          
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>

    $radius-value: 16px;
    
    .login-header{
      top: 0;
      width: 100%;
      height: 6rem;
      
      background: rgba($color: #fff, $alpha: 0.2);
      display: flex;
      align-items: center;
      > .header-title{   
        color: rgb(233, 233, 233);
        line-height: 3.86rem;
        font-weight: 400;
        font-size: 2rem;
        font-family: Source Han Sans CN;
        margin-left: 6%;
      }
      
    }

    .login-container{
      
      border-radius: $radius-value;
      padding-top: 13rem;
      padding-left: 15rem;
    }

    .login-card{
      opacity: 0.8;
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

    .background{

      -webkit-box-flex: 1;
      -ms-flex: 1;
      flex: 1;
      margin-left: auto;
      margin-right: 6rem;
      background-size: contain;
      background-repeat: no-repeat;
      background-position: 100% 0;
      margin-bottom: 5rem;
      min-height: 50rem;
      max-height: calc(100vh - 13rem);
      background-image: url(../../assets/images/login-background.svg);
    }
@media screen and (max-width: 480px){
    .login-container {
        width: 100vw;
        padding-top: 6rem;
        padding-left: 2rem;
    }
}
</style>