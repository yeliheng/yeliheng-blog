<template>
<div class="home-container">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <!-- 头部(移动端) -->
    <div class="header-mobile">
      <div class="button-container-mobile">
          <span class="iconfont icon-mulu" style="font-size: 1.4rem;"></span>
      </div>
    </div>
  <!-- 头部(PC) -->
    <div class="header">
        <!-- TODO: 后台获取站点信息 -->
        <iframe src="fontmesh/index.html"></iframe>
        
         <div class="button-container">
            <a class="index-button header-button" href="/">
                <span class="iconfont icon-shouye"></span>
                <span>首页</span>
            </a>
            <a class="about-button header-button" href="#">
                <span class="iconfont icon-wo"></span>
                <span>关于</span>
            </a>
            <a class="category-button header-button" href="#">
                <span class="iconfont icon-fenlei"></span>
                <span>分类</span>
            </a>

            <a class="tag-button header-button">
                <span class="iconfont icon-24gf-tags" href="#"></span>
                <span>标签</span>
            </a>

            <a class="category-button header-button" href="#">
                <span class="iconfont icon-youqinglianjie"></span>
                <span>友情链接</span>
            </a>
        </div>
    </div>
  <div class="body-container">
    
<!-- 侧边栏(PC) -->
    <div class="sidebar">
      <!-- 作者信息 -->
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
            <div class="contract-container">
              <a class="iconfont icon-github" href="https://github.com/yeliheng" target="_blank"></a>
              <a class="iconfont icon-Discord" href="#" target="_blank"></a>
              <a class="iconfont icon-email-fill" href="mailto://yeliheng00@gmail.com" target="_blank"></a>
            </div>
            
        </div>
        <!-- 站点信息/公告 -->
        <div class="site-info">
            <span style="font-size: 1.3rem; font-weight: bold; display: flex; justify-content: center;margin-top: 1.3rem;">站点公告</span>
            <span class="announcement">这是Yeliheng的个人博客,我会在这里分享我计算机学习生涯中的笔记、总结、技术干货...</span>
            <div class="site-state">
              <div>
                <span class="iconfont icon-zhinanzhen"></span>
                <span> 本站建立于2018年11月19日</span>
              </div>
              <div>
                <span class="iconfont icon-wo"></span> 
                <span> ©2022 Yeliheng 版权所有</span>
              </div>
                
                <span>转载请注明出处!</span>
            </div>

        </div>
    </div>
    
    <!-- 内容区 -->
    <div class="content" 
        ref="articleWrap"> 

      <div class="article-container"
        v-for="article in articleList"
        :key="article.id"
      >
        <div class="article-title" @click="readArticle(article.id)">{{ article.title }}</div>
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
        </div>
        <div class="summary">
            <span v-if="article.summary == null"> 暂无简介 </span>
           {{article.summary}}
          </div>
        <div class="read-btn-container">
          <span class="read-btn" @click="readArticle(article.id)">阅读全文</span>
        </div>
        <div class="split-line"></div>
      </div>
      <div class="loading-bar" ref="loadingBar">
        <v-loading></v-loading>
      </div>
          
      <div class="pagination">
        <v-pagination
          v-model="page"
          :pages="pageCount"
          :range-size="1"
          active-color="#d0d0d0"
          @update:modelValue="loadArticle"
        />
      </div>

    </div>
    
  </div>
</div>

</template>

<script lang="ts">
import '../../assets/iconfont.css';
import { getArticleList } from '../../api/index';
import {useRouter} from 'vue-router';
import { ref, onMounted } from 'vue';
export default {
  setup(){
    const router = useRouter();
    const pageCount = ref();
    const page = ref(1);
    const pageSize = 5;
    const articleList = ref([]);
    const loadingBar = ref(null);
    onMounted(() => {
      loadingBar.value
    });
    //TODO: 内容加载动画
    
    getArticleList({"page": page.value,"pageSize": pageSize}).then((res: any) => {
      pageCount.value = res.data.pages;
      articleList.value = res.data.list;
    });

    const loadArticle = () => {
          loadingBar.value.style.opacity = "1";
          getArticleList({"page": page.value,"pageSize": pageSize}).then((res: any) => {
            articleList.value = res.data.list;
            loadingBar.value.style.opacity = "0";
            scrollTo(0,0);
      });
    }

    const readArticle = (id) =>{ 
      router.push({
        path: '/articles/' + id, 
      });
    }
  

    return {
      loadArticle,
      articleList,
      pageCount,
      page,
      loadingBar,
      readArticle
    }
  }

}
</script>

