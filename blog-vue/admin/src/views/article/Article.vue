<template>
  <div>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0"/>
    <div class="article-container">

      <div class="article-header">
        <div class="line"></div>
        <span>文章编辑器</span>
        <div class="article-btn">
          <span>私密</span>
          <el-switch class="private-sw" v-model="article.visible" active-value="0" inactive-value="1"/>
          <span>置顶</span>
          <el-switch class="top-sw" v-model="article.top" active-value="1" inactive-value="0"/>
          <el-button type="success" @click="openDraftBox" style="margin-left: 1rem;">草稿箱</el-button>
          <el-button type="primary" v-if="articleId == null" @click="publishArticle" style="margin-left: 1rem;">保存并发布
          </el-button>
          <el-button type="primary" v-else @click="publishArticle" style="margin-left: 1rem;">更新</el-button>
        </div>
      </div>

      <div class="article-content">
        <el-input class="title" v-model="article.title" placeholder="请输入标题" @change="draftChanged"></el-input>
        <el-input class="summary" v-model="article.summary" placeholder="请输入文章简介" style="margin-bottom: 0.5rem;"
                  @change="draftChanged"></el-input>
        <v-md-editor
            v-model="article.content"
            :disabled-menus="[]"
            placeholder="正文"
            @change="draftChanged"
            @upload-image="handleUploadImage"
        />
      </div>
      <div class="article-footer">
        <div class="category">
          <span>分类:</span>
          <el-select class="selector" v-model="article.categoryId" clearable placeholder="选择一个分类">
            <el-option
                v-for="item in categories"
                :key="item.id"
                :label="item.categoryName"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div class="tag">
          <span>标签:</span>
          <el-select class="selector" v-model="article.tagIds" multiple placeholder="选择标签">
            <el-option
                v-for="item in tags"
                :key="item.id"
                :label="item.tagName"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div class="publish-time">
          <span>发布时间:</span>
          <el-date-picker
              style="margin-left: 0.5rem;"
              v-model="article.createdAt"
              type="datetime"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="选择发布时间">
          </el-date-picker>
        </div>

      </div>
    </div>

    <el-dialog
        v-model="dialogVisible"
        width="20rem"
        title="提示"
    >
      <span>是否发布该文章？</span>

      <template #footer>
      <span class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onConfirmClick" :loading="loading"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>

    <!--    <el-dialog
            v-model="existDraft"
            width="20rem"
            title="提示"
            :close-on-click-modal="false"
        >
          <span>你有未保存的草稿文章，是否恢复？</span>

          <template #footer>
          <span class="dialog-footer" style="display: flex; justify-content: center;">
            <el-button @click="">取消</el-button>
            <el-button type="primary" @click="revertDraft()"
            >确定</el-button
            >
          </span>
          </template>
        </el-dialog>-->
  </div>

</template>

<script lang="ts">
import {Ref, ref, watch} from 'vue';
import {addArticle, getArticleByIdBacked, getCategories, getTags, updateArticle, uploadImage} from '@/api/article';
import {ElMessage, ElMessageBox} from 'element-plus';
import {useRouter} from "vue-router";
import {addDraft, deleteDraft, getDraft, updateDraft} from "@/api/draft";

