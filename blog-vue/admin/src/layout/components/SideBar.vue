<template>
        <div class="logo-container">
            <span>YNetwork-后台管理</span>
        </div>
        <el-menu
            class="side-bar-menu"
            @open="handleOpen"
            @close="handleClose"
            router
        >
            <template v-for="route of this.$store.state.permission.addRoutes">
                <!-- 二级菜单标题 -->
                <template v-if="route.name && route.children && !route.hidden">
                    <el-sub-menu :key="route.path" :index="route.path">
                        <template #title>
                            <i :class="route.icon"/>
                            <span class="menu-title"> {{ route.name }}</span>
                        </template>
                        <!-- 二级菜单选项 -->
                        <template v-for="(item, index) of route.children">
                            <el-menu-item v-if="!item.hidden" :key="index" :index="item.path">
                                <i :class="item.icon" />
                                <span class="menu-item-title">{{ item.name }}</span>
                            </el-menu-item>
                        </template>
                    </el-sub-menu>
                </template>
                <!-- 一级菜单 -->
                <template v-else-if="!route.hidden">
                    <el-menu-item :index="route.path" :key="route.path">
                        <i :class="route.icon" />
                         <span class="menu-title"> {{ route.name }}</span>
                    </el-menu-item>
                </template>
            </template>
      </el-menu>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import store from '@/store';

export default defineComponent({
  setup() {
      console.log(store);
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
    i{
        margin-bottom: 0.21rem;
        margin-right: 0.8rem;
        color: #666666;
    }
    
}
.menu-title{
    color: #666666;
    font-weight: bold;
}
.menu-item-title{

    color: #666666;
}

</style>