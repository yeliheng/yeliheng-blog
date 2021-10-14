<template>
  <div class="login-container">
      <div class="login-card">
          <div class="login-title">管理员登录</div>
          <!-- 登录表单 -->
          <el-form 
            :model="loginForm" 
            ref="ruleForm"
            class="login-form"
            >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="用户名"
                @keyup.enter="login"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-input type="password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">登录</el-button>
            </el-form-item>
          </el-form>
          
          
        
          
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
    
</style>