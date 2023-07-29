<template>
  <div class="button-container">
    <span class="iconfont icon-dianzan"></span>
    <span class="iconfont icon-pinglun1" style="margin-top: 2rem;"></span>
    <span class="iconfont icon-fenxiang" style="margin-top: 2rem; font-size: 2.6rem;"></span>
  </div>
</template>

<script>
export default {
  setup() {

  }
}


</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;
.button-container {
  transition: all 0.5s;
  position: sticky;
  height: 16rem;
  width: 5.5rem;
  top: 60vh;
  //margin-left: 0.5rem;
  border-radius: 0.5rem;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  @include background_color("contentBackgroudColor");
  .iconfont {
    font-size: 3rem;
    @include font_color("secondaryTextColor");
  }
}
/* 移动端 */
@media screen and (max-width: 900px){
  .button-container {
    display: none;
  }
}
</style>