<template>
  <div>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
    <div class="article-container">

      <div class="article-header">
        <div class="line"></div>
        <span>编辑文章</span>
        <div class="article-btn">
          <span>私密</span>
          <el-switch class="private-sw" v-model="article.visible" active-value="0" inactive-value="1"/>
          <span>置顶</span>
          <el-switch class="top-sw" v-model="article.top" active-value="1" inactive-value="0"/>
          <el-button class="publish-btn" type="primary" @click="publishArticle">保存更改</el-button>
        </div>
      </div>

      <div class="article-content">
        <el-input class="title" v-model="article.title" placeholder="请输入标题" ></el-input>
        <el-input class="summary" v-model="article.summary" placeholder="请输入简介" style="margin-bottom: 0.5rem;"></el-input>
        <v-md-editor v-model="article.content" placeholder="正文" v-loading="loading"></v-md-editor>
      </div>
      <div class="article-footer">
        <div class="category">
          <span>分类:</span>
          <el-select class="category-select" v-model="article.categoryId" clearable placeholder="选择一个分类" >
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
          <el-select class="tag-select" v-model="article.tagIds" multiple placeholder="选择标签">
            <el-option
                v-for="item in tags"
                :key="item.id"
                :label="item.tagName"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </div>

      </div>
    </div>

    <el-dialog
        v-model="dialogVisible"
        width="20rem"
        title="提示"
    >
      <span>确认保存吗？</span>

      <template #footer>
      <span class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onConfirmClick" :loading="loading"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts">
import { Ref, ref } from 'vue';
import { addArticle, getArticleByIdBacked, getCategories,getTags, updateArticle } from '@/api/article';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
export default {
setup() {
    const router = useRouter();
    let categories: Ref = ref([]);
    let tags: Ref = ref([]);
    let dialogVisible = ref(false);
    const loading = ref(true);
    getCategories().then((data) => {
        categories.value = data.data;
    });
    
    getTags().then((data) => {
        tags.value = data.data;
    });

    const article = ref({
        id: router.currentRoute.value.params.id,
        categoryId: null,
        tagIds: [],
        title: "",
        summary: "",
        content: "",
        top: 0,
        visible: 1,
    })

    //拉取文章信息
    getArticleByIdBacked(router.currentRoute.value.params.id).then((data) => {
        let articleData = ref();
        articleData.value = data.data;
        let tagIds = [];
        articleData.value.tags.forEach(item => {
            tagIds.push(item.id);
        });
        article.value = articleData.value;
        article.value.tagIds = tagIds;
        loading.value = false;
    })

    const publishArticle = () => {
        if(article.value.title.trim() == ""){
            ElMessage({
                message: '文章标题不能为空!',
                type: 'error',
            });
            return;
        }
        if(article.value.content.trim() == ""){
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
        updateArticle(article.value).then((data: any) => {
            dialogVisible.value = false;
            loading.value = false;
            if(!data.errCode){
                ElMessage({
                    message: "文章保存成功!",
                    type: 'success',
                });
                router.push('/articles/list');
            }  
        });
    }
    

    return {
        categories,
        tags,
        article,
        dialogVisible,
        loading,
        publishArticle,
        onConfirmClick,
    }
      
    }
}
</script>

<style lang="scss" scoped>
.article-btn{
    position: absolute;
    right: 1.5rem;
    top: 4.4rem;
    > span{  
       margin-right: 0.3rem;
    }
    .private-sw{
        margin-right: 1rem;
    }
    
}
.article-header{
    display: flex;
    margin-bottom: 1rem;
    >span{
        font-size: 20px;
        margin-left: 0.5rem;
    }

}
.line{
    width: 0.5rem;
    height: 2rem;
    background: #666666;
}
.article-content{
    .title{
        margin-bottom: 0.5rem;
    }
}
.article-footer{
    display: flex;
    justify-content: center;
    margin-top: 1rem;
    span{
        font-size: 1.1rem;
        font-weight: bold;      
    }
    .category-select{
        margin-left: 0.5rem;
        width: 30vw;
    }
    .tag{
        margin-left: 1rem;
    }
    .tag-select{
        margin-left: 0.5rem;
        width: 30vw;
    }
}

:deep(.v-md-editor){
  height: 60vh;
}
    
</style>