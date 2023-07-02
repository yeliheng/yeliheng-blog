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

<script lang="ts">
import './index.scss';
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    // 设置主题
    window.document.documentElement.setAttribute('data-theme', store.getters.isDarkMode ? 'dark' : 'light');
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