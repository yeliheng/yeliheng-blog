<template>
<div class="home-container">
  <div class="loading-bar" ref="loadingBar" >
    <v-loading class="loading-bar"></v-loading>
  </div>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
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
        ref="articleWrap"> 

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
export default {
  setup(){
    const router = useRouter();
    const pageCount = ref();
    const page = ref(1);
    const pageSize = 5;
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
      if(articleWrap.value.scrollHeight < window.innerHeight){
        articleWrap.value.style.height = window.innerHeight + 'px';
      }
      });
    });
    

  

    return {
      pageCount,
      page,
      article,
      articleWrap,
      articleMenu,
      loadingBar
    }
  }

}
</script>

<style lang="scss" scoped>
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


// 侧边栏
.sidebar{
 // transition: all 1s;
  opacity: 1;
  display: flex;
  flex-direction: column;
  width: 22rem;
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
.header-mobile{
  opacity: 0;
  .button-container-mobile{
    display: none;
  }
}
.body-container{

  display: flex;
  justify-content: center;
  height: 100%;
}
.loading-bar{
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
 // animation: bottom-top-anim 1s ease forwards;
  background: #121212;
  width: 90rem;
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
  .content{
    width: 100%;
    margin: 0;
    margin-top: 0.5rem;
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