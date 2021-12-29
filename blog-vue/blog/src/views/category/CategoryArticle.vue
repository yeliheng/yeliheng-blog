<template>
<div class="home-container">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
      <div class="article-container"
        v-for="article in articleList"
        :key="article.id"
      >
        <div class="article-title" @click="readArticle(article.id)">{{ article.title }}</div>
        <div class="article-info">
          <div class="pub-time info"><span class="iconfont icon-rili" style="margin-right: 0.5rem;"></span>
          <span class="info-text">发表于 </span>
          {{article.createdAt}}
          </div>
          <div class="words info">
            <span class="iconfont icon-zishu" style="margin-right: 0.5rem;"></span>
            <span class="info-text">字数统计: </span>
            {{article.words}}
            </div>
          <div class="read-time info"><span class="iconfont icon-shizhong" style="margin-right: 0.5rem;"></span>
          <span class="info-text">阅读时长(分钟) ≈  </span>{{article.readingTime}}</div>
        </div>
        <div class="summary">
            <span v-if="article.summary == null"> 暂无简介 </span>
           {{article.summary}}
          </div>
        <div class="read-btn-container">
          <span class="read-btn" @click="readArticle(article.id)">阅读全文</span>
        </div>
        <div class="split-line"></div>
      </div>
      <div class="loading-bar" ref="loadingBar">
        <v-loading></v-loading>
      </div>
          
      <div class="pagination">
        <v-pagination
          v-model="page"
          :pages="pageCount"
          :range-size="1"
          active-color="#d0d0d0"
          @update:modelValue="loadArticle"
        />
      </div>
    </div>
    

</template>

<script lang="ts">
import '../../assets/iconfont.css';
import { getArticleListByCategoryId } from '../../api/index';
import {useRouter} from 'vue-router';
import { ref, onMounted } from 'vue';
export default {
  setup(){
    const router = useRouter();
    const pageCount = ref();
    const page = ref(1);
    const pageSize = 5;
    const articleList = ref([]);
    const loadingBar = ref(null);
    onMounted(() => {
      loadingBar.value
    });
    //TODO: 内容加载动画
    getArticleListByCategoryId({"categoryId": router.currentRoute.value.params.id, "page": page.value, "pageSize": pageSize}).then((res: any) => {
      pageCount.value = res.data.pages;
      articleList.value = res.data.list;
    });

    const loadArticle = () => {
          loadingBar.value.style.opacity = "1";
          getArticleListByCategoryId({"categoryId": router.currentRoute.value.params.id, "page": page.value, "pageSize": pageSize}).then((res: any) => {
            articleList.value = res.data.list;
            loadingBar.value.style.opacity = "0";
            scrollTo(0,0);
      });
    }

    const readArticle = (id) =>{ 
      router.push({
        path: '/articles/' + id, 
      });
    }
  

    return {
      loadArticle,
      articleList,
      pageCount,
      page,
      loadingBar,
      readArticle
    }
  }

}
</script>

<style lang="scss" scoped>
.loading-bar{
  transition: all 0.5s;
  opacity: 0;
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}

@keyframes change-opcity{
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
}

:deep(.spinner){
    &::after{
      background-color: #d4d3d3;
    }
}

.article-container{
  padding: {
    top: 3rem;
    bottom: 3rem;
    left: 1.5rem;
    right: 1.5rem;
  }
  text-align: center;
  .article-title{
    font-size: 1.5rem;
    color: #d0d0d0;
    &:hover{
      cursor: pointer;
      &::after{
        transform: scaleX(1);
      }
    }
    &::after{
      content: "";
      height: 1px;
      width: 100%;
      background-color: #fff;
      display: flex;
      visibility: visible;
      transform: scaleX(0);
      transition-duration: 0.2s;
      transition-timing-function: ease-in-out;
      transition-delay: 0s;
}

  }
  .article-info{
    display: flex;
    justify-content: center;
    .info{
        color: #9e9e9e;
        font-size: 0.8rem;
        margin-left: 1.2rem;
        margin-top: 0.2rem;
    }
  }
  .summary{
    text-align: start;
    margin: {
      left: 2rem;
      top: 2rem;
    }
    min-height: 4rem;
  }
  .read-btn-container{     
    margin: auto;
    width: 8rem;
    height: 2.2rem;
    border: 0.13rem solid #D0D0D0;
    :hover{
      cursor: pointer;
      background: #d0d0d0;
      color: #121212
    }
    .read-btn{
      transition: all 0.5s;
      display: flex;
      width:100%;
      height:100%;
      justify-content: center;
      align-items:center;
    }
  }
  .split-line{
    margin: {
      top: 3rem;
      bottom: 3rem;
      left: auto;
      right: auto;
    }
    height: 1px;
    width: 5rem;
    background: #6f6f6f; 
  }
}

.info-text{
  display: inline;
}
.pagination{
  display: flex;
  justify-content: center;
  margin-bottom: 2rem !important;
}

:deep(.Page){
  color: #d0d0d0;
  }
  :deep(.Page-active){
    color: #121212;
  }
  :deep(.Control){
    fill: #353535;
  }
  :deep(.Control-active){
    fill: #9e9e9e;
  } 



/* 移动端 */
@media screen and (max-width: 900px){
  .info-text{
    display: none;
  }
}
</style>