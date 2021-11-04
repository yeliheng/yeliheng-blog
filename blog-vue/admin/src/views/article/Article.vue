<template>
    <div class="article-container">
        <div class="article-btn">
            <el-button class="publish-btn" type="primary" size="default">保存并发布</el-button>
        </div>
        <div class="article-header">
            <div class="line"></div>
            <span>发布文章</span>
        </div>


        <div class="article-content">
            <el-input class="title" placeholder="请输入标题" size="normal"></el-input>
            <v-md-editor v-model="text" placeholder="正文" height="400px"></v-md-editor>
        </div>
        <div class="article-footer">
            <div class="category">
                <span>分类:</span>
                    <el-select class="category-select" v-model="value" clearable placeholder="选择一个分类">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                        </el-option>
                    </el-select> 
            </div>
            <div class="tag">
                <span>标签:</span>
                <el-select class="tag-select" v-model="tag" multiple placeholder="选择标签">
                        <el-option
                        v-for="item in optionsMultiple"
                        :key="item.tag"
                        :label="item.label"
                        :value="item.tag"
                        >
                        </el-option>
                </el-select> 
            </div>
            
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
export default {
setup() {
    return {
      options: ref([
        {
          value: 'Option1',
          label: '原创',
        },
        {
          value: 'Option2',
          label: '编程开发',
        },
        {
          value: 'Option3',
          label: 'Java',
        },
        {
          value: 'Option4',
          label: 'VR',
        },
        {
          value: 'Option5',
          label: 'Unity',
        },
      ]),
      optionsMultiple: ref([
        {
          tag: 'Option1',
          label: '原创',
        },
        {
          tag: 'Option2',
          label: '编程开发',
        },
        {
          tag: 'Option3',
          label: 'Java',
        },
        {
          tag: 'Option4',
          label: 'VR',
        },
        {
          tag: 'Option5',
          label: 'Unity',
        },
      ]),
      value: ref(''),
      tag: ref(''),
    }
  },
}
</script>

<style lang="scss" scoped>
.article-btn{
    position: absolute;
    right: 1.5rem;
    top: 4.4rem;
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
    
</style>