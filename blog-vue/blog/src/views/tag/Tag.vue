<template>
    <div class="tags-container">
        <div class="loading-bar-full" :class="{'no-loading': !isLoading,'is-loading': isLoading}" >
            <v-loading></v-loading>
        </div>
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
        const isLoading = ref(true);
        const tagList = ref();
        const router = useRouter();
        getTagList().then((res: any) => {
            tagList.value = res.data;
            isLoading.value = false;
        });
        const handleCardClick = (id,articleCount) => {
            if(articleCount > 0)
                router.push('/tags/' + id);
        }
        return {
            tagList,
            handleCardClick,
            isLoading,
        };
    }

}
</script>

<style lang="scss" scoped>
:deep(.spinner){
    &::after{
      background-color: #d4d3d3;
    }
}
.no-loading {
  opacity: 0;
}

.is-loading {
  opacity: 1;
}
.loading-bar-full {
  display: flex;
  background-color: #121212;
  transition: all 0.5s;
  height: 100vh;
  position: absolute;
  top: 0;             
  bottom: 0;           
  left: 0;        
  right: 0;
  margin: auto;
  justify-content: center;
  align-items: center;
  z-index: 999999;
}
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