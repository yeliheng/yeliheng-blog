<template>
<div class="home-container">
  <sidebar-mobile class="sidebar-mobile" ></sidebar-mobile>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <div class="header-mobile">
    <div class="button-container-mobile" @click="handleOpen()">
        <span class="iconfont icon-mulu"></span>
    </div>
    <router-link class="site-info" to="/" @click="onSiteClick()">Yeliheng的技术小站</router-link>
  </div>

  <div class="body-container">

    <!-- 侧边栏(PC) -->
    <div class="sidebar" >
        <!-- 作者信息 -->
        <my-profile></my-profile>
        <!-- 文章目录 -->
        <div class="article-menu-container" ref="articleMenu">
          <span style="font-size: 1.3rem; font-weight: bold; display: flex; justify-content: center;margin-top: 1.3rem;">文章目录</span>
          <div class="article-menu toc"></div>
        </div>
    </div>

    <!-- 内容区 -->
    <div class="content" ref="articleWrap" >
      <div class="split-line"></div>
      <div class="article-container">
        <div class="article-title">{{article.title}}</div>
        <div class="article-info">
          <div class="pub-time info"><span class="iconfont icon-rili" style="margin-right: 0.5rem;"></span>
          <span class="info-text">发表于 </span>
          {{article.createdAt}}
          </div>
          <div class="words info">
            <span class="iconfont icon-zishu" style="margin-right: 0.5rem;"></span>
            <span class="info-text">字数统计: </span>
            {{article.words}}
            </div>
          <div class="read-time info"><span class="iconfont icon-shizhong" style="margin-right: 0.5rem;"></span>
          <span class="info-text">阅读时长(分钟) ≈  </span>{{article.readingTime}}</div>
          <div class="views info"><span class="iconfont icon-liulancishu" style="margin-right: 0.5rem;"></span>
          <span class="info-text">浏览次数: </span>{{article.views}}</div>    
        </div>
        <markdown 
        class="markdown-body"
        :source="article.content"
        />
        
      </div>
      <site-footer></site-footer>
    </div> 
    
  </div>
  
</div>

</template>

<script lang="ts">
import '@/assets/iconfont.css';
import { getArticleByUrl} from '@/api';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import tocbot from 'tocbot';
import SidebarMobile from '@/layout/SidebarMobile.vue';
import { useStore } from 'vuex';
import SiteFooter from '@/components/SiteFooter.vue';
import {computed} from "vue";
import store from "@/store";
export default {
  components: { SidebarMobile, SiteFooter },
  computed: {
    store() {
      return store
    }
  },
  setup(){
    const router = useRouter();
    const store = useStore();
    store.dispatch("isLoading");
    const article = ref({
      'title': '',
      'content': '',
      'word': 0,
      'readingTime': 0,
      'createdAt': ''
    });
    const articleWrap = ref();
    const articleMenu = ref();

    const handleOpen = () => {
      store.dispatch('openSidebar');
    };
    const isOpen = computed(() =>
        store.state.showSidebar
    );

    onMounted(() => {
      articleWrap;
      articleMenu;
    });

    getArticleByUrl(router.currentRoute.value.params.url).then((res: any) => {
      document.title = res.data.title + ' - Yeliheng的技术小站';
      article.value = res.data;
      articleWrap.value.style.opacity = "1";
      articleMenu.value.style.position = "sticky";
      setTimeout(() => {
        tocbot.init({
          tocSelector: '.article-menu',
          contentSelector: '.markdown-body',
          headingSelector: 'h1, h2, h3',
          hasInnerContainers: true,
          scrollSmoothDuration: 100,
          scrollSmooth: true,
          onClick(e){
            e.preventDefault();
          }
        });
        tocbot.init({
          tocSelector: '.article-menu-mobile',
          contentSelector: '.markdown-body',
          headingSelector: 'h1, h2, h3',
          hasInnerContainers: true,
          scrollSmoothDuration: 100,
          scrollSmooth: true,
          onClick(){
            store.dispatch("closeSidebar");
          }
        });
      if(articleWrap.value.scrollHeight < window.innerHeight){
        articleWrap.value.style.height = window.innerHeight + 'px';
      }
      store.dispatch("notLoading");
      });
    });
    const onSiteClick = () => {
      scrollTo(0,0);
    }

    return {
      article,
      articleWrap,
      articleMenu,
      isOpen,
      handleOpen,
      onSiteClick,
    }
  }

}
</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;
.display {
  opacity: 1;
}
.hidden {
  opacity: 0;
}
.fixed {
  position: fixed;
}
.no-fixed {
  position: relative;
}

:deep(.is-active-link){
  &::before{
    background-color: #bb46ff;
  }
}

.article-content{
  text-align: start;
}

.header-mobile{
  display: none;
}


// 侧边栏
.sidebar{
 // transition: all 1s;
  opacity: 1;
  display: flex;
  flex-direction: column;
  width: 17vw;
  margin: {
    top: 0.5rem;
    left: 0.5rem;
    right: 0.5rem;
  };
  
  // 站点信息
  .article-menu-container{
    position: initial;
    top: 0.5rem;
    padding-bottom: 2rem;
    margin-top: 0.5rem;
    @include background_color("contentBackgroudColor");
  }
  .article-menu{
    display: flex;
    justify-content: center;
  }

}

.body-container{
  display: flex;
  justify-content: center;
  height: 100%;
}

.content{
  transition: all 1s;
  opacity: 0;
  @include background_color("contentBackgroudColor");
  width: 70vw;
  margin: {
    top: 0.5rem;
    right: 0.5rem;
  };
  .article-container{
    padding: {
      top: 3rem;
      bottom: 3rem;
      left: 1.5rem;
      right: 1.5rem;
    }
    text-align: center;
    .article-title{
      font-size: 2rem;
      @include font_color("primaryTextColor");
      font-weight: bold;
    }
    .article-info{
      display: flex;
      justify-content: center;
      .info{
          @include font_color("secondaryTextColor");
          font-size: 0.8rem;
          margin-left: 1.2rem;
          margin-top: 0.2rem;
      }
    }

  }

}
.info-text{
  display: inline;
}


/* 移动端 */
@media screen and (max-width: 900px){
  .icon-mulu{
    font-size: 1.6rem;
    margin-left: 1rem;
    top: 1rem;
    @include font_color("secondaryTextColor");
    position: absolute;
  }
  .sidebar{
    display: none;
  }
  .header-mobile{
    position: fixed;
    z-index: 2;
    display: flex;
    height: 4rem;
    width: 100%;
    align-items: center;
    @include background_color("mainBackgroundColor");
  }

  .site-info {
    text-decoration: none;
    font-size: 1.5rem;
    margin-left: 4rem;
    @include font_color("secondaryTextColor");
  }

  .split-line {
    width: 100%;
    top: 4rem;
    height: 0.3rem;
    opacity: 0.6;
    @include background_color("decorateColor");
  }

  .content{
    width: 100%;
    margin: 4rem 0 0;
    .article-container{
      .article-info{
        .info{
          margin-left: 0.6rem;
        }
      }
    }
  }
  .info-text{
    display: none;
  }
}
</style>