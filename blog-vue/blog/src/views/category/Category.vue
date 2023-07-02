<template>
    <div class="categories-container">
        <div class="category-card"
            v-for="category in categoryList"
            :key="category.id"
            @click="handleCardClick(category.id,category.articleCount)"
        >
          <span class="categoryText"><span class="iconfont icon-fenlei"></span> {{category.categoryName}}</span>
          <div class="text">文章数：<span class="count">{{category.articleCount}}</span></div>
        </div>
    </div>
</template>

<script lang="ts">
import { getCategoryList } from '@/api';
import { ref } from 'vue';
import router from '@/router';
import store from "@/store";
export default {
    setup(){
        const categoryList = ref();
        store.dispatch('isLoading');
        getCategoryList().then((res: any) => {
            categoryList.value = res.data; 
            store.dispatch('notLoading');
        });
        const handleCardClick = (id,articleCount) => {
            if(articleCount > 0){
                router.push('/categories/' + id);
            }
                
        }
        return {
            categoryList,
            handleCardClick,
        };
    }

}
</script>

<style lang="scss" scoped>
@use "@/theme/_handle.scss" as *;
.categories-container {
    transition: all 0.5s;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    margin-top: 1rem;
    width: 100%;
    .category-card{
        &:hover{
            @include background_color("cardHoverBackgroundColor");
            cursor: pointer;
        }
        text-decoration: none;
        transition: all 0.8s ease-in-out;
        font-size: 1.5rem;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        margin: 1rem;
        width: 20rem;
        height: 10rem;
        border-radius: 20px;
        @include background_color("mainBackgroundColor");
        .categoryText{
          @include font_color("primaryTextColor");
        }
        .text{
            font-size: 1rem;
            @include font_color("secondaryTextColor");
            .count{
              font-style: italic;
              font-weight: bold;
              font-size: 1.2rem;
              @include font_color("primaryTextColor");
            }
        }
    }
}

@media screen and (max-width: 900px){
    .categories-container {
        .category-card{
            width: 11rem;
            height: 6.5rem;
            border-radius: 15px;
        }
    }
}
</style>