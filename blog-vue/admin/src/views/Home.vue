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
            left: 0;
            .background{
                width: 520px;
                height: 100%;
                background-image: url("../assets/images/login-background.svg");
                background-repeat: no-repeat;
                background-position: 0 center;
            }
        }
    }
    .login-container{
        
    }
</style>