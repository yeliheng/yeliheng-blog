<template>
  <div class="tabs" v-if="showTabs">
    <ul>
      <li class="tabs-li" v-for="(item,index) in tabsList" :class="{'active': isActive(item.path)}" :key="index">
        <router-link
            :to="item.path" class="tabs-li-title"
            @click.middle="closeTabs(index)"
        >
          {{item.title}}
        </router-link>
        <span class="tabs-li-icon" @click="closeTabs(index)">
           <i class="el-icon-close"></i>
        </span>
      </li>
    </ul>
    <div class="tabs-close-box">
      <el-dropdown @command="handleTabs">
        <el-button size="mini" type="primary">
          标签选项
          <i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu size="small">
            <el-dropdown-item command="other">关闭其他</el-dropdown-item>
            <el-dropdown-item command="all">关闭所有</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";
import { onBeforeRouteUpdate, useRoute, useRouter } from "vue-router";
export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const isActive = (path) => {
      return path === route.fullPath;
    };

    const store = useStore();
    const tabsList = computed(() => store.state.tabs.tabsList);
    const showTabs = computed(() => tabsList.value.length > 0);

    // 关闭单个标签
    const closeTabs = (index) => {
      const delItem = tabsList.value[index];
      store.commit("delTabsItem", { index });
      const item = tabsList.value[index]
          ? tabsList.value[index]
          : tabsList.value[index - 1];
      if (item) {
        delItem.path === route.fullPath && router.push(item.path);
      } else {
        router.push("/");
      }
    };

    // 设置标签
    const setTabs = (route) => {
      const isExist = tabsList.value.some((item) => {
        return item.path === route.fullPath;
      });
      if (!isExist) {
        if (tabsList.value.length >= 8) {
          store.commit("delTabsItem", { index: 0 });
        }
        store.commit("setTabsItem", {
          name: route.name,
          title: route.meta.title,
          path: route.fullPath,
        });
      }
    };
    setTabs(route);
    onBeforeRouteUpdate((to) => {
      setTabs(to);
    });

    // 关闭全部标签
    const closeAll = () => {
      store.commit("clearTabs");
      router.push("/");
    };
    // 关闭其他标签
    const closeOther = () => {
      const curItem = tabsList.value.filter((item) => {
        return item.path === route.fullPath;
      });
      store.commit("closeTabsOther", curItem);
    };
    const handleTabs = (command) => {
      command === "other" ? closeOther() : closeAll();
    };

    return {
      isActive,
      tabsList,
      showTabs,
      closeTabs,
      handleTabs,
    };
  },
};
</script>


<style lang="scss" scoped>
.tabs {
  position: relative;
  margin: {
    top: 0.2rem;
  };
  background: #fff;
  padding-right: 120px;
  box-shadow: 0 5px 10px #ddd;
}

.tabs ul {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
}

.tabs-li {
  float: left;
  margin: 3px 5px 2px 3px;
  border-radius: 3px;
  font-size: 12px;
  overflow: hidden;
  cursor: pointer;
  height: 23px;
  line-height: 23px;
  border: 1px solid #e9eaec;
  background: #fff;
  padding: 0 5px 0 12px;
  vertical-align: middle;
  color: #666;
  -webkit-transition: all 0.3s ease-in;
  -moz-transition: all 0.3s ease-in;
  transition: all 0.3s ease-in;
}

.tabs-li:not(.active):hover {
  background: #f8f8f8;
}

.tabs-li.active {
  color: #fff;
  background: #49a1f8;
}

.tabs-li-title {
  float: left;
  max-width: 80px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 5px;
  color: #666;
}

.tabs-li.active .tabs-li-title {
  color: #fff;
}

.tabs-close-box {
  position: absolute;
  right: 0;
  top: 0;
  box-sizing: border-box;
  text-align: center;
  width: 10rem;
  z-index: 10;
}

a {
  text-decoration: none;
  color: inherit;
}
</style>
