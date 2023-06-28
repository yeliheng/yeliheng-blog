<template>
  <!-- 作者信息 -->
        <div class="my-info">
            <span class="iconfont icon-rijianmoshi theme-button" @click="toggleTheme()" v-show="isDarkMode"></span>
            <span class="iconfont icon-yejianmoshi theme-button" @click="toggleTheme()" v-show="!isDarkMode"></span>
            <router-link class="avatar" to="/about" @click="store.dispatch('closeSidebar');"></router-link>
            <span class="name">Liam Ye</span>
            <div class="art-info">
              <router-link class="article-count count" to="/" @click="handleClick()">
                <span>文章</span>
                <span>{{siteInfo.articlesCount}}</span>
              </router-link>
              <div class="line"></div>
              <router-link class="category-count count" to="/categories" @click="handleClick()">
                <span>分类</span>
                <span>{{siteInfo.categoriesCount}}</span>
              </router-link>
              <div class="line"></div>
              <router-link to="/tags" class="tag-count count" @click="handleClick()">
                <span>标签</span>
                <span>{{siteInfo.tagsCount}}</span>
              </router-link>
            </div>
            <div class="contract-container">
              <a class="iconfont icon-github" href="https://github.com/yeliheng" target="_blank"></a>
              <a class="iconfont icon-shouye" href="/"></a>
              <a class="iconfont icon-email-fill" href="mailto:yeliheng00@gmail.com" target="_blank"></a>
            </div>
            
        </div>
</template>

<script>
import { getSiteInfo } from '@/api';
import {computed, ref} from 'vue';
import { useStore } from 'vuex';
import store from "@/store";
export default {
  computed: {
    store() {
      return store
    }
  },
  setup() {
    const store = useStore();
    const siteInfo = ref({
      'articleCount': '∞',
      'categoriesCount': '∞',
      'tagsCount': '∞',
    });
    getSiteInfo().then((res) => {
      siteInfo.value = res.data;
      store.commit('SET_INFO',res.data);
    });
    const handleClick = () => {
      store.dispatch('closeSidebar');
    };
    const toggleTheme = () => {
      store.dispatch('toggleDarkMode');
    };

    const isDarkMode = computed(() => store.state.isDarkMode);

    return {
      siteInfo,
      handleClick,
      toggleTheme,
      isDarkMode
    }
  }

}
</script>

<style lang="scss" scoped>
@use '@/theme/_handle.scss' as *;
a {
  text-decoration: none;
  @include font_color("secondaryTextColor");
}
// 我的信息
.my-info{
  @include background_color("contentBackgroudColor");
  display: flex;
  flex-direction: column;
  align-items: center;
  .avatar{
    margin-top: 1rem;
    width: 10rem;
    height: 10rem;
    border-radius: 50%;
    background-image: url("https://cdn.yeliheng.com/16287964.jpeg");
    background-repeat: round;
    &:hover{
      transform: rotate(1turn);
      transition-duration: 0.5s;
      transition-timing-function: linear;
    }

  }
  .name{
    margin-top: 1rem;
    font-size: 1.5rem;
    font-weight: bold;
  }
  .art-info{
    display: flex;
    font-size: 1.2rem;
    @include font_color("secondaryTextColor");
    .line{
      @include background_color("secondaryTextColor");
      margin-top: 2rem;
      height: 3.5rem;
      width: 0.05rem;
      opacity: 0.3;
    }
    .count{
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin: {
        left: 1rem;
        right: 1rem;
        top: 1.5rem;
      };
    }
  }
  .contract-container{
    @include font_color("secondaryTextColor");
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-top: 1.5rem;
    .iconfont{
      margin-left: 0.9rem;
      margin-right: 0.9rem;
      margin-bottom: 3rem;
      font-size: 2rem;
      @include font_color("secondaryTextColor");
      text-decoration: none;
    }
  }

  .theme-button {
    @include font_color("splitLineColor");
    font-size: 2rem;
    position: absolute;
    top: 1.5rem;
    right: 1.5rem;
    &:hover {
      cursor: pointer;
    }
  }
}
</style>