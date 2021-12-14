<template>
  <div class="category-container">
    <div class="category-header">
      <div class="line"></div>
      <span>分类管理</span>
    </div>
    <div class="category-body">
      <div class="category-btns">
         <el-button type="primary" @click="handleAddClick" style="margin-right:1rem">新增分类</el-button>
         <el-input
          v-model="searchText"
          prefix-icon="el-icon-search"
          placeholder="输入分类名"
          style="width:15rem;margin-start: auto;"
          @keyup.enter="searchCategories"
        />
        <el-button
          type="primary"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchCategories"
        >
          搜索
        </el-button>
      </div>
      <el-table
      ref="multipleTable"
      :data="table.data"
      style="width: 100%; height: 400px;overflow: auto;"
      v-loading="table.loading"
      
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="id" label="分类编号" width="120" align="center" />
        <el-table-column property="categoryName" label="分类名称" width="200" align="center"/>
        <el-table-column property="createdAt" label="创建时间" align="center"/>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除该分类? " @confirm="handleDelCategory(scope.row.id)">
            <template #reference>
              <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
            </template>
          </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="category-footer">
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
    <!-- 新增分类对话框 -->
    <el-dialog
      title="新增分类"
      v-model="dialogFormVisible"
      width="25rem"
      >
      <el-form :model="categoryForm">
          <el-form-item label="分类名称" required>
            <el-input v-model="categoryForm.categoryName"/>
          </el-form-item>
      </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddCategory" :loading="btnLoading"
          >确认</el-button
        >
      </span>
    </template>
    </el-dialog>
     <!-- 编辑分类对话框 -->
    <el-dialog
      title="编辑分类"
      v-model="editDialogFormVisible"
      width="25rem"
      >
      <el-form :model="categoryForm">
          <el-form-item label="分类名称" required>
            <el-input v-model="categoryForm.categoryName"/>
          </el-form-item>
      </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEditCategory" :loading="btnLoading"
          >确认</el-button
        >
      </span>
    </template>
    </el-dialog>
    
  </div>
</template>

<script lang="ts">
import { addCategory, deleteCategory, getCategories, updateCategory } from '@/api/category';
import { ref } from 'vue';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
export default {
    setup(){
      const store = useStore();

      let resetSearch = false;

      const table:any = ref({
          page: 1,        // 当前在第几页
          pageSize: 10,   // 一页显示多少
          total: 0,       // 数据总量
          loading: true,  // 加载中
          data: [],       // 表格数据
          isMobile: false,// 表格是否移动端
      });
      
      const btnLoading = ref(false);

      const categoryForm = ref({
          categoryName: '',
          id: 0,
      });

      const dialogFormVisible = ref(false);

      const editDialogFormVisible = ref(false);

      const searchText = ref("");

      table.value.isMobile = store.state.app.isMobile;
      
     getCategories({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
        table.value.total = res.data.total;
        table.value.data = res.data.list;
        table.value.loading = false;
    });
    
    const handleSizeChange = (pageSize) => {
        table.value.loading = true;
        table.value.pageSize = pageSize;
        getCategories({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
          table.value.total = res.data.total;
          table.value.data = res.data.list;
          table.value.loading = false;
        });
    }

    const handleCurrentChange = (page) => {
        table.value.loading = true;
        table.value.page = page;
        getCategories({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
          table.value.total = res.data.total;
          table.value.data = res.data.list;
          table.value.loading = false;
        });
    }

    const handleAddClick = () => {
        categoryForm.value.categoryName = "";
        dialogFormVisible.value = true;
    }

    const handleEditClick = (row) => {
        categoryForm.value.categoryName = row.categoryName;
        categoryForm.value.id = row.id;
        editDialogFormVisible.value = true;
    }

    const handleAddCategory = () => {
      if(categoryForm.value.categoryName.trim() == ""){
        ElMessage({
          message: '分类名称不能为空!',
          type: 'error',
        });
        return;
      }else{
        dialogFormVisible.value = false;
        btnLoading.value = true;
        addCategory(categoryForm.value.categoryName).then((res) => {
          btnLoading.value = false;
          if(res.data){
            table.value.loading = true;
            getCategories({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
              table.value.total = res.data.total;
              table.value.data = res.data.list;
              table.value.loading = false;
            });
            ElMessage({
              message: '添加分类成功!',
              type: 'success',
            });

          }
        })

      }
    }


    const handleEditCategory = () => {
      if(categoryForm.value.categoryName.trim() == ""){
        ElMessage({
          message: '分类名称不能为空!',
          type: 'error',
        });
        return;
      }else{
        editDialogFormVisible.value = false;
        btnLoading.value = true;
        updateCategory(categoryForm.value.id,categoryForm.value.categoryName).then((res) => {
          btnLoading.value = false;
          if(res.data){
            table.value.loading = true;
            getCategories({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
              table.value.total = res.data.total;
              table.value.data = res.data.list;
              table.value.loading = false;
            });
            ElMessage({
              message: '修改分类成功!',
              type: 'success',
            });
            categoryForm.value.categoryName = "";
          }
          
        });
      }
    }

    const handleDelCategory = (id) => {
        deleteCategory(id).then((res: any) => {
            if(!res.errCode) {
              ElMessage.success("删除成功!");
              table.value.loading = true;
              getCategories({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
                table.value.total = res.data.total;
                table.value.data = res.data.list;
                table.value.loading = false;
              });
            }else{
              ElMessage.error(res.detail);
            }

        });
    }

    const searchCategories = () => {
        if(searchText.value.trim() != ""){
          resetSearch = false;
          table.value.loading = true;
          getCategories({page: table.value.page,pageSize: table.value.pageSize,categoryName: searchText.value}).then((res: any) => {
          table.value.total = res.data.total;
          table.value.data = res.data.list;
          table.value.loading = false;
        });

        }else if(!resetSearch){
          table.value.loading = true;
          getCategories({page: table.value.page,pageSize: table.value.pageSize,categoryName: searchText.value}).then((res: any) => {
            table.value.total = res.data.total;
            table.value.data = res.data.list;
            table.value.loading = false;
            });
            resetSearch = true;
        }

    }

      return {
        table,
        handleSizeChange,
        handleCurrentChange,
        handleAddClick,
        dialogFormVisible,
        editDialogFormVisible,
        categoryForm,
        handleAddCategory,
        handleDelCategory,
        handleEditCategory,
        btnLoading,
        handleEditClick,
        searchCategories,
        searchText,
      }
    }
}
</script>

<style lang="scss" scoped>

.category-header{
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
.category-footer{
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}

.category-btns{
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
}
</style>