<template>
  <div>
  <router-link to="/"></router-link>
  <router-view #default="{Component}">
    <keep-alive>
      <component :is="Component" v-if="$route.meta.keepAlive" />
    </keep-alive>
    <component :is="Component" v-if="!$route.meta.keepAlive" />
  </router-view>
  
  </div>
</template>

<script>
import './index.scss';
export default {

}
</script>
<style scoped>

</style>