<template>
<div class="home-container">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <div class="header-mobile">
    <div class="header-bg">
    </div>
    <div class="button-container-mobile">
        <span class="iconfont icon-mulu" style="font-size: 1.4rem;"></span>
    </div>
    
  </div>
    <div class="header">
        <!-- TODO: 后台获取站点信息 -->
        <!-- <div class="title">Yeliheng的技术小站</div> -->
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
    <div class="sidebar">
        <div class="my-info">
            <div class="avatar"></div>
            <span>Yeliheng</span>
            <div class="art-info">
              <div class="article-count">
                <span>文章</span>
                <span>12</span>
              </div>
              <div class="category-count">
                <span>分类</span>
                <span>5</span>
              </div>
              <div class="tag-count">
                <span>标签</span>
                <span>20</span>
              </div>
            </div>
            <span class="iconfont icon-github"></span>
            <span class="iconfont icon-github"></span>
            <span class="iconfont icon-github"></span>
        </div>
        <div class="site-info"></div>
    </div>
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
 
@-webkit-keyframes button-anim /* Safari 与 Chrome */
{
    0%   {margin-top: 2rem;opacity: 0;}
    100% {margin-top: 7rem;opacity: 1;}
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
    animation: button-anim 1.5s ease 1.5s forwards;
    -webkit-animation: button-anim 1.5s ease 1.5s forwards; /* Safari 与 Chrome */
    position: absolute;
    justify-content: center;
    margin-top: 7rem;
    display: flex;
    font-size: 1.3rem;
    opacity: 0;
    .iconfont{
      margin-right: 0.4rem;
      font-size: 1.3rem;
    }
  }

}
.header-mobile{
  opacity: 0;
}
@media screen and (max-width: 600px){

  @keyframes mobile-header
  {
      0% {
        opacity: 0;
      }
      100% {
        opacity: 1;
      }
  }
  
  @-webkit-keyframes mobile-header
  {
      0% {
        background: #000;
        height: 0rem;
        opacity: 0;
      }
      100% {
        background: #fff;
        height: 3rem;
        opacity: 1;
      }
  }

  .button-container{
    visibility: collapse;
  }

  .header-mobile{
    
    height: 3rem;
    width: 100%;
    position: absolute;
    animation: mobile-header 1.5s ease forwards;
    -webkit-animation: mobile-header 1.5s ease forwards; /* Safari 与 Chrome */
    .header-bg{
      opacity: 0;
    }
    .button-container-mobile{
      margin-top: 1.1rem;
      margin-left: 1.1rem;
    }
  }
}
.header-button{
  margin-left: 1.1rem;
  :hover{
    cursor: pointer;
  }
}

</style>