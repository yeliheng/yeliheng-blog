<template>
  <div>
    <div class="draft-list-container">
      <div class="draft-list-header">
        <div class="line"></div>
        <span>草稿箱</span>
      </div>
      <div class="draft-list-body">
        <el-table
            class="draft-table"
            ref="multipleTable"
            :data="table.data"
            v-loading="table.loading"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column property="id" label="草稿编号" width="100" align="center" sortable>
            <template #default="scope">
              <i v-if="scope.row.top == '1'" class="fa fa-thumb-tack" style="color: #579ff8"> </i>
              {{scope.row.id}}
            </template>
          </el-table-column>
          <el-table-column property="title" label="标题" width="220" align="center" sortable/>
          <el-table-column property="summary" label="摘要" width="220" align="center" sortable/>
          <el-table-column property="createdAt" label="创建时间" width="150" align="center" sortable />
          <el-table-column property="updatedAt" label="更新时间" width="150" align="center" sortable />
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row.id)">继续编辑</el-button>
              <el-popconfirm title="确定删除该草稿? " @confirm="handleDelete(scope.row.id)">
                <template #reference>
                  <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="draft-list-footer">
        <!-- 分页 -->
        <el-pagination
            class="pagination-nav"
            v-model:currentPage="table.page"
            :page-sizes="[30, 50, 80]"
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
  </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import {ElMessage} from 'element-plus';
import {deleteDraft, getDraftList} from "@/api/draft";

export default {
  setup(){

    const store = useStore();

    const router = useRouter();

    const formSize = ref('large');

    //适配移动端
    if(store.state.app.isMobile){
      formSize.value = 'mini';
    }else{
      formSize.value = 'large';
    }

    const table:any = ref({
      page: 1,        // 当前在第几页
      pageSize: 30,   // 一页显示多少
      total: 0,       // 数据总量
      loading: false,  // 加载中
      data: [],       // 表格数据
      isMobile: false,// 表格是否移动端
    });

    table.value.isMobile = store.state.app.isMobile;

    const listDraft = () => {
      table.value.loading = true,
          getDraftList({
            page: table.value.page,
            pageSize: table.value.pageSize,
          }).then((res: any) => {
            table.value.data = res.data.list;
            table.value.total = res.data.total;
            table.value.loading = false;
          });
    }

    listDraft();

    const handleSizeChange = (pageSize: number) => {
      table.value.pageSize = pageSize;
      listDraft();
    }

    const handleCurrentChange = (page: number) => {
      table.value.page = page;
      listDraft();
    }

    const handleDelete = (id) => {
      deleteDraft(id).then((res) => {
        if(res.data) {
          ElMessage.success("删除成功!");
          listDraft();
        }
      });
    }

    const handleEditClick = (id) => {
      router.push('/draft/' + id);
    }

    return {
      table,
      handleSizeChange,
      handleCurrentChange,
      formSize,
      handleDelete,
      handleEditClick,
    };
  }
}
</script>

<style lang="scss" scoped>
.draft-list-header{
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
.draft-list-footer{
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.draft-table{
  width: 100%;
  overflow: auto;
}

@media screen and (max-width: 480px){
  .draft-table{
    height: 28rem;
    overflow: auto;
  }
}

</style>