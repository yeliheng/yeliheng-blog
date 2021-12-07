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
            <div class="index-button header-button">
                <span class="iconfont icon-shouye"></span>
                <span>首页</span>
            </div>
            <div class="about-button header-button">
                <span class="iconfont icon-wo"></span>
                <span>关于</span>
            </div>
            <div class="category-button header-button">
                <span class="iconfont icon-fenlei"></span>
                <span>分类</span>
            </div>

            <div class="tag-button header-button">
                <span class="iconfont icon-24gf-tags"></span>
                <span>标签</span>
            </div>

            <div class="category-button header-button">
                <span class="iconfont icon-youqinglianjie"></span>
                <span>友情链接</span>
            </div>
        </div>
    </div>

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
              <span class="iconfont icon-github"></span>
              <span class="iconfont icon-Discord"></span>
              <span class="iconfont icon-email-fill"></span>
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
    <div class="content"></div>
</div>

</template>

<script>
import '../../assets/iconfont.css';
export default {

}
</script>

<style lang="scss" scoped>

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
  }

}

// 侧边栏
.sidebar{
  animation: bottom-top-anim 1s ease 1.5s forwards;
  opacity: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 18rem;
  margin: {
    top: 0.5rem;
    left: 0.5rem
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
      display: flex;        
      flex-direction: row;
      justify-content: center;
      margin-top: 1.5rem;
      .iconfont{
        margin-left: 0.9rem;
        margin-right: 0.9rem;
        margin-bottom: 3rem;
        font-size: 2rem;
      }
    }
  }

  // 站点信息
  .site-info{
    height: 15rem;
    margin-top: 0.5rem;
    background: #121212;
    
    .announcement{
      display: block;
      margin: 1.2rem;
      text-align: center;
    }
    .site-state{
      text-align: center;
    
    }
  }
}

.header-mobile{
  opacity: 0;
  .button-container-mobile{
    display: none;
  }
}

/* 移动端 */
@media screen and (max-width: 600px){



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
}
.header-button{
  margin-left: 1.1rem;
  :hover{
    cursor: pointer;
  }
}



</style>