export default {
  setup() {
    const router = useRouter();
    const article = ref({
      id: null,
      articleId: null,
      categoryId: null,
      draftId: null,
      tagIds: [],
      title: "",
      summary: "",
      content: "",
      visible: 1,
      top: 0,
      createdAt: "",
    });
    const articleId = ref(router.currentRoute.value.params.articleId);
    let draftId = router.currentRoute.value.params.draftId;

    watch(() => router.currentRoute.value.params.draftId, () => {
      draftId = router.currentRoute.value.params.draftId;
    });

    watch(() => router.currentRoute.value.params, () => {
      articleId.value = router.currentRoute.value.params.articleId;
      // 如果是更新文章，获取文章信息
      if (articleId.value != null) {
        //拉取文章信息
        getArticleByIdBacked(articleId.value).then((data) => {
          let articleData = ref();
          articleData.value = data.data;
          let tagIds = [];
          articleData.value.tags.forEach(item => {
            tagIds.push(item.id);
          });
          article.value = articleData.value;
          article.value.tagIds = tagIds;
          draftId = articleData.value.draftId;
          loading.value = false;
        })
      } else if (router.currentRoute.value.params.draftId != null) {
        // 从草稿中恢复
        getDraft(router.currentRoute.value.params.draftId).then((data) => {
          let draftData = ref();
          draftData.value = data.data;
          article.value.title = draftData.value.title;
          article.value.summary = draftData.value.summary;
          article.value.content = draftData.value.content ? draftData.value.content : "";
          article.value.id = draftData.value.articleId;
          loading.value = false;
        })
      } else {
        article.value = {
          id: null,
          articleId: null,
          categoryId: null,
          draftId: null,
          tagIds: [],
          title: "",
          summary: "",
          content: "",
          visible: 1,
          top: 0,
          createdAt: "",
        };
      }
    }, {immediate: true});
    let categories: Ref = ref([]);
    let tags: Ref = ref([]);
    let dialogVisible = ref(false);
    let loading = ref(false);
    getCategories().then((data) => {
      categories.value = data.data;
    });

    getTags().then((data) => {
      tags.value = data.data;
    });

    const publishArticle = () => {
      if (article.value.title.trim() == "") {
        ElMessage({
          message: '文章标题不能为空!',
          type: 'error',
        });
        return;
      }
      if (article.value.content.trim() == "") {
        ElMessage({
          message: '文章内容不能为空!',
          type: 'error',
        });
        return;
      }
      loading.value = false;
      dialogVisible.value = true;
    };

    const onConfirmClick = () => {
      loading.value = true;
      article.value.draftId = draftId;
      if (article.value.id != null) {
        updateArticle(article.value).then((data: any) => {
          dialogVisible.value = false;
          loading.value = false;
          if (!data.errCode) {
            ElMessage({
              message: "文章更新成功!",
              type: 'success',
            });
            router.push('/articles/list');
          }
        });
      } else {
        addArticle(article.value).then((data: any) => {
          dialogVisible.value = false;
          if (!data.errCode) {
            ElMessage({
              message: "发布成功！",
              type: 'success',
            });
            article.value.articleId = data.data;
            updateDraft(article.value);
            router.push('/articles/list');
          } else {
            ElMessage.error(data.detail);
          }
        });
      }
    }

    let isDraftSaved = false;

    // 定时检查草稿是否保存
    setInterval(() => {
      if (!isDraftSaved) {
        saveDraft();
      }
    }, 5000);

    const draftChanged = () => {
      isDraftSaved = false;
    }

    // 保存草稿
    const saveDraft = () => {
      isDraftSaved = true;
      if (article.value.title != '' || article.value.content != '' || article.value.summary != '') {
        if (draftId == null) {
          // 这个id是因为数据库中存储的id没有辨识，需要修改
          article.value.articleId = article.value.id;
          addDraft(article.value).then((data: any) => {
            if (!data.errCode) {
              draftId = data.data;
              console.log("2333333", article.value.createdAt);
              console.log("创建草稿: ", draftId);
            } else {
              console.error("保存草稿失败！", data.detail);
            }
          });
        } else {
          article.value.draftId = draftId;
          updateDraft(article.value).then((data: any) => {
            if (!data.errCode) {
              console.log("更新草稿: ", draftId);
            } else {
              if (data.errCode == 'GENERAL_EXCEPTION') {
                draftId = null;
                return;
              }
              console.error("更新草稿失败！", data);
            }
          });
        }
      }
    }

    const handleUploadImage = (event, insertImage, files) => {
      // 上传图片到服务器
      uploadImage(files[0]).then((data: any) => {
        if (!data.errCode) {
          insertImage({
            url: data.data,
            desc: files[0].name,
          });
        } else {
          ElMessage.error(data.detail);
        }
      });
    }

    const openDraftBox = () => {
      router.push('/draft/list');
    }

    return {
      categories,
      tags,
      article,
      draftId,
      dialogVisible,
      loading,
      publishArticle,
      onConfirmClick,
      saveDraft,
      handleUploadImage,
      openDraftBox,
      articleId,
      draftChanged,
    }
  }
}
</script>

<style lang="scss" scoped>
.article-btn {
  display: flex;
  align-items: center;
  margin-left: 1rem;

  > span {
    margin-right: 0.3rem;
  }

  .private-sw {
    margin-right: 1rem;
  }

}

.article-header {
  display: flex;
  margin-bottom: 1rem;

  > span {
    font-size: 20px;
    margin-left: 0.5rem;
  }

}

.line {
  width: 0.5rem;
  height: 2rem;
  background: #666666;
}

.article-content {
  .title {
    margin-bottom: 0.5rem;
  }
}

.article-footer {
  display: flex;
  justify-content: center;
  margin-top: 1rem;

  span {
    font-size: 1.1rem;
    font-weight: bold;
  }

  .tag {
    margin-left: 3rem;
  }

  .selector {
    margin-left: 0.5rem;
  }

  .publish-time {
    margin-left: 3rem;
  }
}

:deep(.v-md-editor) {
  height: 60vh;
}

</style>