<template>
  <!-- 二级菜单标题 -->
  <template v-if="route.name && route.children && !route.hidden">
    <el-sub-menu :key="route.path + route.id" :index="route.id" >
      <template #title >
        <i :class="route.icon"/>
        <span class="menu-title"> {{ route.name }}</span>
      </template>
      <!-- 二级菜单选项 -->
      <template v-for="(item, index) of route.children">
        <el-menu-item v-if="!item.hidden" :key="index" :index="item.path" >
          <i :class="item.icon" />
          <span class="menu-item-title">{{ item.name }}</span>
        </el-menu-item>
      </template>
    </el-sub-menu>
  </template>
  <!-- 一级菜单 -->
  <template v-else-if="!route.hidden" >
    <el-menu-item :index="route.path" :key="route.path + route.id">
      <i :class="route.icon" />
      <span class="menu-title"> {{ route.name }}</span>
    </el-menu-item>
  </template>
</template>

<script>

export default {
  name: "SideBarItem.vue",
  props: {
    route: {
      required: true
    },
  },
  setup() {

    return {
    }
  }
}
</script>

<style lang="scss" scoped>
i{
  margin-bottom: 0.21rem;
  margin-right: 0.8rem;
// color: #666666;
}

.menu-title{
//color: #666666;
  font-weight: bold;
}
.menu-item-title{

//color: #666666;
}
</style>