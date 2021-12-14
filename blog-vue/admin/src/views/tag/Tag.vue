<template>
  <div class="tag-container">
    <div class="tag-header">
      <div class="line"></div>
      <span>标签管理</span>
    </div>
    <div class="tag-body">
      <div class="tag-btns">
         <el-button type="primary" @click="handleAddClick" style="margin-right:1rem">新增标签</el-button>
         <el-input
          v-model="searchText"
          prefix-icon="el-icon-search"
          placeholder="输入标签名"
          style="width:15rem;margin-start: auto;"
          @keyup.enter="searchTags"
        />
        <el-button
          type="primary"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchTags"
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
        <el-table-column property="id" label="标签编号" width="120" align="center" />
        <el-table-column property="tagName" label="标签名称" width="200" align="center"/>
        <el-table-column property="createdAt" label="创建时间" align="center"/>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除该标签? " @confirm="handleDelTag(scope.row.id)">
            <template #reference>
              <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
            </template>
          </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="tag-footer">
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
    <!-- 新增标签对话框 -->
    <el-dialog
      title="新增标签"
      v-model="dialogFormVisible"
      width="25rem"
      >
      <el-form :model="tagForm">
          <el-form-item label="标签名称" required>
            <el-input v-model="tagForm.tagName"/>
          </el-form-item>
      </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddTag" :loading="btnLoading"
          >确认</el-button
        >
      </span>
    </template>
    </el-dialog>
     <!-- 编辑标签对话框 -->
    <el-dialog
      title="编辑标签"
      v-model="editDialogFormVisible"
      width="25rem"
      >
      <el-form :model="tagForm">
          <el-form-item label="标签名称" required>
            <el-input v-model="tagForm.tagName"/>
          </el-form-item>
      </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEditTag" :loading="btnLoading"
          >确认</el-button
        >
      </span>
    </template>
    </el-dialog>
    
  </div>
</template>

<script lang="ts">
import { addTag, deleteTag, getTags, updateTag } from '@/api/tag';
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

      const tagForm = ref({
          tagName: '',
          id: 0,
      });

      const dialogFormVisible = ref(false);

      const editDialogFormVisible = ref(false);

      const searchText = ref("");

      table.value.isMobile = store.state.app.isMobile;
      
     getTags({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
        table.value.total = res.data.total;
        table.value.data = res.data.list;
        table.value.loading = false;
    });
    
    const handleSizeChange = (pageSize) => {
        table.value.loading = true;
        table.value.pageSize = pageSize;
        getTags({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
          table.value.total = res.data.total;
          table.value.data = res.data.list;
          table.value.loading = false;
        });
    }

    const handleCurrentChange = (page) => {
        table.value.loading = true;
        table.value.page = page;
        getTags({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
          table.value.total = res.data.total;
          table.value.data = res.data.list;
          table.value.loading = false;
        });
    }

    const handleAddClick = () => {
        tagForm.value.tagName = "";
        dialogFormVisible.value = true;
    }

    const handleEditClick = (row) => {
        tagForm.value.tagName = row.tagName;
        tagForm.value.id = row.id;
        editDialogFormVisible.value = true;
    }

    const handleAddTag = () => {
      if(tagForm.value.tagName.trim() == ""){
        ElMessage({
          message: '标签名称不能为空!',
          type: 'error',
        });
        return;
      }else{
        dialogFormVisible.value = false;
        btnLoading.value = true;
        addTag(tagForm.value.tagName).then((res) => {
          btnLoading.value = false;
          if(res.data){
            table.value.loading = true;
            getTags({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
              table.value.total = res.data.total;
              table.value.data = res.data.list;
              table.value.loading = false;
            });
            ElMessage({
              message: '添加标签成功!',
              type: 'success',
            });

          }
        })

      }
    }


    const handleEditTag = () => {
      if(tagForm.value.tagName.trim() == ""){
        ElMessage({
          message: '标签名称不能为空!',
          type: 'error',
        });
        return;
      }else{
        editDialogFormVisible.value = false;
        btnLoading.value = true;
        updateTag(tagForm.value.id,tagForm.value.tagName).then((res) => {
          btnLoading.value = false;
          if(res.data){
            table.value.loading = true;
            getTags({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
              table.value.total = res.data.total;
              table.value.data = res.data.list;
              table.value.loading = false;
            });
            ElMessage({
              message: '修改标签成功!',
              type: 'success',
            });

          }
          
        });
      }
    }

    const handleDelTag = (id) => {
        deleteTag(id).then((res: any) => {
            if(!res.errCode) {
              ElMessage.success("删除成功!");
              table.value.loading = true;
              getTags({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
                table.value.total = res.data.total;
                table.value.data = res.data.list;
                table.value.loading = false;
              });
            }else{
              ElMessage.error(res.detail);
            }

        });
    }

    const searchTags = () => {
        if(searchText.value.trim() != ""){
          resetSearch = false;
          table.value.loading = true;
          getTags({page: table.value.page,pageSize: table.value.pageSize,tagName: searchText.value}).then((res: any) => {
          table.value.total = res.data.total;
          table.value.data = res.data.list;
          table.value.loading = false;
        });

        }else if(!resetSearch){
          table.value.loading = true;
          getTags({page: table.value.page,pageSize: table.value.pageSize,tagName: searchText.value}).then((res: any) => {
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
        tagForm,
        handleAddTag,
        handleDelTag,
        handleEditTag,
        btnLoading,
        handleEditClick,
        searchTags,
        searchText,
      }
    }
}
</script>

<style lang="scss" scoped>

.tag-header{
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
.tag-footer{
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}

.tag-btns{
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
}
</style>