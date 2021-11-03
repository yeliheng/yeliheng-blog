<template>
    <div class="header">
        <div class="collapse-btn" @click="toggleSidebar">
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div class="welcome"> 
            欢迎 👏🏻
        </div>
        <div class="header-right">

        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
            <div class="user-avater">
                <img src="../../assets/images/avater.jpg"/>
                <i class="el-icon-caret-bottom"></i>
            </div> 
            <span class="el-dropdown-link">
                {{username}}        
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item command="user">个人中心</el-dropdown-item>
                    <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        </div>
    </div>
</template>

<script>
import {onMounted,computed} from 'vue';
import { useStore } from 'vuex';
export default {
    setup(){
        const store = useStore();

        const isMobile = computed(() => store.state.app.isMobile);

        const toggleSidebar = () => {
            if(isMobile.value){
                store.dispatch('openSidebar');
            }else{
                store.dispatch('toggleSidebar');
            }
            
        }

        
        const collapse = computed(() => 
            store.state.app.sidebarCollapse
        );


        return {
            toggleSidebar,
            collapse,
        }
    }
}
</script>
<style lang="scss" scoped>
    .header{
        position: relative;
        .collapse-btn {
            position: absolute;
            font-size: 1.8rem;
            color: #494949;
            margin-right: 0.5rem;
            top: 0.6rem;
            left: 0rem;
            :hover{
                cursor: pointer;
                color: #666666;
            }
        }
        .welcome{
            position: absolute;
            font-size: 1.2rem;
            color: #666666;
            left: 3rem;
            top: 0.68rem;
        }
        .header-right{
            position: absolute;
            right: 0rem;
            display: flex;
            justify-content: center;
            align-items: center;
            .user-avater{
                :hover{
                    cursor: pointer;
                }
                img{
                    margin-top: 0.35rem;
                    margin-right: 0.25rem;
                    width: 2.6rem;
                    height: 2.6rem;
                    border-radius: 50%;
                }   
            }
        }
        

    }
</style>