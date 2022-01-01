<template>
    <div class="sidebar-menu-wrap" :class="{'sidebar-open': isOpen,'sidebar-close': !isOpen}">
      <div class="my-info">
            <div class="avatar"></div>
            <span class="name">Yeliheng</span>
            <div class="art-info">
              <div class="article-count count">
                <span>文章</span>
                <span>12</span>
              </div>
              <div class="line"></div>
              <div class="category-count count">
                <span>分类</span>
                <span>5</span>
              </div>
              <div class="line"></div>
              <div class="tag-count count">
                <span>标签</span>
                <span>20</span>
              </div>
            </div>
      </div>
      <div class="sidebar-menu">
          <router-link class="index-button header-button" to="/" @click="handleClick">
              <span class="iconfont icon-shouye"></span>
              <span>首页</span>
          </router-link>
          <router-link class="about-button header-button" to="/about" @click="handleClick">
              <span class="iconfont icon-wo"></span>
              <span>关于</span>
          </router-link>
          <router-link class="category-button header-button" to="/categories" @click="handleClick">
              <span class="iconfont icon-fenlei"></span>
              <span>分类</span>
          </router-link>
          <router-link class="tag-button header-button" to="/tags" @click="handleClick">
              <span class="iconfont icon-24gf-tags"></span>
              <span>标签</span>
          </router-link>

      </div>
          
      
    </div>
</template>

<script lang="ts">
  import { useStore } from 'vuex';
  import { ref, computed} from 'vue';
  export default {
    setup() {
      const store = useStore();
      const isOpen = computed(() => 
        store.state.showSidebar
      );
      const handleClick = () => {
          store.dispatch('closeSidebar');
      }
      return {
        isOpen,
        handleClick,
      }
    }
  }
</script>

<style lang="scss" scoped>
.sidebar-open {
  transform: translateY(0%);
}
.sidebar-close {
  transform: translateY(-100%);
}
.sidebar-menu-wrap {
  position: absolute;
  transition: all cubic-bezier(0.39, 0.575, 0.565, 1) 0.5s;
  background: #161616;
  width: 100%;
  height: 20rem;
  opacity: 1;
}
// 我的信息
  .my-info{
    display: flex;
    flex-direction: column;
    align-items: center;
    .avatar{
      margin-top: 1rem;
      width: 7rem;
      height: 7rem;
      border-radius: 50%;
      background-image: url("../assets/images/avater.jpg");
      background-repeat: round;
    }
    .name{
      margin-top: 1rem;
      font-size: 1.5rem;
      font-weight: bold;
    }
    .art-info{
      display: flex;
      font-size: 1.2rem;
      color: #9e9e9e;
      .line{
        background: #353535;
        margin-top: 2rem;
        height: 3.5rem;
        width: 0.05rem;
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
  }

.sidebar-menu {
  display: flex;
  justify-content: center;
  a{
    margin-left: 1rem;
    margin-top: 1rem;
    color: #d0d0d0;
    text-decoration: none;
  }
}
</style>