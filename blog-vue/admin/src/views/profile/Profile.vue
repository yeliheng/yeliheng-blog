<template>
  <div class="profile-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>个人资料</span>
        </div>
        <div class="avatar-container">
          <img :src="avatar" class="user-avatar"/>
        </div>
      </template>


      <div class="user-info">
        <i class="fa fa-user fa-fw"/><div class="text item">用户名: </div>
        <div class="info-item">{{ user.profile.username }}</div>
      </div>

      <div class="user-info">
        <i class="fa fa-child fa-fw"/><div class="text item">昵称: </div>
        <div class="info-item">{{ user.profile.nickname }}
          <span v-if="user.profile.nickname == null" style="font-style: italic"> 未设置 </span>
        </div>
      </div>

      <div class="user-info">
        <i class="fa fa-phone fa-fw"/><div class="text item">手机号: </div>
        <div class="info-item"> {{ user.profile.phone }}
          <span v-if="user.profile.phone == null" style="font-style: italic"> 未设置 </span>
        </div>
      </div>

      <div class="user-info">
        <i class="fa fa-envelope fa-fw"/><div class="text item">电子邮箱: </div>
        <div class="info-item"> {{ user.profile.email }}
          <span v-if="user.profile.email == null" style="font-style: italic"> 未设置 </span>
        </div>
      </div>

      <div class="user-info">
        <i class="fa fa-address-book fa-fw"/><div class="text item">用户角色: </div>
        <div class="info-item"> {{ user.profile.roles }}
          <span v-if="user.profile.roles == null" style="font-style: italic"> 无 </span>
        </div>
      </div>

      <div class="user-info">
        <i class="fa fa-calendar fa-fw"/><div class="text item">注册日期: </div>
        <div class="info-item"> {{ user.profile.createdAt }}
          <span v-if="user.profile.createdAt == null" style="font-style: italic"> 未设置 </span>
        </div>
      </div>


    </el-card>
  </div>
</template>

<script lang="ts">
import {useStore} from "vuex";

export default {
  setup() {
    const store = useStore();
    const user = store.state.user;
    const avatar = process.env.VUE_APP_BASE_API + user.profile.avatar;
    return {
      user,
      avatar,
    }
  }
}
</script>

<style lang="scss" scoped>
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 1.2rem;
  }

  .user-info {
    display: flex;
    margin-left: 1rem;
    color: #555555;
    font-size: 1.1rem;
    i {
      margin-top: 0.23rem;
    }
    .info-item {
      margin-left: 1rem;
    }
  }
.avatar-container{
  display: flex;
  justify-content: center;
  .user-avatar {
    width: 10rem;
    height: 10rem;
    border-radius: 90px;

  }
}

  .item {
    margin-bottom: 18px;
  }

  .box-card {
    width: 34rem;
  }
</style>