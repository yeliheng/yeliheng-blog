<template>
  <el-container class="page-container">
      <el-aside style="background: #fff;" width="auto">
       <!-- Aside content -->
          <SideBar />
      </el-aside>
      <el-container :class="main-container">
          <!-- 导航栏 -->
            <el-header class="nav-header">
              <!-- Header content -->
              <Header />
            </el-header>
            <el-main>
                <!-- Main content -->
                <Home />
            </el-main>
            
      </el-container>
      
  </el-container>
</template>

<script>
import SideBar from './components/SideBar';
import Header from './components/Header';
import Home from '../views/Home';
export default {
    components: {
        SideBar,
        Header,
        Home,
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