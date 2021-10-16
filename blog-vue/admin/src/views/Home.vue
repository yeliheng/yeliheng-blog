<template>
    <div class="page-container">
        <div class="background-container">
            <div class="background"></div>
        </div>
        <div class="login-container">
            <login-card></login-card>
        </div>
    </div>
    

</template>

<script>
import loginCard from '@/components/login/login-card.vue';
export default {
  components: { loginCard },

}
</script>

<style lang="scss" scoped>
    .page-container{
        .background-container{
            position: absolute;
            height: 100%;
            background-color: #f5f9fc;
            left: 10rem;
            .background{
                width: 800px;
                height: 100%;
                background-image: url("../assets/images/login-background.svg");
                background-repeat: no-repeat;
                background-position: 0 center;
            }
        }
    }
    .login-container{
        position: absolute;
        overflow: auto;
        display: flex;
        right: 8rem;
        top: 10rem;
    }


@media screen and (max-width: 480px){
    .page-container{

        .background-container{
            .background{
                position: absolute;
                width: 200px;
            }
        }
    }
    .login-container {
        position: absolute;
        left: 0;
        right: 0;
        overflow: auto;
        display: flex;
        justify-content: center;
        padding-top: 2rem;        
    }

}
</style>