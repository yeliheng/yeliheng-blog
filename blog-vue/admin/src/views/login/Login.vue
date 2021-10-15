<template>
  <div class="login-container">
      <div class="login-card">
        <div class="title-container">
          <span class="login-title">管理员登录</span>
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

    .login-container{
      position: absolute;
      border-radius: $radius-value;
    }

    .login-card{
      width: 22.8rem;
      height: 28.5rem;
      background: #fff;
      //border-radius: $radius-value;
    }

    .title-container{
      display: flex;
      align-items: center;
      color: #758093;
      justify-content: center;
      width: 100%;
      height: 4.28rem;
      > .login-title{
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
      width: 17.7rem;
      height: 3rem;
      margin-left: 11px;
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
</style>