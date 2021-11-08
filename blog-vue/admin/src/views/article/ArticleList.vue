<template>
    <div class="article-list-container">
        <div class="article-list-header">
            <div class="line"></div>
            <span>文章管理</span>
        </div>
        <div class="article-list-body">
            <el-table
                ref="multipleTable"
                :data="table.data"
                style="width: 100%; height: 400px;overflow: auto;"
                v-loading="table.loading"
            >
                <el-table-column type="selection" width="55" />
                <el-table-column property="id" label="文章编号" width="120" align="center" />
                <el-table-column property="title" label="标题" width="200" align="center"/>
                <el-table-column property="createdAt" label="创建时间" align="center"/>
                <el-table-column property="updatedAt" label="更新时间" align="center"/>
                <el-table-column property="visible" label="是否公开" align="center"/>
                <el-table-column label="操作" align="center">
                <template #default="scope">
                    <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">编辑</el-button>
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

export default {    
    setup(){
        const table:any = ref({
          page: 1,        // 当前在第几页
          pageSize: 10,   // 一页显示多少
          total: 0,       // 数据总量
          loading: true,  // 加载中
          data: [],       // 表格数据
          isMobile: false,// 表格是否移动端
      });

      return {
          table,
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
</style>