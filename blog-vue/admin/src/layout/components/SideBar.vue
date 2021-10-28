<template>
        <div class="logo-container">
            <span>YNetwork-后台管理</span>
        </div>
        <el-menu
            class="side-bar-menu"
            @open="handleOpen"
            @close="handleClose"
            
        >
        <el-sub-menu index="1">
          <template #title>
            <i class="el-icon-location"></i>
            <span>测试菜单1</span>
          </template>
            <el-menu-item index="1-1">测试子菜单1</el-menu-item>
            <el-menu-item index="1-2">测试子菜单2</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="2">
          <i class="el-icon-menu"></i>
          <span>测试菜单2</span>
        </el-menu-item>
        <el-menu-item index="3" disabled>
          <i class="el-icon-document"></i>
          <span>测试菜单3</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-setting"></i>
          <span>测试菜单4</span>
        </el-menu-item>
      </el-menu>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  setup() {
    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath)
    }
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath)
    }
    return {
      handleOpen,
      handleClose,
    }
  },
})
</script>

<style lang="scss" scoped>
.logo-container{
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.14rem;
    font-family: Source Han Sans CN;
    font-weight: bold;
    color: #666666;
    opacity: 1;
    width: 17.14rem;
    height: 6.86rem;
}  
.side-bar-menu{
    width: 17.14rem; 
    border: 0px;
}
</style>