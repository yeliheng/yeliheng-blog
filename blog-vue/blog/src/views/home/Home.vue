<template>
<div class="home-container">

  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
      <div class="article-container"
        v-for="article in articleList"
        :key="article.id"
      >
        <div class="article-title" @click="readArticle(article.url)">{{ article.title }}</div>
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
          <span class="iconfont icon-tuding is-top" v-if="article.top === '1'"></span>
        </div>
        <div class="summary">
            <span v-if="article.summary == null"> 暂无简介 </span>
           {{article.summary}}
          </div>
        <div class="read-btn-container">
          <span class="read-btn" @click="readArticle(article.url)">阅读全文</span>
        </div>
        <div class="split-line"></div>
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
import { getArticleList } from '@/api';
import {useRouter} from 'vue-router';
import { ref } from 'vue';
import { useStore } from 'vuex';
export default {
  setup(){
    const router = useRouter();
    const pageCount = ref();
    const page = ref(1);
    const pageSize = 10;
    const articleList = ref([]);
    const store = useStore();
    store.dispatch('isLoading');
    getArticleList({"page": page.value,"pageSize": pageSize}).then((res: any) => {
      pageCount.value = res.data.pages;
      articleList.value = res.data.list;
      store.dispatch('notLoading');
    });

    const loadArticle = () => {
      store.dispatch('isLoading');
      getArticleList({"page": page.value,"pageSize": pageSize}).then((res: any) => {
        articleList.value = res.data.list;
      }).then(() => {
        scrollTo(0,0);
        store.dispatch('notLoading');
      });
    }

    const readArticle = (id) =>{ 
      store.dispatch('closeSidebar');
      router.push({
        path: '/p/' + id, 
      });
    }
  

    return {
      loadArticle,
      articleList,
      pageCount,
      page,
      readArticle,
    }
  }

}
</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;

.is-top {
  @include font_color("highlightTextColor");
  margin: {
    left: 0.6rem;
    top: 0.4rem;
  }
  font-size: 1rem;
}

@keyframes change-opcity{
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
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
    @include font_color("primaryTextColor");
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
      @include background_color("highlightTextColor");
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
        @include font_color("secondaryTextColor");
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
    border: 0.13rem solid;
    @include border_color("primaryTextColor");
    :hover{
      cursor: pointer;
      @include background_color("primaryTextColor");
      @include font_color("reverseTextColor");
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
    @include background_color("secondaryTextColor");
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


/* 移动端 */
@media screen and (max-width: 900px){
  .info-text{
    display: none;
  }

  .loading-bar-full {
    display: flex;
    background-color: #121212;
    transition: all 0.5s;
    height: 60vh;
    position: relative;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    justify-content: center;
    align-items: center;
    z-index: 999999;
  }
  .no-loading {
    position: absolute;
    visibility: collapse;
    opacity: 0;
  }

}
</style>