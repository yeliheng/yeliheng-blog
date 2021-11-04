<template>
    <div class="article-container">
        <div class="article-header">
            <div class="title">发布文章</div>
        </div>
        <div class="article-content">
            <el-input  placeholder="请输入标题" size="normal"></el-input>
            <v-md-editor v-model="text" height="400px"></v-md-editor>
        </div>
        <div class="article-footer">
            文章页脚
        </div>
    </div>
</template>

<script>

export default {

}
</script>

<style lang="scss" scoped>

</style>