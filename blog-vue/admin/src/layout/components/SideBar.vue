<template>
        <el-menu
            class="side-bar-menu"
            :class="{mobile: isMobile,close: sidebarClosed,}"
            @open="handleOpen"
            @close="handleClose"
            :collapse="collapse"
            router
        >
            <div class="logo-container">
                <span>YNetwork-后台管理</span>
            </div>
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
                            <el-menu-item v-if="!item.hidden" :key="index" :index="item.path"  @click="handleMenuClick">
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
import { defineComponent,computed,watch,onMounted,ref } from 'vue'
import { useStore } from 'vuex';

export default defineComponent({
  setup() {
    const store = useStore();
    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath)
    } 
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath)
    }

    const handleMenuClick = () => {
        if(isMobile.value){
            store.state.app.sidebarClosed = true;
        }
    }

    const collapse = computed(() => 
        store.state.app.sidebarCollapse
    );
    const isMobile = computed(() => 
        store.state.app.isMobile
    );
 
    const sidebarClosed = computed(() => store.state.app.sidebarClosed);

    let clientWidth:any = ref(document.body.clientWidth);
    if(clientWidth.value < 650 && !isMobile.value){
        //手机
        store.dispatch('toggleMobile');
    }else if(clientWidth.value > 650 && isMobile.value){
        //电脑
        store.dispatch('toggleMobile');
    }

    watch(clientWidth,(newVal:number,oldVal:number) => {

        if(newVal < 650 && !isMobile.value){
            //手机
            store.dispatch('toggleMobile');
        }else if(newVal > 650 && isMobile.value){
            //电脑
            store.dispatch('toggleMobile');
        }
    });

    onMounted(() => {
        
        window.onresize = () => {
            clientWidth.value = document.body.clientWidth;
        }
    })
    
    return {
      handleOpen,
      handleClose,
      collapse,
      isMobile,
      sidebarClosed,
      handleMenuClick,
    }
  },
})
</script>

<style lang="scss" scoped>

.side-bar-menu:not(.el-menu--collapse) {
    width: 17.14rem; 
    .logo-container{  
        opacity: 1;
    }
}
.logo-container{
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.14rem;
        font-family: Source Han Sans CN;
        font-weight: bold;
        color: #666666;
        opacity: 0;
        height: 6.86rem;
        white-space: nowrap;
    }  
.side-bar-menu{
    border: 0px;
    i{
        margin-bottom: 0.21rem;
        margin-right: 0.8rem;
        color: #666666;
    }
    
}
.mobile{
    overflow: auto;
    z-index: 9999;
    height: 100%;
    position: fixed;
    border: 0px;
    transition: opacity 0.5s;
    opacity: 1;
    i{
        margin-bottom: 0.21rem;
        margin-right: 0.8rem;
        color: #666666;
    }
}

.close{
    transition: all 0.5s;
    opacity: 0;
    visibility: collapse;
}

.menu-title{
    color: #666666;
    font-weight: bold;
}
.menu-item-title{

    color: #666666;
}

</style>