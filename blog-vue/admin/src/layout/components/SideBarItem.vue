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
        <el-menu-item v-else-if="!item.children && !item.hidden" :index="resolvePath(item.external, item.path)" :key="item.id">
          <a v-if="item.external === '1'" :href="item.path" target="_blank" class="external-href">
            <i :class="item.icon" />
            <span class="menu-title"> {{ item.name }}</span>
          </a>
          <div v-else>
            <i :class="item.icon" />
            <span class="menu-title"> {{ item.name }}</span>
          </div>
        </el-menu-item>
      </template>
    </el-sub-menu>

  </template>
  <!-- 顶级菜单 -->
  <template v-else-if="!route.hidden" >
    <el-menu-item :index="resolvePath(route.external, route.path)" :key="route.path + route.id" style="background-color: #324157">
      <a v-if="route.external === '1'" :href="route.path" target="_blank" class="external-href" width="100%">
        <i :class="route.icon" />
        <span class="menu-title"> {{ route.name }}</span>
      </a>
      <div v-else>
        <i :class="route.icon" />
        <span class="menu-title"> {{ route.name }}</span>
      </div>
    </el-menu-item>
  </template>
</template>

<script lang="ts">

import * as path from "path";

export default {
  name: "SideBarItem",
  props: {
    route: {
      required: true,
    },
  },
  setup() {

    const resolvePath = (external: string,routePath: string) => {
      if(external === '1') {
        return "#" + routePath;
      } else {
        return path.resolve(routePath)
      }
    }

    return {
      resolvePath,
    }
  }
}
</script>

<style lang="scss" scoped>
i{
  margin-bottom: 0.21rem;
  margin-right: 0.8rem;
}

.menu-title{
  font-weight: bold;
}

.external-href {
  text-decoration: none;
  color: inherit;
  display: block;
}

</style>