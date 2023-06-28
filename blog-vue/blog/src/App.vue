<template>
  <div>
  <router-link to="/"></router-link>
  <router-view #default="{Component}">
    <keep-alive>
      <component :is="Component" v-if="$route.meta.keepAlive" />
    </keep-alive>
    <component :is="Component" v-if="!$route.meta.keepAlive" />
  </router-view>
  
  </div>
</template>

<script>
import './index.scss';
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    // 默认为暗色主题
    window.document.documentElement.setAttribute('data-theme', store.state.isDarkMode ? 'dark' : 'light');
    // 监听store中isDarkMode的变化状态
    store.watch(
      (state) => state.isDarkMode,
      (newVal) => {
        window.document.documentElement.setAttribute('data-theme', newVal ? 'dark' : 'light');
      }
    );

    return {}
  }
}
</script>