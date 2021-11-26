<template>
<meta name="viewport" content="width=device-width, initial-scale=1, role-scalable=0" />
    <div class="role-list-container">
        <div class="role-list-header">
            <div class="line"></div>
            <span>角色管理</span>
        </div>
        <div class="role-list-body">
            <el-form :inline="true" :model="searchParams" class="search-role" :size="formSize">
                <el-form-item label="角色名称: ">
                    <el-input v-model="searchParams.roleName" placeholder="角色名称关键字"></el-input>
                </el-form-item>
                <el-form-item label="角色字符: ">
                    <el-input v-model="searchParams.roleChar" placeholder="角色字符关键字"></el-input>
                </el-form-item>
                <el-form-item label="角色状态: ">
                    <el-select v-model="searchParams.locked" clearable>
                        <el-option
                        v-for="item in roleLocked"
                        :key="item.locked"
                        :label="item.label"
                        :value="item.locked"
                        >
                        </el-option>
                    </el-select>  
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchRoles">搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="roleFormVisible = true;dialogTitle = '添加角色';">新增角色</el-button>
                </el-form-item>
            </el-form>
                
            
            <el-table
                class="role-table"
                ref="multipleTable"
                :data="table.data"
                v-loading="table.loading"
            >
                <el-table-column type="selection" width="55" />
                <el-table-column property="id" label="角色编号" width="80" align="center" />
                <el-table-column property="roleName" label="角色名称" width="220" align="center"/>
                <el-table-column property="roleChar" label="角色字符" width="120" align="center"/>
                <el-table-column property="locked" label="角色状态" align="center">
                    <template #default="scope">
                    <el-tag :type="getLockedDict(scope.row.locked).type"> {{getLockedDict(scope.row.locked).label}} </el-tag>
                    </template>
                </el-table-column>
                <el-table-column property="createdAt" label="创建时间" width="150" align="center"/>
                <el-table-column property="updatedAt" label="更新时间" width="150" align="center"/>

                <el-table-column label="操作" align="center">
                <template #default="scope">
                    <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">修改</el-button>
                    <el-popconfirm title="确定删除该角色? " @confirm="handleDelete(scope.row.id)">
                    <template #reference>
                    <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
                    </template>
                </el-popconfirm>
                </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="role-list-footer">
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

        
        <!-- 添加或修改角色 -->
        <el-dialog v-model="roleFormVisible" :title="dialogTitle" :close-on-click-modal="false" width="25rem">
            <el-form ref="validate" :model="roleForm" label-position="left" :rules="formRules">
                <el-form-item label="角色名称: " label-width="100px" prop="roleName" required>
                    <el-input autocomplete="off" v-model="roleForm.roleName"></el-input>
                </el-form-item>
                <el-form-item label="角色字符: " label-width="100px" prop="roleChar" required>
                    <el-input autocomplete="off" v-model="roleForm.roleChar"></el-input>
                </el-form-item>   
                <el-form-item label="角色权限: " label-width="100px">
                    <el-tree
                        :data="menuOptions"
                        show-checkbox
                        ref="menuIds"
                        node-key="id"
                        empty-text="菜单加载中"
                    ></el-tree>
                </el-form-item>          
                <el-form-item label="锁定角色: " label-width="100px">
                    <el-switch v-model="roleForm.locked"/>
                </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button @click="roleFormVisible = false">取消</el-button>
                <el-button type="primary" @click="submitForm()" :loading="loading"
                >确定</el-button
                >
            </span>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { deleteRole, getRoleList, getRoles, addRole, getSelectMenuTree, updateRole } from '@/api/role';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';

