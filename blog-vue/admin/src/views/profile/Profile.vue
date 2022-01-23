<template>
  <div class="profile-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>我的资料卡</span>
        </div>
        <div class="avatar-container" @click="openCropper()">
          <img :src="avatar" class="user-avatar" v-if="avatar != null"/>
          <img src="../../assets/images/avatar_default.svg" v-if="avatar == null" class="user-avatar">

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
      <el-card class="edit-profile-card">
        <div class="card-header">
          <span>修改资料</span>
        </div>
        <el-tabs v-model="activeTab">
          <el-tab-pane label="我的资料" name="profile">
            <el-form ref="profileForm" :model="userForm" :rules="profileRules" label-width="8rem">
              <el-form-item label="昵称: " prop="nickname">
                <el-input v-model="userForm.nickname" placeholder="请输入昵称" maxlength="30" />
              </el-form-item>
              <el-form-item label="手机号: " prop="phone">
                <el-input v-model="userForm.phone" placeholder="请输入手机号" maxlength="11" />
              </el-form-item>
              <el-form-item label="电子邮箱: " prop="email">
                <el-input v-model="userForm.email" placeholder="请输入电子邮箱" maxlength="30" />
              </el-form-item>

              <el-form-item>
                <el-button type="primary" size="mini" @click="submitProfile()">保存</el-button>
                <el-button type="danger" size="mini" @click="close()">关闭</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="修改密码" name="resetPassword">
            <el-form ref="pwdForm" :model="passwordForm" :rules="resetPasswordRules" label-width="8rem">
              <el-form-item label="旧密码" prop="oldPassword">
                <el-input v-model="passwordForm.oldPassword" placeholder="请输入旧密码" type="password"/>
              </el-form-item>
              <el-form-item label="新密码" prop="newPassword">
                <el-input v-model="passwordForm.newPassword" placeholder="请输入新密码" type="password"/>
              </el-form-item>
              <el-form-item label="确认密码" prop="confirmPassword">
                <el-input v-model="passwordForm.confirmPassword" placeholder="请确认密码" type="password"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="mini" @click="submitPassword()">保存</el-button>
                <el-button type="danger" size="mini" @click="close()">关闭</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-card>

  </div>
</template>

<script lang="ts">
import { ref } from "vue";
import {useStore} from "vuex";
import {updateProfile,resetPassword} from "@/api/profile";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

export default {
  setup() {
    const store = useStore();
    const user = store.state.user;
    const activeTab = ref('profile');
    const router = useRouter();
    let avatar = null;
    if(user.profile.avatar) {
      avatar = process.env.VUE_APP_BASE_API + user.profile.avatar;
    }
    const profileRules = {
      email: [
        {
          type: "email",
          message: "'请输入正确的邮箱地址",
          trigger: ["blur", "change"]
        }
      ],
      phone: [
        {
          pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
          message: "请输入正确的手机号码",
          trigger: "blur"
        }
      ]
    };

    const resetPasswordRules = {
      oldPassword: [
        { required: true, message: "旧密码不能为空", trigger: "blur" }
      ],
      newPassword: [
        { required: true, message: "新密码不能为空", trigger: "blur" },
        { min: 6, max: 20, message: "密码长度在6到20个字符之间", trigger: "blur" }
      ],
      confirmPassword: [
        { required: true, message: "确认密码不能为空", trigger: "blur" }
      ]
    }

    const userForm = ref(
        {
          username: user.username,
          nickname: '',
          phone: '',
          email: '',
        }
    );
    const passwordForm = ref(
        {
          oldPassword: '',
          newPassword: '',
          confirmPassword: '',
        }
    );

    const profileForm = ref();
    const submitProfile = () => {
      profileForm.value.validate(valid => {
        if(valid) {
          if (!userForm.value.nickname && !userForm.value.phone && !userForm.value.email) {
            return;
          }

          updateProfile(userForm.value).then((res: any) => {
            if (res.errCode) {
              ElMessage.error(res.detail);
              return;
            }
            ElMessage.success("修改资料成功!");
            location.reload();
          });
        }
      });
    };


    const pwdForm = ref();
    const submitPassword = () => {
      pwdForm.value.validate(valid => {
        if(valid) {
          if(passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
            ElMessage.error('两次密码输入不一致!');
            return;
          }
          resetPassword(passwordForm.value).then((res: any) => {
            if(res.errCode) {
              ElMessage.error(res.detail);
              return;
            }
            ElMessage.success('重置密码成功,请使用新密码登录');
            store.dispatch('Logout').then(() => {
              router.push('/login');
            }).catch(() => {
              Promise.reject();
            });
          });
        }
      });
    };

    const close = () => {
      router.back();
    };

    const openCropper = () => {
      console.log('打开裁剪框');
    }


    return {
      user,
      avatar,
      activeTab,
      profileRules,
      userForm,
      passwordForm,
      submitProfile,
      submitPassword,
      close,
      resetPasswordRules,
      pwdForm,
      profileForm,
      openCropper,

    }
  },
  components: {
  }
}
</script>

<style lang="scss" scoped>
  .profile-container{
    display: flex;
    justify-content: center;
  }
  .edit-profile-card {
    margin-left: 3rem;
    width: 35vw;
  }
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

.avatar-container:hover:after {
  content: ' + ';
  position: absolute;
  margin-top: 0.3rem;
  width: 9.2rem;
  height: 9.1rem;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}

  .item {
    margin-bottom: 18px;
  }

  .box-card {
    width: 35vw;
  }

  /* 移动端 */
  @media screen and (max-width: 900px){
    .box-card {
      width: 100%;
    }
    .profile-container {
      display: block;
    }

    .edit-profile-card {
      margin-top: 1rem;
      margin-left: 0;
      width: 100%;

    }

  }
</style>