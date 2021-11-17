<template>
    <div class="article-list-container">
        <div class="article-list-header">
            <div class="line"></div>
            <span>文章管理</span>
        </div>
        <div class="article-list-body">
            <el-form :inline="true" :model="searchParams" class="search-article" :size="formSize">
                <el-form-item label="标题: ">
                <el-input v-model="searchParams.title" placeholder="标题关键字"></el-input>
                </el-form-item>
                <el-form-item label="分类:">
                    <el-select v-model="searchParams.categoryId" clearable placeholder="选择一个分类">
                        <el-option
                        v-for="item in categories"
                        :key="item.id"
                        :label="item.categoryName"
                        :value="item.id"
                        >
                        </el-option>
                    </el-select> 
                </el-form-item>
                <el-form-item label="可见性: ">
                    <el-select v-model="searchParams.visible" clearable>
                        <el-option
                        v-for="item in articleVisible"
                        :key="item.visible"
                        :label="item.label"
                        :value="item.visible"
                        >
                        </el-option>
                    </el-select>  
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="searchArticles">搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <router-link to="/articles/"><el-button type="primary">发布文章</el-button></router-link>    
                </el-form-item>
            </el-form>
                
            
            <el-table
            class="article-table"
                ref="multipleTable"
                :data="table.data"
                v-loading="table.loading"
            >
                <el-table-column type="selection" width="55" />
                <el-table-column property="id" label="文章编号" width="80" align="center" />
                <el-table-column property="title" label="标题" width="220" align="center"/>
                <el-table-column property="categoryName" label="分类" width="120" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.category == null"> - </span>
                        <el-tag v-else type="warning"> {{scope.row.category.categoryName}} </el-tag>
                    </template>
                </el-table-column>
                <el-table-column property="tags" label="标签" width="170" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.tags == ''"> - </span>
                        <el-tag style="margin-left: 0.2rem"
                        v-for="item in scope.row.tags"
                        :key="item.id"
                        :label="item.tagName"
                        :value="item.id"                  
                        >
                        {{item.tagName}} 
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column property="createdAt" label="创建时间" width="150" align="center"/>
                <el-table-column property="updatedAt" label="更新时间" width="150" align="center"/>
                <el-table-column property="visible" label="状态" align="center">
                    <template #default="scope">
                    <el-tag :type="getVisibleDict(scope.row.visible).type"> {{getVisibleDict(scope.row.visible).label}} </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                <template #default="scope">
                    <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row.id)">编辑</el-button>
                    <el-popconfirm title="确定删除该文章? " @confirm="handleDelete(scope.row.id)">
                    <template #reference>
                    <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
                    </template>
                </el-popconfirm>
                </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="article-list-footer">
            <!-- 分页 -->
            <el-pagination
                class="pagination-nav"
                v-model:currentPage="table.page"
                :page-sizes="[10, 20, 30]"
                :page-size="table.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="table.total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :small="table.isMobile"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { deleteArticle, getArticlesAdmin,getCategories } from '@/api/article';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

export default {    
    setup(){

        const store = useStore();
        
        const router = useRouter();

        const formSize = ref('large');

        const getVisibleDict = (visible) => {
            let label = '';
            let type = '';
            if(visible == 1){
                label = '公开';
                type = 'success';
            }else{
                label = '私密';
                type = 'warning';
            }
            return {"label": label,"type": type};
        };


        const articleVisible = [
            {
                visible: 1,
                label: '公开',
            },
            {
                visible: 0,
                label: '私密',
            }
        ];
        //适配移动端
        if(store.state.app.isMobile){
            formSize.value = 'mini';
        }else{
            formSize.value = 'large';
        }

        const table:any = ref({
            page: 1,        // 当前在第几页
            pageSize: 10,   // 一页显示多少
            total: 0,       // 数据总量
            loading: false,  // 加载中
            data: [],       // 表格数据
            isMobile: false,// 表格是否移动端
        });

        const searchParams: any = ref({
            title: '',
            summary: '', 
            categoryId: null,
            visible: null,  
        });

        const categories: any = ref([]);

        getCategories().then((res) => {
            categories.value = res.data;
        });

        table.value.isMobile = store.state.app.isMobile;

        const listArticles = () => {
            table.value.loading = true,
            getArticlesAdmin({
                page: table.value.page,
                pageSize: table.value.pageSize,
            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.total;
                table.value.loading = false;
            });
        }

        listArticles();

        const handleSizeChange = (pageSize: number) => {
            table.value.pageSize = pageSize;
            listArticles();
        }

        const handleCurrentChange = (page: number) => {
            table.value.page = page;
            listArticles();
        }

        const searchArticles = () => {
            table.value.loading = true,
            getArticlesAdmin({
                page: table.value.page,
                pageSize: table.value.pageSize,
                title: searchParams.value.title,
                summary: searchParams.value.summary,
                categoryId: searchParams.value.categoryId,
                visible: searchParams.value.visible,

            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.total;
                table.value.loading = false;
            });
        }

        const handleDelete = (id) => {
            deleteArticle(id).then((res) => {
            if(res.data) {
              ElMessage.success("删除成功!");
              listArticles();
            }
            });
        }

        const handleEditClick = (id) => {
            router.push('/articles/' + id);
        }
        return {
            table,
            handleSizeChange,
            handleCurrentChange,
            searchParams,
            categories,
            searchArticles,
            formSize,
            articleVisible,
            getVisibleDict,
            handleDelete,
            handleEditClick
            };
        }
}
</script>

<style lang="scss" scoped>
.article-list-header{
    display: flex;
    margin-bottom: 1rem;
    >span{
        font-size: 20px;
        margin-left: 0.5rem;
    }

}
.line{
    width: 0.5rem;
    height: 2rem;
    background: #666666;
}
.article-list-footer{
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.article-table{
    width: 100%;
    height: 50rem;
    overflow: auto;
}

@media screen and (max-width: 480px){
    .article-table{
        height: 28rem;
        overflow: auto;
    }
}

</style>