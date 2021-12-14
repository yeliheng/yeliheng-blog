<template>
<div class="home-container">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
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
        <!-- 文章目录 -->
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
          
        </div>
        <markdown :source="article.content" style="text-align: start;margin-top: 3rem;" class="markdown-body"/>

      </div>

    </div>
    
  </div>
            

</div>


</template>

<script lang="ts">
import '../../assets/iconfont.css';
import { getArticleById} from '../../api/index';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
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
    //TODO: 内容加载动画
    getArticleById(router.currentRoute.value.params.id).then((res: any) => {
      article.value = res.data;
    });
  

    return {
      pageCount,
      page,
      article,
    }
  }

}
</script>

<style lang="scss" scoped>
.article-content{
  text-align: start;
}
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


// 侧边栏
.sidebar{
  opacity: 1;
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
  animation: bottom-top-anim 1s ease 0.5s forwards;
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
  }
  .info-text{
    display: none;
  }
}
</style>