<template>
<div class="home-container">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <!-- 头部(移动端) -->
    <div class="header-mobile" @touchmove.prevent>
      <div class="button-container-mobile"  @click="handleOpen()">
          <span class="iconfont icon-mulu" v-if="!isOpen"></span>
      </div>
    </div>
  <sidebar-mobile class="sidebar-mobile"></sidebar-mobile>
  <!-- 头部(PC) -->
    <div class="header">
        <!-- TODO: 后台获取站点信息 -->
        <iframe :src="src"></iframe>
         <div class="button-container">
            <router-link class="index-button header-button" to="/">
                <span class="iconfont icon-shouye"></span>
                <span>首页</span>
            </router-link>
            <router-link class="about-button header-button" to="/about">
                <span class="iconfont icon-wo"></span>
                <span>关于</span>
            </router-link>
            <router-link class="category-button header-button" to="/categories">
                <span class="iconfont icon-fenlei"></span>
                <span>分类</span>
            </router-link>
            <router-link class="tag-button header-button" to="/tags">
                <span class="iconfont icon-24gf-tags"></span>
                <span>标签</span>
            </router-link>

            <a class="category-button header-button" href="https://github.com/yeliheng" target="_blank">
                <span class="iconfont icon-github"></span>
                <span>Github</span>
            </a>
        </div>
    </div>
  <div class="body-container">
   
    <!-- 侧边栏(PC) -->
    <Sidebar></Sidebar>

    
    <!-- 内容区 -->
    <div class="content">
       <router-view v-slot="{ Component }">
            <transition name="fade-transform" mode="out-in">
                <keep-alive>
                    <component :is="Component" />
                </keep-alive>
            </transition>
        </router-view>
      <site-footer></site-footer>
    </div>
      
  </div>

</div>

</template>

<script lang="ts">
import '../assets/iconfont.css';
import MyProfile from '@/components/MyProfile.vue';
import SidebarMobile from '@/layout/SidebarMobile.vue';
import SiteFooter from '@/components/SiteFooter.vue';
import Sidebar from "@/layout/Sidebar.vue";
import {useStore} from "vuex";
import store from "@/store";
import {computed} from "vue";
export default {
  components: { MyProfile, SidebarMobile,SiteFooter, Sidebar},
  computed: {
    store() {
      return store
    }
  },
  setup() {
    const store = useStore();

    const handleOpen = () => {
      store.dispatch('toggleSidebar');

    };
    const isOpen = computed(() =>
        store.state.showSidebar
    );

    const isDarkMode = computed(
        () => store.state.isDarkMode
    );

    let src = `/fontmesh/index.html?isDarkMode=${isDarkMode.value}`;
    // 每次isDarkMode变化时刷新iframe
    store.watch(
        (state) => state.isDarkMode,
        () => {
          if(isDarkMode.value) {
            src = '/fontmesh/index.html?isDarkMode=true';
          }else {
            src = '/fontmesh/index.html?isDarkMode=false';
          }
          console.log(src);
          // 获取iframe元素
          const iframe = document.querySelector('iframe');
          iframe.contentWindow.location.reload();
        }
    );

    return {
      handleOpen,
      isOpen,
      src
    };
  }

}
</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;
.loading-bar {
  transition: all 0.5s;
  opacity: 0;
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}
@keyframes button-anim
{
    0%   {margin-top: 2rem;opacity: 0;}
    100% {margin-top: 7rem;opacity: 1;}
}
@keyframes change-opcity {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
}
@keyframes bottom-top-anim {
    0% {
      visibility: hidden;
      margin-top: 10rem;
    }
    100% {
      visibility: visible;
      margin-top: 0.5rem;
    }
}

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

.icon-mulu {
  font-size: 1.4rem;
  @include font_color("secondaryTextColor");
}

iframe {
  border: 0;
  height: 20rem;
  width: 100%;
}
.header {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 20rem;
  .title {
    font-size: 1.8rem;
    font-weight: bold;
    position: absolute;
    left: 4rem;
  }
  .button-container {
    @include font_color("highlightTextColor");
    animation: button-anim 1.5s ease 1.5s forwards;
    position: absolute;
    justify-content: center;
    margin-top: 7rem;
    display: flex;
    font-size: 1.2rem;
    opacity: 0;
    .iconfont {
      margin-right: 0.4rem;
      font-size: 1.3rem;
    }
    a {
      @include font_color("highlightTextColor");
      text-decoration: none;
    }
  }
}

.header-mobile {
  opacity: 0;
  .button-container-mobile{
    z-index: 9;
    display: none;
  }
}
.body-container{
  display: flex;
  justify-content: center;
  height: 100%;

}
.content {
  transition: all 0.5s;
  visibility: hidden;
  animation: bottom-top-anim 0.8s ease 0.5s forwards;
  @include background_color("contentBackgroudColor");
  width: 90rem;
  margin: {
    top: 0.5rem;
    right: 0.5rem;
  };
}



/* 移动端 */
@media screen and (max-width: 900px){
  .button-container{
    visibility: collapse;
  }

  .header-mobile{  
    height: 3rem;
    width: 100%;
    position: absolute;
    animation: change-opcity 1.5s ease forwards;
    .header-bg{
      opacity: 0;
    }
    .button-container-mobile{
      display: inline;
      position: absolute;
      top: 1.1rem;
      left: 1.1rem;
    }
  }

  .sidebar{
    display: none;
  }
  .content{
    width: 100%;
    margin: 0.5rem 0 0;
  }
  .info-text{
    display: none;
  }
}
.header-button{
  margin-left: 1.1rem;
}
</style>