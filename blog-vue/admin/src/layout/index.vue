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
            <el-main @click="closeSidebar">
            <!-- Main content -->

                <router-view/>

            </el-main>
            
      </el-container>
      
  </el-container>
</template>

<script>
import SideBar from './components/SideBar';
import Header from './components/Header';
import { useStore } from 'vuex';
import {computed} from 'vue';
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

    return{
        closeSidebar,
    }

    },
    components: {
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
</style>