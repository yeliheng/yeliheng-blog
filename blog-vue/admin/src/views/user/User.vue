<template>
    <div class="user-list-container">
        <div class="user-list-header">
            <div class="line"></div>
            <span>用户管理</span>
        </div>
        <div class="user-list-body">
            <el-form :inline="true" :model="searchParams" class="search-user" :size="formSize">
                <el-form-item label="用户名: ">
                <el-input v-model="searchParams.username" placeholder="用户名关键字"></el-input>
                </el-form-item>
                <el-form-item label="昵称: ">
                    <el-input v-model="searchParams.nickname" placeholder="昵称关键字"></el-input>
                </el-form-item>
                <el-form-item label="手机号: ">
                    <el-input v-model="searchParams.phone" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item label="用户状态: ">
                    <el-select v-model="searchParams.locked" clearable>
                        <el-option
                        v-for="item in userLocked"
                        :key="item.locked"
                        :label="item.label"
                        :value="item.locked"
                        >
                        </el-option>
                    </el-select>  
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="searchUsers">搜索</el-button>
                </el-form-item>
            </el-form>
                
            
            <el-table
            class="user-table"
                ref="multipleTable"
                :data="table.data"
                v-loading="table.loading"
            >
                <el-table-column type="selection" width="55" />
                <el-table-column property="id" label="用户编号" width="80" align="center" />
                <el-table-column property="username" label="用户名" width="220" align="center"/>
                <el-table-column property="nickname" label="昵称" width="120" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.nickname == null"> - </span>
                    </template>
                </el-table-column>
                <el-table-column property="phone" label="手机号" width="120" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.phone == null"> - </span>
                    </template>
                </el-table-column>    
                <el-table-column property="roleList" label="用户角色" width="170" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.roleList == ''"> - </span>
                        <el-tag style="margin-left: 0.2rem"
                        v-for="item in scope.row.roleList"
                        :key="item.id"
                        :label="item.roleName"
                        :value="item.id"                  
                        >
                        {{item.roleName}} 
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column property="createdAt" label="创建时间" width="150" align="center"/>
                <el-table-column property="updatedAt" label="更新时间" width="150" align="center"/>
                <el-table-column property="locked" label="状态" align="center">
                    <template #default="scope">
                    <el-tag :type="getLockedDict(scope.row.locked).type"> {{getLockedDict(scope.row.locked).label}} </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                <template #default="scope">
                    <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row.id)">修改</el-button>
                    <el-popconfirm title="确定删除该用户? " @confirm="handleDelete(scope.row.id)">
                    <template #reference>
                    <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
                    </template>
                </el-popconfirm>
                </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="user-list-footer">
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
import { getUserList } from '@/api/user';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

export default {    
    setup(){

        const store = useStore();
        
        const router = useRouter();

        const formSize = ref('large');

        const getLockedDict = (locked) => {
            let label = '';
            let type = '';
            if(locked){
                label = '锁定';
                type = 'warning';
            }else{
                label = '正常';
                type = 'success';
            }
            return {"label": label,"type": type};
        };


        const userLocked = [
            {
                locked: true,
                label: '锁定',
            },
            {
                locked: false,
                label: '正常',
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
            username: '',
            nickname: '', 
            phone: '',
            locked: '',
        });


        table.value.isMobile = store.state.app.isMobile;


        //获取用户列表
        const listUsers = () => {
            table.value.loading = true,
            getUserList({
                page: table.value.page,
                pageSize: table.value.pageSize,
            }).then((res: any) => {
                console.log(res);
                table.value.data = res.data.list;
                table.value.total = res.data.size;
                table.value.loading = false;
            });
        }

        listUsers();

        const handleSizeChange = (pageSize: number) => {
            table.value.pageSize = pageSize;
            listUsers();
        }

        const handleCurrentChange = (page: number) => {
            table.value.page = page;
            listUsers();
        }

        const searchUsers = () => {
            table.value.loading = true,
            getUserList({
                page: table.value.page,
                pageSize: table.value.pageSize,
                username: searchParams.value.username,
                nickname: searchParams.value.nickname,
                phone: searchParams.value.phone,
                locked: searchParams.value.locked,

            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.size;
                table.value.loading = false;
            });
        }

        const handleDelete = (id) => {
            // deleteArticle(id).then((res) => {
            // if(res.data) {
            //   ElMessage.success("删除成功!");
            //   listArticles();
            // }
            // });
        }

        const handleEditClick = (id) => {
            router.push('/user/' + id);
        }
        return {
            table,
            handleSizeChange,
            handleCurrentChange,
            searchParams,
            searchUsers,
            formSize,
            userLocked,
            getLockedDict,
            handleDelete,
            handleEditClick
            };
        }
}
</script>

<style lang="scss" scoped>
.user-list-header{
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
.user-list-footer{
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.user-table{
    width: 100%;
    height: 50rem;
    overflow: auto;
}

@media screen and (max-width: 480px){
    .user-table{
        height: 28rem;
        overflow: auto;
    }
}

</style>