<style lang="scss" scoped>
.loading-bar{
  transition: all 0.5s;
  opacity: 0;
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}
@keyframes button-anim
{
    0%   {margin-top: 2rem;opacity: 0;}
    100% {margin-top: 7rem;opacity: 1;}
}
@keyframes change-opcity{
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
}
@keyframes bottom-top-anim{
    0% {
      opacity: 0;
      margin-top: 10rem;
    }
    100% {
      opacity: 1;
      margin-top: 0.5rem;
    }
}
:deep(.spinner){
    &::after{
      background-color: #d4d3d3;
    }
}
iframe{
  border: 0;
  height: 20rem;
  width: 100%;
}
.header{
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 20rem;
  .title{
    font-size: 1.8rem;
    font-weight: bold;
    position: absolute;
    left: 4rem;
  }
  
  .button-container{
    color: #fff;
    animation: button-anim 1.5s ease 1.5s forwards;
    position: absolute;
    justify-content: center;
    margin-top: 7rem;
    display: flex;
    font-size: 1.2rem;
    opacity: 0;
    .iconfont{
      margin-right: 0.4rem;
      font-size: 1.3rem;
    }
    a{
      color: #fff;
      text-decoration: none;
    }
  }
}


// 侧边栏
.sidebar{
  animation: bottom-top-anim 1s ease 0.5s forwards;
  opacity: 0;
  display: flex;
  flex-direction: column;
  width: 22rem;
  margin: {
    top: 0.5rem;
    left: 0.5rem;
    right: 0.5rem;
  };
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
      background-image: url("../../assets/images/avater.jpg");
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
  :deep(.spinner){
    &::after{
      background-color: #d4d3d3;
    }
}
  // 站点信息
  .site-info{
    position: sticky;
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
.content{
  transition: all 0.5s;
  opacity: 0;
  animation: bottom-top-anim 0.8s ease 0.5s forwards;
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
      font-size: 1.5rem;
      color: #d0d0d0;
      &:hover{
        cursor: pointer;
        &::after{
          transform: scaleX(1);
        }
      }
      &::after{
        content: "";
        height: 1px;
        width: 100%;
        background-color: #fff;
        display: flex;
        visibility: visible;
        transform: scaleX(0);
        transition-duration: 0.2s;
        transition-timing-function: ease-in-out;
        transition-delay: 0s;


}

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
    .summary{
      text-align: start;
      margin: {
        left: 2rem;
        top: 2rem;
      }
      min-height: 4rem;
    }
    .read-btn-container{     
      margin: auto;
      width: 8rem;
      height: 2.2rem;
      border: 0.13rem solid #D0D0D0;
      :hover{
        cursor: pointer;
        background: #d0d0d0;
        color: #121212
      }
      .read-btn{
        transition: all 0.5s;
        display: flex;
        width:100%;
        height:100%;
        justify-content: center;
        align-items:center;
      }
    }
    .split-line{
      margin: {
        top: 3rem;
        bottom: 3rem;
        left: auto;
        right: auto;
      }
      height: 1px;
      width: 5rem;
      background: #6f6f6f; 
    }
  }

}
.info-text{
  display: inline;
}
.pagination{
  display: flex;
  justify-content: center;
  margin-bottom: 2rem !important;
}

:deep(.Page){
  color: #d0d0d0;
  }
  :deep(.Page-active){
    color: #121212;
  }
  :deep(.Control){
    fill: #353535;
  }
  :deep(.Control-active){
    fill: #9e9e9e;
  } 



/* 移动端 */
@media screen and (max-width: 900px){
  .button-container{
    visibility: collapse;
  }

  .header-mobile{  
    height: 3rem;
    width: 100%;
    position: absolute;
    animation: change-opcity 1.5s ease forwards;
    .header-bg{
      opacity: 0;
    }
    .button-container-mobile{
      display: inline;
      position: absolute;
      top: 1.1rem;
      left: 1.1rem;
    }
  }

  .sidebar{
    display: none;
  }
  .content{
    width: 100%;
    margin: 0;
    margin-top: 0.5rem;
  }
  .info-text{
    display: none;
  }
}
.header-button{
  margin-left: 1.1rem;
}
</style>