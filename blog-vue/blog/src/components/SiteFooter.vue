<template>
  <div class="site-footer">
    © {{year}} Yeliheng 版权所有 | 由<a href="https://github.com/yeliheng/yeliheng-blog" target="_blank">YNetwork</a>原创框架强力驱动
    <div style="margin-top: 0.1rem;"><a href="https://beian.miit.gov.cn/">闽ICP备18009950号</a></div>
  </div>
</template>

<script>
import { ref } from 'vue';
export default {
  setup() {
    const date = new Date();
    const year = ref(2000);
    year.value = date.getFullYear();
    return {
      year,
    }
  }
}
</script>

<style lang="scss" scoped>
    .site-footer {
      text-align: center;
      background: inherit ;
      opacity: 0.5;
      width: 100%;
    }
    a {
      color: #fff;
    }
</style>