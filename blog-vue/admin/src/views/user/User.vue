<template>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
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
              <el-form-item label="电子邮箱: ">
                <el-input v-model="searchParams.email" placeholder="电子邮箱"></el-input>
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
                <el-form-item>
                    <el-button type="primary" @click="userForm = {};userFormVisible = true;">添加用户</el-button>
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
                        {{scope.row.nickname}}
                    </template>
                </el-table-column>
                <el-table-column property="phone" label="手机号" width="120" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.phone == null"> - </span>
                        {{scope.row.phone}}
                    </template>
                </el-table-column>
              <el-table-column property="email" label="电子邮箱" width="160" align="center">
                <template #default="scope">
                  <span v-if="scope.row.email == null"> - </span>
                  {{scope.row.email}}
                </template>
              </el-table-column>
              <el-table-column property="roleList" label="用户角色" width="170" align="center">
                    <template #default="scope">
                        <span v-if="scope.row.id == 1"> <el-tag type="success">超级管理员</el-tag></span>
                        <span v-if="scope.row.roleList == '' && scope.row.id != 1"> - </span>
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
                <el-table-column label="操作" align="center" >
                <template #default="scope">
                    <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">修改</el-button>
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

        
        <!-- 添加用户 -->
        <el-dialog v-model="userFormVisible" title="添加用户" :close-on-click-modal="false" width="25rem">
            <el-form ref="validate" :model="userForm" label-position="left" :rules="formRules">
            <el-form-item label="用户名: " label-width="80px" prop="username" required>
                <el-input autocomplete="off" v-model="userForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码: " label-width="80px" prop="password" required>
                <el-input show-password autocomplete="off" v-model="userForm.password"></el-input>
            </el-form-item>
            <el-form-item label="昵称: " label-width="80px" prop="nickname">
                <el-input autocomplete="off" v-model="userForm.nickname"></el-input>
            </el-form-item>            
            <el-form-item label="手机号: " label-width="80px" prop="phone">
                <el-input autocomplete="off" v-model="userForm.phone"></el-input>
            </el-form-item>
              <el-form-item label="电子邮箱: " label-width="80px" prop="email">
                <el-input autocomplete="off" v-model="userForm.email"></el-input>
              </el-form-item>
            <el-form-item label="角色: " label-width="80px">
                <el-select placeholder="请选择一个角色" v-model="userForm.roles" multiple>
                    <el-option
                        v-for="item in roles"
                        :key="item.id"
                        :label="item.roleName"
                        :value="item.id"
                        >
                        </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="锁定用户: " label-width="80px">
                <el-switch v-model="userForm.locked"/>
            </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button @click="userFormVisible = false">取消</el-button>
                <el-button type="primary" @click="handleAddUser()" :loading="loading"
                >确定</el-button
                >
            </span>
            </template>
        </el-dialog>

        <!-- 编辑用户 -->
        <el-dialog v-model="userEditFormVisible" title="编辑用户"  :close-on-click-modal="false" width="25rem">
            <el-form ref="validate" :model="userForm" label-position="left" :rules="editFormRules">
                <el-form-item label="用户名: " label-width="80px" prop="username" required>
                    <el-input autocomplete="off" v-model="userForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码: " label-width="80px" prop="password">
                    <el-input show-password placeholder="密码未更改" autocomplete="off" v-model="userForm.password"></el-input>
                </el-form-item>
                <el-form-item label="昵称: " label-width="80px" prop="nickname">
                    <el-input autocomplete="off" v-model="userForm.nickname"></el-input>
                </el-form-item>            
                <el-form-item label="手机号: " label-width="80px" prop="phone">
                    <el-input autocomplete="off" v-model="userForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱: " label-width="80px" prop="email">
                  <el-input autocomplete="off" v-model="userForm.email"></el-input>
                </el-form-item>
                <el-form-item label="角色: " label-width="80px">
                    <el-select placeholder="请选择一个角色" v-model="userForm.roles" multiple>
                        <el-option
                            v-for="item in roles"
                            :key="item.id"
                            :label="item.roleName"
                            :value="item.id"
                            >
                            </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="锁定用户: " label-width="80px">
                    <el-switch v-model="userForm.locked"/>
                </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button @click="userEditFormVisible = false">取消</el-button>
                <el-button type="primary" @click="handleEditUser()" :loading="loading"
                >确定</el-button
                >
            </span>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { deleteUser, getUserList, getRoles, addUser, updateUser } from '@/api/user';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';

