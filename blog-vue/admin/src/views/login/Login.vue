<template>
  <div class="login-container">
      <div class="login-card">
          <div class="login-title">管理员登录</div>
          <!-- 登录表单 -->
          <el-form :model="form" ref="form" :rules="rules" label-width="80px" :inline="false" size="normal">
            <el-form-item label="">
              <el-input></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">立即创建</el-button>
              <el-button>取消</el-button>
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