<template>
  <div class="back-to-top">
    <span class="iconfont icon-fanhuidingbu1" @click="backToTop()" ></span>
  </div>

</template>

<script>
export default {
  setup() {
    // 滚动条位于顶部的时候不显示回到顶部按钮
    window.onscroll = function () {
      const scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      const backToTop = document.querySelector(".back-to-top");
      if (scrollTop > 200) {
        backToTop.style.opacity = "1";
      } else {
        backToTop.style.opacity = "0";
      }
    };
    const backToTop = () => {
      window.scrollTo(0,0);
    };
    return {
      backToTop,
    };
  },
};
</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;
.back-to-top {
  position: fixed;
  right: 2.5rem;
  bottom: 3.5rem;
  width: 2.5rem;
  height: 2.5rem;
  @include font_color("secondaryTextColor");
  cursor: pointer;
  transition: all 0.5s;
  opacity: 1;
  border-radius: 50%;
  .icon-fanhuidingbu1 {
    font-size: 3rem;
  }
}
/* 移动端 */
@media screen and (max-width: 900px){
  .back-to-top {
    opacity: 0;
    visibility: collapse;
  }
}
</style>