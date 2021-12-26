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
            <router-link class="index-button header-button" to="/">
                <span class="iconfont icon-shouye"></span>
                <span>首页</span>
            </router-link>
            <router-link class="about-button header-button" to="#">
                <span class="iconfont icon-wo"></span>
                <span>关于</span>
            </router-link>
            <router-link class="category-button header-button" to="/categories">
                <span class="iconfont icon-fenlei"></span>
                <span>分类</span>
            </router-link>
            <router-link class="tag-button header-button" to="/tags">
                <span class="iconfont icon-24gf-tags"></span>
                <span>标签</span>
            </router-link>

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
        <my-profile></my-profile>
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
    <div class="content"> 
       <router-view v-slot="{ Component }">
            <transition name="slide-fade">
                <keep-alive>
                    <component :is="Component" />
                </keep-alive>
            </transition>
        </router-view>
    </div>
  </div>
</div>

</template>

<script lang="ts">
import '../assets/iconfont.css';
import {useRouter} from 'vue-router';
import { ref, onMounted } from 'vue';
import MyProfile from '@/components/MyProfile.vue';
export default {
  components: { MyProfile },
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

.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
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