<template>
  <div>
    <el-dialog  width="800px" v-model="visible" title="修改头像" append-to-body @close="closeDialog()">
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
              ref="cropper"
              :img="options.img"
              :info="true"
              :autoCrop="options.autoCrop"
              :autoCropWidth="options.autoCropWidth"
              :autoCropHeight="options.autoCropHeight"
              :fixedBox="options.fixedBox"
              @realTime="realTime"
              v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div >
            <img :src="previews.url" :style="previews.img" class="avatar-upload-preview" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload()" :show-file-list="false">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import 'vue-cropper/dist/index.css'
import { VueCropper }  from "vue-cropper";
import {ref} from "vue";
import {useStore} from "vuex";
export default {
  components: {
    VueCropper,
  },
  setup() {
    const visible = ref(true);
    const store = useStore();
    const previews = ref({});
    const options = ref({
      img: process.env.VUE_APP_BASE_API + store.state.user.profile.avatar,
      autoCrop: true,
      autoCropWidth: 100,
      autoCropHeight: 100,
      fixedBox: true,
    });
    const realTime = (data) => {
      previews.value = data;
    }

    const requestUpload = () => {
      //
    };

    console.log(options.value.img);

    return {
      visible,
      options,
      previews,
      realTime,
      requestUpload,
    }
  }
}
</script>

<style scoped>
.avatar-upload-preview {
  border-radius: 180px;
  position: absolute;
}
</style>