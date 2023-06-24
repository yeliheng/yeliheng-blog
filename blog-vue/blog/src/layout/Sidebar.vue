<template>
  <div class="sidebar">
    <!-- 作者信息 -->
    <my-profile></my-profile>
    <!-- 站点信息/公告 -->
    <div class="site-info">
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
  </div>
</template>

<style lang="scss" scoped>
@keyframes bottom-top-anim {
  0% {
    opacity: 0;
    margin-top: 10rem;
  }
  100% {
    opacity: 1;
    margin-top: 0.5rem;
  }
}
// 侧边栏
.sidebar {
  z-index: 100;
  position: sticky;
  top: 0.5rem;
  animation: bottom-top-anim 1s ease 0.5s forwards;
  opacity: 0;
  display: flex;
  flex-direction: column;
  width: 22rem;
  height: 100%;
  margin: {
    top: 0.5rem;
    left: 0.5rem;
    right: 0.5rem;
  };

  :deep(.spinner) {
    &::after{
      background-color: #d4d3d3;
    }
  }
  // 站点信息
  .site-info {
    top: 0.5rem;
    height: 15rem;
    margin-top: 0.5rem;
    background: #121212;
    .announcement{
      display: block;
      margin: 1.2rem;
      text-align: center;
      color: #9e9e9e;
    }
    .site-state{
      text-align: center;
      color: #9e9e9e;
    }
  }

}
</style>

<script lang="ts">
import { ref,onMounted, computed } from 'vue';
import { useStore } from 'vuex';
import '../assets/iconfont.css';
import MyProfile from '@/components/MyProfile.vue';
import SidebarMobile from '@/layout/SidebarMobile.vue';
import SiteFooter from '@/components/SiteFooter.vue';
export default {
  components: { MyProfile },
  setup() {
    const store = useStore();
    const date = new Date();
    const year = ref();
    year.value = date.getFullYear();
    const siteInfo = computed(
        () => store.state.siteInfo
    );

    return {
      siteInfo,
      year
    };
  }
}
</script>
