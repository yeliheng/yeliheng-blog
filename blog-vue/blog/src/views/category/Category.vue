<template>
    <div class="categories-container">
        <router-link class="category-card" 
            v-for="category in categoryList"
            :key="category.id"
            :to="'/category/' + category.id"
        >

                <span class="categoryText"><span class="iconfont icon-fenlei"></span> {{category.categoryName}}</span>
                <div class="text">文章数：<span class="count">{{category.articleCount}}</span></div>
 
        </router-link>
    </div>

</template>

<script lang="ts">
import { getCategoryList } from '@/api/index';
import { ref } from 'vue';
export default {
    setup(){
        const categoryList = ref();
        getCategoryList().then((res: any) => {
            categoryList.value = res.data;
        });
        return {
            categoryList,
        };
    }

}
</script>

<style lang="scss" scoped>
    .categories-container {
        display: flex;
        flex-wrap: wrap;
        margin-top: 1rem;
        margin-left: 1rem;
        width: 100%;
        
        .category-card{
            &:hover{           
                background: #535353;
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
            background: #1e1e1e;
            .categoryText{
                color: #d0d0d0;
            }
            .text{
                font-size: 1rem;
                color: #9e9e9e;
                .count{
                  font-style: italic;  
                  font-weight: bold;
                  font-size: 1.2rem;  
                  color: #d0d0d0;
                }
            }
        }
    }

@media screen and (max-width: 900px){
    .categories-container {
        
        //grid-template-columns: repeat(2, 50%);
        .category-card{
            width: 10rem;
            height: 5rem;
            border-radius: 15px;
        }
    }
}
</style>