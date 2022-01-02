<template>
  <!-- 作者信息 -->
        <div class="my-info">
            <div class="avatar"></div>
            <span class="name">Yeliheng</span>
            <div class="art-info">
              <div class="article-count count">
                <span>文章</span>
                <span>{{siteInfo.articlesCount}}</span>
              </div>
              <div class="line"></div>
              <router-link class="category-count count" to="/categories">
                <span>分类</span>
                <span>{{siteInfo.categoriesCount}}</span>
              </router-link>
              <div class="line"></div>
              <router-link to="/tags" class="tag-count count">
                <span>标签</span>
                <span>{{siteInfo.tagsCount}}</span>
              </router-link>
            </div>
            <div class="contract-container">
              <a class="iconfont icon-github" href="https://github.com/yeliheng" target="_blank"></a>
              <a class="iconfont icon-shouye" href="/" ></a>
              <a class="iconfont icon-email-fill" href="mailto://yeliheng00@gmail.com" target="_blank"></a>
            </div>
            
        </div>
</template>

<script>
import { getSiteInfo } from '@/api';
import { ref } from 'vue';
import { useStore } from 'vuex';
export default {
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
    return {
      siteInfo,
    }
  }

}
</script>

<style lang="scss" scoped>
  a {
    text-decoration: none;
    color: #9e9e9e;
  }
  // 我的信息
  .my-info{
    background: #121212;
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
    .contract-container{
      color: #9e9e9e;
      display: flex;        
      flex-direction: row;
      justify-content: center;
      margin-top: 1.5rem;
      .iconfont{
        margin-left: 0.9rem;
        margin-right: 0.9rem;
        margin-bottom: 3rem;
        font-size: 2rem;
        color: #9e9e9e;
        text-decoration: none;
      }
    }
  }
</style>