<template>
  <!-- 子菜单标题 -->
  <template v-if="route.name && route.children && !route.hidden">
    <el-sub-menu :key="route.path + route.id" :index="route.path + route.id">
      <template #title >
        <i :class="route.icon"/>
        <span class="menu-title"> {{ route.name }}</span>
      </template>

      <!-- 子菜单选项 -->
      <template v-for="(item, index) of route.children">
        <el-sub-menu v-if=" item.children && !item.hidden" :index="item.path" :key="index">
          <template #title >
            <i :class="item.icon"/>
            <span class="menu-title"> {{ item.name }}</span>
          </template>
          <side-bar-item
              v-for="(route, index) in item.children"
              :key="route.path + index"
              :route="route"/>
        </el-sub-menu>
        <el-menu-item v-else-if="!item.children && !item.hidden" :index="item.path" :key="item.id">
          <i :class="item.icon" />
          <span class="menu-title"> {{ item.name }}</span>
        </el-menu-item>
      </template>
    </el-sub-menu>

  </template>
  <!-- 顶级菜单 -->
  <template v-else-if="!route.hidden" >
    <el-menu-item :index="route.path" :key="route.path + route.id">
      <i :class="route.icon" />
      <span class="menu-title"> {{ route.name }}</span>
    </el-menu-item>
  </template>
</template>

<script>

export default {
  name: "SideBarItem",
  props: {
    route: {
      required: true,
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