<template>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
    <div class="article-container">
        
        <div class="article-header">
            <div class="line"></div>
            <span>发布文章</span>
            <div class="article-btn">
                <span>私密</span>
                <el-switch class="private-sw" v-model="article.visible" active-value="0" inactive-value="1"/>
                <span>置顶</span>
                <el-switch class="top-sw" v-model="article.top" active-value="1" inactive-value="0"/>
                <el-button class="publish-btn" type="primary" @click="publishArticle">保存并发布</el-button>
            </div>
        </div>

        <div class="article-content">
            <el-input class="title" v-model="article.title" placeholder="请输入标题" @blur="saveDraft()"></el-input>
            <el-input class="summary" v-model="article.summary" placeholder="请输入文章简介" @blur="saveDraft()" style="margin-bottom: 0.5rem;"></el-input>
            <v-md-editor v-model="article.content" placeholder="正文" @blur="saveDraft()"></v-md-editor>
        </div>
        <div class="article-footer">
            <div class="category">
                <span>分类:</span>
                    <el-select class="category-select" v-model="article.categoryId" clearable placeholder="选择一个分类" @blur="saveDraft()" @remove-tag="saveDraft()">
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
                <el-select class="tag-select" v-model="article.tagIds" multiple placeholder="选择标签" @blur="saveDraft()" @remove-tag="saveDraft()">
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

  <el-dialog
    v-model="existDraft"
    width="20rem"
    title="提示"
    :close-on-click-modal="false"
  >
    <span>你有未保存的草稿文章，是否恢复？</span>
    
    <template #footer>
      <span class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="trashDraft()">取消</el-button>
        <el-button type="primary" @click="revertDraft()"
          >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import { Ref, ref } from 'vue';
import { addArticle, getCategories,getTags } from '@/api/article';
import {ElMessage, ElMessageBox} from 'element-plus';
import { useRouter } from "vue-router";
export default {
setup() {
    const router = useRouter();

    let categories: Ref = ref([]);
    let tags: Ref = ref([]);
    let dialogVisible = ref(false);
    const existDraft = ref(false);
    let loading = ref(false);
    getCategories().then((data) => {
        categories.value = data.data;
    });
    
    getTags().then((data) => {
        tags.value = data.data;
    });

    const article = ref({
        categoryId: null,
        tagIds: [],
        title: "",
        summary: "",
        content: "",
        visible: 1,
        top: 0,
    })

    //检查是否存在草稿
    if(localStorage.getItem('draft')){
        existDraft.value = true;
    }else{
        existDraft.value = false;
    }

    const revertDraft = () => {
        article.value = JSON.parse(localStorage.getItem('draft'));
        existDraft.value = false;
    }

    const trashDraft = () => {
      ElMessageBox.confirm('确定吗？所有的草稿将被删除！', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        localStorage.removeItem('draft');
        existDraft.value = false;
      });
    }

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
        addArticle(article.value).then((data: any) => {
            dialogVisible.value = false;
            if(!data.errCode){
                ElMessage({
                    message: "发布成功！",
                    type: 'success',
                });
                router.push('/articles/list');
                //删除草稿
                localStorage.removeItem('draft');
                existDraft.value = false;
            } else {
              ElMessage.error(data.detail);
            }
        });
    }

    const saveDraft = () => {
        if(article.value.title != '' || article.value.content != '')
            localStorage.setItem('draft',JSON.stringify(article.value));
        if(article.value.title == '' && article.value.content == '')
            localStorage.removeItem('draft');
    }
    

    return {
        categories,
        tags,
        article,
        dialogVisible,
        loading,
        publishArticle,
        onConfirmClick,
        saveDraft,
        existDraft,
        revertDraft,
        trashDraft,
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