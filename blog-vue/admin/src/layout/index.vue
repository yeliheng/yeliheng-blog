<template>
  <el-container class="page-container">
      <el-aside style="background: #fff;" width="auto">
       <!-- Aside content -->
          <SideBar />
      </el-aside>
      <el-container class="main-container">
          <!-- 导航栏 -->
            <el-header class="nav-header">
              <!-- Header content -->
              <Header />
            </el-header>
            <tabs></tabs>
            <el-main @click="closeSidebar" style="margin-top: 1rem;">
            <!-- Main content -->
              <router-view v-slot="{ Component }">
                <transition name="fade-transform" mode="out-in">
                  <keep-alive>
                    <component :is="Component" />
                  </keep-alive>
                </transition>
              </router-view>
            </el-main>
            
      </el-container>
      
  </el-container>
</template>

<script>
import SideBar from './components/SideBar';
import Header from './components/Header';
import { useStore } from 'vuex';
import {computed} from 'vue';
import Tabs from "@/layout/components/Tabs";
export default {
    setup(){
    const store = useStore();
    const closeSidebar = (() => {
        if(isMobile.value && !sidebarClosed.value){
            store.dispatch('closeSidebar');
        }
    });

    const isMobile = computed(() => 
        store.state.app.isMobile
    );
 
    const sidebarClosed = computed(
        () => store.state.app.sidebarClosed
    );

    const tabsList = computed(() =>
        store.state.tabs.tabsList.map((item) => item.name)
    );

    return{
      closeSidebar,
      tabsList,
    }

    },
    components: {
      Tabs,
        SideBar,
        Header,
    }
}
</script>

<style lang="scss" scoped>
.page-container{
    height: 100vh;
    .nav-header{
      height: 3.43rem;
      background: #fff;
    }
}

/* fade-transform */
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all .5s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

</style>