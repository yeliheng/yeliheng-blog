<template>
  <div>
    <div class="container-404">
      <div class="not-found"></div>
    </div>
    <span class="title-404">页面找不到了</span>
    <div class="index-button">
      <router-link to="/dashboard">
        <el-button style="background: #a1b9ff;color: #fff;"> 返回首页 </el-button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang=scss scoped>
.container-404{
    display: flex;
    justify-content: center;
    align-content: center;
        .not-found{
            margin-top: 6rem;
            width: 50vw;
            height: 50vh;
            background-image: url("../../assets/images/404.svg");
            background-repeat: no-repeat;
            background-position: center;
        }
}
.title-404{
    display: flex;
    justify-content: center;
    font-size: 5vw;
    color: #a1b9ff;
}
.index-button{
    display: flex;
    justify-content: center;
    margin-top: 2rem;    
}
</style>