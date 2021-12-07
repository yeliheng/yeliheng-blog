<template>

<div class="home-container">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
    <div class="header">
        <!-- TODO: 后台获取站点信息 -->
        <!-- <div class="title">Yeliheng的技术小站</div> -->
        <iframe src="fontmesh/index.html"></iframe>
        <!-- <div class="button-container">
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
        </div> -->
    </div>
    <div class="sidebar">
        <div class="my-info"></div>
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
iframe{
  border: 0;
  height: 20rem;
  width: 100%;
}
.header{
  display: flex;
  align-items: center;
  width: 100%;
  height: 20rem;
  //background-image: url("../../assets/images/test.jpg");
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: 0 center;
  .title{
    font-size: 1.8rem;
    font-weight: bold;
    position: absolute;
    left: 4rem;
  }
  .button-container{
    position: absolute;
    right: 2rem;
    display: flex;
    .iconfont{
      margin-right: 0.2rem;
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