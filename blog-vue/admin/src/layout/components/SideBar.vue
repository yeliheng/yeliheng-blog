<template>
        <el-menu
            class="side-bar-menu"
            :class="{mobile: isMobile,close: sidebarClosed,}"
            :collapse="collapse"
            router
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
        >
            <div class="logo-container">
                <span>YNetwork-后台管理</span>
            </div>
            <side-bar-item
                v-for="(route, index) in sidebarMenu"
                :key="route.path + index"
                :route="route"/>
      </el-menu>

</template>

<script lang="ts">
import { defineComponent,computed,watch,onMounted,ref } from 'vue'
import { useStore } from 'vuex';
import SideBarItem from '@/layout/components/SideBarItem.vue';

export default defineComponent({
  components: {SideBarItem},
  setup() {

    const store = useStore();

    const menuRoutes = store.state.permission.menuRoutes;


    //剔除子菜单中隐藏的菜单
    const buildChildMenu = (routes: any) => {
      const routeArr = JSON.parse(JSON.stringify(routes));
      for(let i in routeArr) {
         let children = [];
         if(routeArr[i].children) {
            for (let j in routeArr[i].children) {
               if(routeArr[i].children[j].children) {
                  routeArr[i].children = buildChildMenu(routeArr[i].children);
               }
               if(routeArr[i].children[j].hidden == 0) {
                  children.push(routeArr[i].children[j]);
               }
            }
            if(children.length == 0) routeArr[i].children = null;
            else routeArr[i].children = children;
         }
      }
      console.log(routeArr);
      return routeArr;
    }


    const sidebarMenu = buildChildMenu(menuRoutes);


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
      collapse,
      isMobile,
      sidebarClosed,
      handleMenuClick,
      menuRoutes,
      sidebarMenu,
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
        color: #fff;
        opacity: 0;
        height: 6.86rem;
        white-space: nowrap;
    }
.side-bar-menu{
    border: 0px;
    i{
        margin-bottom: 0.21rem;
        margin-right: 0.8rem;
       // color: #666666;
    }
    height: 100%;
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
    //color: #666666;
    font-weight: bold;
}
.menu-item-title{

    //color: #666666;
}

</style>