<template>
<div class="home-container">
  <sidebar-mobile class="sidebar-mobile" ></sidebar-mobile>
  <div class="loading-bar" ref="loadingBar" >
    <bounce-loader class="loading-bar"
        :loading="true"
        :color="'#fff'"
    />
  </div>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <div class="header-mobile">
    <div class="button-container-mobile" @click="handleOpen()">
        <span class="iconfont icon-mulu" style="font-size: 1.6rem;margin-left: 1rem; top: 1rem;color: #9e9e9e;position: absolute"></span>
    </div>
    <router-link class="site-info" to="/">Yeliheng的技术小站</router-link>
  </div>
  <div class="split-line"></div>
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
    <div class="content" 
        ref="articleWrap" @click="handleContentClick()"> 

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
        style="text-align: start;margin-top: 3rem;background-color: #121212;" 
        :source="article.content"
        />
        
      </div>
      <site-footer></site-footer>
    </div> 
    
  </div>
  
</div>

</template>

<script lang="ts">
import '../../assets/iconfont.css';
import { getArticleByUrl} from '../../api/index';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import tocbot from 'tocbot';
import SidebarMobile from '@/layout/SidebarMobile.vue';
import { useStore } from 'vuex';
import SiteFooter from '@/components/SiteFooter.vue';
export default {
  components: { SidebarMobile, SiteFooter },
  setup(){
    const router = useRouter();
    const store = useStore();
    const pageCount = ref();
    const page = ref(1);
    const pageSize = 5;
    const isLoading = ref(true);
    const article = ref({
      'title': '',
      'content': '',
      'word': 0,
      'readingTime': 0,
      'createdAt': ''
    });
    const articleWrap = ref();
    const loadingBar = ref();
    const articleMenu = ref();
    const handleOpen = () => {
      store.dispatch('toggleSidebar');
    };

    const handleContentClick = () => {
      store.dispatch('closeSidebar');
    };
    onMounted(() => {
      articleWrap;
      loadingBar;
      articleMenu;
    });
    //TODO: 内容加载动画
    getArticleByUrl(router.currentRoute.value.params.url).then((res: any) => {
      
      document.title = res.data.title + ' - Yeliheng的技术小站';
      article.value = res.data;
      articleWrap.value.style.opacity = "1";
      loadingBar.value.style.opacity = "0";
      loadingBar.value.style.visibility = 'collapse';
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
            store.dispatch("toggleSidebar")
          }
        });
      if(articleWrap.value.scrollHeight < window.innerHeight){
        articleWrap.value.style.height = window.innerHeight + 'px';
      }
      isLoading.value = false;
      });
    });
    

  

    return {
      pageCount,
      page,
      article,
      articleWrap,
      articleMenu,
      loadingBar,
      isLoading,
      handleOpen,
      handleContentClick
    }
  }

}
</script>

<style lang="scss" scoped>
.display {
  opacity: 1;
}
.hidden {
  opacity: 0;
}
:deep(.spinner){
    &::after{
      background-color: #d4d3d3;
    }
}
:deep(.is-active-link){
  &::before{
    background-color: #bb46ff;
  }
}
:deep(.toc-link){
  color: #9e9e9e;
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
    background: #121212; 
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
.loading-bar{
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #272727;
  transition: all 0.5s;
  opacity: 1;
  position: absolute;
  top: 0;             
  bottom: 0;           
  left: 0;        
  right: 0;
  margin: auto;
  height: 100%;
}

.content{
  transition: all 1s;
  opacity: 0;
  background: #121212;
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
      color: #d0d0d0;
      font-weight: bold;
    }
    .article-info{
      display: flex;
      justify-content: center;
      .info{
          color: #9e9e9e;
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
  .sidebar{
    display: none;
  }
  .header-mobile{
    display: flex;
    height: 4rem;
    align-items: center;
    background: #161616;
  }

  .site-info {
    text-decoration: none;
    font-size: 1.5rem;
    margin-left: 4rem;
    color: #9e9e9e;
  }

  .split-line {
    height: 0.3rem;
    opacity: 0.6;
    background: #8b1bce;
  }

  .sidebar-mobile {

  }
  .content{
    width: 100%;
    margin: 0rem 0 0;
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