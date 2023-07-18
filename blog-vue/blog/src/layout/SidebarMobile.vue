<template>
  <div class="sidebar-container">
    <div class="sidebar-menu-wrap" :class="{'sidebar-open': isOpen,'sidebar-close': !isOpen}" >
      <div class="sidebar">
        <!-- 作者信息 -->
        <my-profile></my-profile>

        <div class="split-line"></div>
        <!-- 站点信息/公告 -->
        <div class="site-info" v-if="!getCurrentUri().includes('/p/')">
          <span style="font-size: 1.3rem; font-weight: bold; display: flex; justify-content: center;margin-top: 1.3rem;">站点公告</span>
          <span class="announcement">{{ siteInfo.notice.content }}</span>
          <div class="site-state">
            <div>
              <span class="iconfont icon-zhinanzhen"></span>
              <span> 本站建立于2018年11月19日</span>
            </div>
            <div>
              <span class="iconfont icon-wo"></span>
              <span> ©{{ year }} Yeliheng 版权所有</span>
            </div>
            <span>转载请注明出处!</span>
          </div>
        </div>
        <!--   目录     -->
        <!-- 文章目录 -->
        <div class="article-menu-container" v-if="getCurrentUri().includes('/p/')">
          <span style="font-size: 1.3rem; font-weight: bold; display: flex; justify-content: center;margin-top: 1.3rem;">文章目录</span>
          <div class="article-menu-mobile toc"></div>
        </div>
      </div>
    </div>
    <div class="transparent-mask" v-show="isOpen" @click="handleMaskClick()"></div>
  </div>

</template>

<script lang="ts">
import { useStore } from 'vuex';
import { ref, computed} from 'vue';
import Sidebar from "@/layout/Sidebar.vue";
import store from "@/store";
  export default {
    computed: {
      store() {
        return store
      }
    },
    components: {Sidebar},
    setup() {
      const store = useStore();
      const isOpen = computed(() => 
        store.state.showSidebar
      );
      
      const siteInfo = computed(() => store.state.siteInfo);

      const date = new Date();
      const year = ref();
      const handleClick = () => {
        year.value = date.getFullYear();
        store.dispatch('closeSidebar');
      }

      const getCurrentUri = () => {
        // 获取当前所在页面的uri
        return window.location.pathname;
      }

      const handleMaskClick = () => {
        store.dispatch('closeSidebar');
      }

      return {
        isOpen,
        handleClick,
        siteInfo,
        year,
        getCurrentUri,
        handleMaskClick,
      }
    }
  }
</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;
a {
  text-decoration: none;
  @include font_color("secondaryTextColor");
}
.sidebar-open {
  transform: translateX(0%);
}
.sidebar-close {
  transform: translateX(-100%);
}
.sidebar-menu-wrap {
  z-index: 999;
  display: flex;
  position: fixed;
  transition: all cubic-bezier(0.39, 0.575, 0.565, 1) 0.5s;
  height: 100vh;
  width: 280px;
  @include background_color("contentBackgroudColor");
  overflow: auto;
}
.transparent-mask {
  // 从透明度0渐显到0.9
  animation: mask-anim ease 0.5s forwards;
  width: 100%;
  height: 100vh;
  opacity: 0;
  position: fixed;
  right: 0;
  @include background_color("contentBackgroudColor");
  z-index: 2;
}
@keyframes mask-anim {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 0.9;
  }
}
.split-line {
  margin-left: 5rem;
  margin-right: 5rem;
  height: 0.2rem;
  opacity: 0.5;
  @include background_color("decorateColor");
}
// 侧边栏
.sidebar {
  @include background_color("contentBackgroudColor");
  transition: all 0.5s;
  top: 0.5rem;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  // 站点信息
  .site-info {
    top: 0.5rem;
    height: 15rem;
    margin-top: 0.5rem;
    @include background_color("contentBackgroudColor");
    .announcement{
      display: block;
      margin: 1.2rem;
      text-align: center;
      @include font_color("secondaryTextColor");
    }
    .site-state{
      text-align: center;
      @include font_color("secondaryTextColor");
    }
  }

}
.article-menu-mobile {
  margin-left: 1rem;
}

.sidebar-menu {
display: flex;
justify-content: center;
a{
  margin-left: 1rem;
  margin-top: 1rem;
  @include font_color("primaryTextColor");
  text-decoration: none;
}
}
</style>