export default {
    setup(){
        const roleFormVisible = ref(false);

        const validate: any = ref(null);

        const loading = ref(false);

        const store = useStore();

        const formSize = ref('large');

        const dialogTitle = ref('');

        const formRules = {
            roleName: [
            {
                required: true,
                message: '请输入角色名',
                trigger: 'blur',
            }
            ],
            roleChar: [
            {
                required: true,
                message: '请输入角色字符',
                trigger: 'blur',
            }
            ],
        };


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


        const roleLocked = [
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
            roleName: '',
            roleChar: '', 
            locked: '',
        });

        const roleForm: any = ref({
          id: 0,
          roleName: '',
          roleChar: '',
          menuIds: [],
          locked: 0,
        });

        const menuOptions = ref();

        const menuIds: any = ref([]);

        table.value.isMobile = store.state.app.isMobile;


        //获取角色列表
        const listRoles = () => {
            table.value.loading = true,
            getRoleList({
                page: table.value.page,
                pageSize: table.value.pageSize,
            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.size;
                table.value.loading = false;
            });
        }

        //获取菜单树
        getSelectMenuTree().then((res) => {
            menuOptions.value = res.data;
        });

        listRoles();

        const roles = ref([]);

        const handleSizeChange = (pageSize: number) => {
            table.value.pageSize = pageSize;
            listRoles();
        }

        const handleCurrentChange = (page: number) => {
            table.value.page = page;
            listRoles();
        }

        const searchRoles = () => {
            table.value.loading = true,
            getRoleList({
                page: table.value.page,
                pageSize: table.value.pageSize,
                roleName: searchParams.value.roleName,
                roleChar: searchParams.value.roleChar,
                locked: searchParams.value.locked,
            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.size;
                table.value.loading = false;
            });
        }

        const handleDelete = (id) => {
            deleteRole(id).then((res: any) => {
                if(!res.errCode) {
                    ElMessage.success("删除成功!");
                    listRoles();
                }else{
                    ElMessage.error(res.detail);
                }
            });
        }

        const handleEditClick = (row) => {
            dialogTitle.value = '修改角色';
            roleForm.value = row;
            roleFormVisible.value = true;

        }

        const submitForm = () => {
            roleForm.value.menuIds = menuIds.value.getCheckedKeys();
            if(roleForm.value.id) {
                loading.value = true;
                updateRole(roleForm.value).then((res: any) => {
                    if(!res.errCode){
                        ElMessage.success('更新角色成功!');
                        roleFormVisible.value = false;
                        roleForm.value = {};
                        menuIds.value.setCheckedKeys([]);
                        listRoles();
                    }else{
                        ElMessage.error(res.detail);
                    }
                    loading.value = false;
                }).catch(() => {
                    loading.value = false;
                });
            }else{
                roleForm.value.menuIds = menuIds.value.getCheckedKeys();
                validate.value.validate((valid) => {
                if(valid) {
                    loading.value = true;
                    addRole(roleForm.value).then((res: any) => {
                        if(!res.errCode){
                            ElMessage.success('新建角色成功!');
                            roleFormVisible.value = false;
                            roleForm.value = {};
                            menuIds.value.setCheckedKeys([]);
                            listRoles();
                        }else{
                            ElMessage.error(res.detail);
                        }
                        loading.value = false;
                    }).catch(() => {
                        loading.value = false;
                    });
                }
            });
            }
            
        }

        
        return {
            table,
            handleSizeChange,
            handleCurrentChange,
            searchParams,
            searchRoles,
            formSize,
            roleLocked,
            getLockedDict,
            handleDelete,
            handleEditClick,
            submitForm,
            roleFormVisible,
            roleForm,
            roles,
            loading,
            formRules,
            validate,
            menuOptions,
            menuIds,
            dialogTitle,
            };
        }
}
</script>

<style lang="scss" scoped>
.role-list-header {
    display: flex;
    margin-bottom: 1rem;
    >span {
        font-size: 20px;
        margin-left: 0.5rem;
    }

}
.line {
    width: 0.5rem;
    height: 2rem;
    background: #666666;
}
.role-list-footer {
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.role-table {
    width: 100%;
    height: 50rem;
    overflow: auto;
}

@media screen and (max-width: 480px){
    .role-table{
        height: 28rem;
        overflow: auto;
    }
}

</style>