export default {
    setup(){
        const userFormVisible = ref(false);

        const validate: any = ref(null);

        const userEditFormVisible = ref(false);

        const loading = ref(false);

        const store = useStore();

        const formSize = ref('large');

        const formRules = {
            username: [
              {
                  required: true,
                  message: '请输入用户名',
                  trigger: 'blur',
              },
              {
                  min: 4,
                  max: 20,
                  message: '用户名长度必须在4-20个字符之间',
                  trigger: 'blur',
              }
            ],
            password: [
            {
                required: true,
                message: '请输入密码',
                trigger: 'blur',
            },
            {
                min: 6,
                max: 18,
                message: '密码长度必须在6-18个字符之间',
                trigger: 'blur',
            },
            ],
            nickname: [
              {
                max: 30,
                message: '昵称不能超过30个字符!',
                trigger: 'blur',
              }
            ],
            phone: [
              {
                pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                message: "手机号码格式不正确!",
                trigger: "blur"
              }
            ],
            email: [
              {
                max: 30,
                message: "邮箱不能超过30个字符",
                trigger: ["blur", "change"]
              },
              {
                type: "email",
                message: "邮箱格式不正确",
                trigger: ["blur", "change"]
              }
            ],
        };

        const editFormRules = {
          username: [
            {
                required: true,
                message: '请输入用户名',
                trigger: 'blur',
            },
            {
              min: 4,
              max: 20,
              message: '用户名长度必须在4-20个字符之间',
              trigger: 'blur',
            }
          ],
          password: [
            {
                min: 6,
                max: 18,
                message: '密码长度必须在6-18个字符之间',
                trigger: 'blur',
            }
          ],
          nickname: [
            {
              max: 30,
              message: '昵称不能超过30个字符!',
              trigger: 'blur',
            }
          ],
          phone: [
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: "请输入正确的手机号码",
              trigger: "blur"
            }
          ],
          email: [
            {
              max: 30,
              message: "邮箱不能超过30个字符!",
              trigger: ["blur", "change"]
            },
            {
              type: "email",
              message: "邮箱格式不正确",
              trigger: ["blur", "change"]
            }
          ]


        }

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
            email: '',
            locked: '',
        });

        const userForm: any = ref({
          id: null,
          username: '',
          password: '',
          nickname: '',
          phone: '',
          email: '',
          roles: [],
          locked: 0
        });

        table.value.isMobile = store.state.app.isMobile;


        //获取用户列表
        const listUsers = () => {
            table.value.loading = true,
            getUserList({
                page: table.value.page,
                pageSize: table.value.pageSize,
            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.total;
                table.value.loading = false;
            });
        }

        listUsers();

        const roles = ref([]);

        //获取角色列表
        getRoles().then((res: any) => {
            roles.value = res.data;
        });

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
                email: searchParams.value.email,
                locked: searchParams.value.locked,

            }).then((res: any) => {
                table.value.data = res.data.list;
                table.value.total = res.data.total;
                table.value.loading = false;
            });
        }

        const handleDelete = (id) => {
            deleteUser(id).then((res: any) => {
                if(!res.errCode) {
                    ElMessage.success("删除成功!");
                    listUsers();
                }else{
                    ElMessage.error(res.detail);
                }
            });
        }

        const handleEditClick = (row) => {
            userForm.value = {};
            const formData = JSON.parse(JSON.stringify(row)); //消除row的响应性
            //获取角色id
            let roleIds = [];
            userForm.value = formData;
            row.roleList.forEach(item => {
                roleIds.push(item.id);
                userForm.value.roles = roleIds;
            });
            userEditFormVisible.value = true;

        }


        const handleEditUser = () => {
            validate.value.validate((valid) => {
                if(valid){
                    loading.value = true;
                    updateUser(userForm.value).then((res: any) => {
                        if(!res.errCode) {
                            ElMessage.success('更新用户成功!');
                            userEditFormVisible.value = false;
                            listUsers();
                        } else {
                            ElMessage.error(res.detail);
                        }      
                        loading.value = false;
                    }).catch(() => {
                        loading.value = false;
                    });
                }
            });

        }

        const handleAddUser = () => {
            validate.value.validate((valid) => {
                if(valid) {
                    loading.value = true;
                    addUser(userForm.value).then((res: any) => {
                        if(!res.errCode){
                            ElMessage.success('新建用户成功!');
                            userFormVisible.value = false;
                            listUsers();
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
            handleEditClick,
            handleAddUser,
            userFormVisible,
            userForm,
            roles,
            loading,
            formRules,
            userEditFormVisible,
            handleEditUser,
            editFormRules,
            validate,
            };
        }
}
const validate = ref(null);
</script>

<style lang="scss" scoped>
.user-list-header {
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
.user-list-footer {
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.user-table {
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