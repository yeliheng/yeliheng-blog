<template>
    <div class="tags-container">
        <div class="tag-card" 
            v-for="tag in tagList"
            :key="tag.id"
            @click="handleCardClick(tag.id,tag.articleCount)"
        >

                <span class="tagText"><span class="iconfont icon-24gf-tags"></span> {{tag.tagName}}</span>
                <div class="text">文章数：<span class="count">{{tag.articleCount}}</span></div>
 
        </div>
    </div>

</template>

<script lang="ts">
import { getTagList } from '@/api/index';
import { ref } from 'vue';
import { useRouter } from 'vue-router'
export default {
    setup(){
        const tagList = ref();
        const router = useRouter();
        getTagList().then((res: any) => {
            tagList.value = res.data;
        });
        const handleCardClick = (id,articleCount) => {
            if(articleCount > 0)
                router.push('/tags/' + id);
        }
        return {
            tagList,
            handleCardClick,
        };
    }

}
</script>

<style lang="scss" scoped>
    .tags-container {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        margin-top: 1rem;
        width: 100%;
        
        .tag-card{
            &:hover{           
                background: #535353;
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
            background: #1e1e1e;
            .tagText{
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
    .tags-container {
        
        //grid-template-columns: repeat(2, 50%);
        .tag-card{
            width: 10rem;
            height: 5rem;
            border-radius: 15px;
        }
    }
}
</style>