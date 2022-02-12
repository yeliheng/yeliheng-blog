<template>
  <div class="log-container">
    <div class="log-header">
      <div class="line"></div>
      <span>操作日志管理</span>
    </div>
    <div class="log-body">


      <el-form :inline="true" :model="searchParams" class="search-article" :size="formSize">
        <el-form-item label="模块名称: ">
          <el-input v-model="searchParams.moduleName" placeholder="模块名称关键字"></el-input>
        </el-form-item>
        <el-form-item label="操作者用户名: ">
          <el-input v-model="searchParams.username" placeholder="操作者用户名"></el-input>
        </el-form-item>
        <el-form-item label="操作状态: ">
          <el-select v-model="searchParams.status" clearable>
            <el-option
                v-for="item in statusDict"
                :key="item.status"
                :label="item.label"
                :value="item.status"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchOperateLogs">搜索</el-button>
        </el-form-item>
      </el-form>
      <div class="log-btns">
        <el-button type="danger" @click="handleDelete" style="margin-right:1rem" :disabled="!canBeDelete">删除选中</el-button>
        <el-button type="danger" @click="handleClear" style="margin-right:1rem">清空日志</el-button>
      </div>
      <el-table
          ref="multipleTable"
          :data="table.data"
          style="width: 100%;overflow: auto;"
          v-loading="table.loading"
          class="log-table"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="id" label="日志编号" width="120" align="center" sortable/>
        <el-table-column property="username" label="操作用户名" width="120" align="center" sortable/>
        <el-table-column property="moduleName" label="操作信息" width="130" align="center" sortable/>
        <el-table-column property="ip" label="IP地址" width="140" align="center" sortable/>
        <el-table-column property="url" label="Url" width="180" align="center" sortable/>
        <el-table-column property="method" label="请求方法" width="100" align="center" sortable/>
        <el-table-column property="status" label="状态" width="120" align="center" sortable>
          <template #default="scope">
            <el-tag :type="getStatusDict(scope.row.status).type"> {{getStatusDict(scope.row.status).label}} </el-tag>
          </template>
        </el-table-column>

        <el-table-column property="createdAt" label="操作时间" width="200"  align="center" sortable/>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="text" size="mini" icon="fa fa-eye" @click="handleDetailClick(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="log-footer">
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
    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详情" v-model="visible" width="700px" append-to-body>
      <el-form :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作模块：">{{ form.moduleName }}</el-form-item>
            <el-form-item
                label="操作人："
            >{{ form.username }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求接口：">{{ form.url }}</el-form-item>
            <el-form-item label="请求方法：">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作函数：">{{ form.function }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">{{ form.param }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="返回结果：">{{ form.result }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <div v-if="form.status == 0">失败</div>
              <div v-else-if="form.status == 1">成功</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作时间：">{{ form.createdAt }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="form.status == 0">{{ form.errorDetail }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>

    </el-dialog>
  </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { useStore } from 'vuex';
import {ElMessage, ElMessageBox} from 'element-plus';
import {clearOperateLogs, deleteOperateLogs, getOperateLogs} from "@/api/operateLog";
import {getArticlesAdmin} from "@/api/article";
export default {
  setup(){
    const store = useStore();

    const formSize = ref('large');

    //适配移动端
    if(store.state.app.isMobile){
      formSize.value = 'mini';
    }else{
      formSize.value = 'large';
    }

    const statusDict = [
      {
        status: 1,
        label: '成功',
      },
      {
        status: 0,
        label: '失败',
      }
    ];

    let resetSearch = false;

    const table:any = ref({
      page: 1,        // 当前在第几页
      pageSize: 30,   // 一页显示多少
      total: 0,       // 数据总量
      loading: true,  // 加载中
      data: [],       // 表格数据
      isMobile: false,// 表格是否移动端
    });

    const canBeDelete = ref(false);

    const visible = ref(false);

    const searchText = ref("");

    const searchParams = ref(
        {
          moduleName: '',
          username: '',
          status: '',
        }
    );

    table.value.isMobile = store.state.app.isMobile;

    const listLogs = () => {
      getOperateLogs({page: table.value.page,pageSize: table.value.pageSize}).then((res: any) => {
        table.value.total = res.data.total;
        table.value.data = res.data.list;
        table.value.loading = false;
      });
    };

    listLogs();

    const handleSizeChange = (pageSize) => {
      table.value.loading = true;
      table.value.pageSize = pageSize;
      listLogs();
    }

    const handleCurrentChange = (page) => {
      table.value.loading = true;
      table.value.page = page;
      listLogs();
    }

    const searchOperateLogs = () => {
      table.value.loading = true,
          getOperateLogs({
            page: table.value.page,
            pageSize: table.value.pageSize,
            moduleName: searchParams.value.moduleName,
            username: searchParams.value.username,
            status: searchParams.value.status,

          }).then((res: any) => {
            table.value.data = res.data.list;
            table.value.total = res.data.total;
            table.value.loading = false;
          });
    }

    const getStatusDict = (status) => {
      let label = '';
      let type = '';
      if(!status){
        label = '失败';
        type = 'warning';
      }else{
        label = '成功';
        type = 'success';
      }
      return {"label": label,"type": type};
    };

    const form = ref();


    const handleDetailClick = (row: any) => {
        visible.value = true;
        form.value = row;
    }

    const ids = ref();

    const handleSelectionChange = (selection: any) => {
      ids.value = selection.map(item => item.id) + "";
      canBeDelete.value = selection.length;
    }

    const handleDelete = () => {
      ElMessageBox.confirm('是否删除选中日志？', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        deleteOperateLogs(ids.value).then((res: any) => {
          if(!res.errCode) {
            ElMessage.success("删除成功！");
            listLogs();
          }else {
            ElMessage.error(res.detail);
          }
        });
      });

    }

    const handleClear = () => {
      ElMessageBox.confirm('是否清空所有操作日志？', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        clearOperateLogs().then((res: any) => {
          if(!res.errCode) {
            ElMessage.success("清空日志成功！");
            listLogs();
          }else {
            ElMessage.error(res.detail);
          }
        });
      });
    }


    return {
      table,
      handleSizeChange,
      handleCurrentChange,
      visible,
      form,
      listLogs,
      handleDetailClick,
      searchOperateLogs,
      searchText,
      getStatusDict,
      handleSelectionChange,
      ids,
      handleDelete,
      handleClear,
      canBeDelete,
      searchParams,
      statusDict,
      formSize,

    }
  }
}
</script>

<style lang="scss" scoped>

.log-header{
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
.log-footer{
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}

.log-btns{

  margin-bottom: 1rem;
}

@media screen and (max-width: 480px){
  .log-table{
    height: 36rem;
    overflow: auto;
  }
}
</style>