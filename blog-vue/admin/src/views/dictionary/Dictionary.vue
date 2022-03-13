<template>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <div class="dictionary-list-container">
    <div class="dictionary-list-header">
      <div class="line"></div>
      <span>字典管理</span>
    </div>
    <div class="dictionary-list-body">
      <el-form :inline="true" :model="searchParams" class="search-dictionary" :size="formSize">
        <el-form-item label="字典名称: ">
          <el-input v-model="searchParams.dictName" placeholder="字典名称关键字"></el-input>
        </el-form-item>
        <el-form-item label="字典代码: ">
          <el-input v-model="searchParams.dictCode" placeholder="字典代码"></el-input>
        </el-form-item>
        <el-form-item label="字典描述: ">
          <el-input v-model="searchParams.description" placeholder="字典描述"></el-input>
        </el-form-item>
        <el-form-item label="字典状态: ">
          <el-select v-model="searchParams.status" clearable>
            <el-option
                v-for="item in dictStatus"
                :key="item.status"
                :label="item.label"
                :value="item.status"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchDictionary">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAddClick()">添加字典</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRefreshCache()">刷新缓存</el-button>
        </el-form-item>
      </el-form>

      <el-table
          class="dictionary-table"
          ref="multipleTable"
          :data="table.data"
          v-loading="table.loading"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="id" label="字典编号" width="100" align="center" sortable/>
        <el-table-column property="dictName" label="字典名称" width="150" align="center" sortable>
          <template #default="scope">
            <div class="dict-name" @click="handleModalOpen(scope);">
              {{scope.row.dictName}}
            </div>
          </template>
        </el-table-column>
        <el-table-column property="dictCode" label="字典代码" width="120" align="center" sortable />
        <el-table-column property="description" label="字典描述" width="220" align="center">
          <template #default="scope">
            <span v-if="scope.row.description == null"> - </span>
            {{scope.row.description}}
          </template>
        </el-table-column>
        <el-table-column property="createdAt" label="创建时间" width="150" align="center" sortable/>
        <el-table-column property="updatedAt" label="更新时间" width="150" align="center" sortable/>
        <el-table-column property="status" label="状态" align="center" sortable>
          <template #default="scope">
            <el-tag :type="getStatusDict(scope.row.status).type"> {{getStatusDict(scope.row.status).label}} </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button type="text" size="mini" icon="fa fa-eye" style="color: #24bbd2" @click="handleModalOpen(scope)">打开</el-button>
            <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">修改</el-button>
            <el-popconfirm title="确定删除该字典? " @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="dictionary-list-footer">
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

    <!-- 添加/修改字典 -->
    <el-dialog v-model="formVisible" :title="formTitle" :close-on-click-modal="false" width="25rem">
      <el-form ref="validate" :model="dictForm" label-position="left" :rules="formRules">
        <el-form-item label="字典名称: " label-width="100px" prop="dictName" required>
          <el-input autocomplete="off" v-model="dictForm.dictName"></el-input>
        </el-form-item>
        <el-form-item label="字典代码: " label-width="100px" prop="dictCode">
          <el-input autocomplete="off" v-model="dictForm.dictCode"></el-input>
        </el-form-item>
        <el-form-item label="字典描述: " label-width="100px" prop="description">
          <el-input autocomplete="off" v-model="dictForm.description" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="锁定字典: " label-width="80px">
          <el-switch v-model="dictForm.status"
                      active-value="0"
                      inactive-value="1"
          />
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
                <el-button @click="formVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit()" :loading="loading">确定</el-button>
            </span>
      </template>
    </el-dialog>

    <!-- 字典数据弹窗 -->
    <el-dialog v-model="dictDataModal" :close-on-click-modal="false" :title="dictDataInfo" width="90%">
      <dictionary-data :code="dictCode"></dictionary-data>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import dictionaryData from "@/views/dictionary/DictionaryData.vue";
import {ref} from 'vue';
import {
  addDictionary,
  deleteDictionary,
  getDictionary,
  refreshDictionaryCache,
  updateDictionary
} from "@/api/dictionary";
import {useStore} from 'vuex';
import {ElMessage, ElMessageBox} from 'element-plus';
import DictionaryData from "@/views/dictionary/DictionaryData.vue";

export default {
  components: {DictionaryData},
  setup(){
    const formVisible = ref(false);

    const validate: any = ref(null);

    const loading = ref(false);

    const store = useStore();

    const formSize = ref('large');

    const formRules = {
      dictName: [
        {
          required: true,
          message: '请输入字典名',
          trigger: 'blur',
        },
        {
          max: 20,
          message: '字典名称不能超过20字符',
          trigger: 'blur',
        }
      ],
      dictCode: [
        {
          required: true,
          message: '请输入字典代码',
          trigger: 'blur',
        },
        {
          max: 20,
          message: '字典代码不能超过20字符',
          trigger: 'blur',
        },
      ],
      description: [
        {
          max: 50,
          message: '描述不能超过50个字符!',
          trigger: 'blur',
        }
      ],
    };

    const formTitle = ref();

    //字典数据弹窗
    const dictDataModal = ref(false);

    const dictDataInfo = ref();

    const dictCode = ref();

    const getStatusDict = (status) => {
      let label = '';
      let type = '';
      if(status == '0'){
        label = '停用';
        type = 'warning';
      }else{
        label = '正常';
        type = 'success';
      }
      return {"label": label,"type": type};
    };

    const dictStatus = [
      {
        status: '0',
        label: '停用',
      },
      {
        status: '1',
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
      dictName: '',
      dictCode: '',
      description: '',
      status: '',
    });

    const dictForm: any = ref({});

    table.value.isMobile = store.state.app.isMobile;

    //获取字典列表
    const listDictionary = () => {
      table.value.loading = true,
          getDictionary({
            page: table.value.page,
            pageSize: table.value.pageSize,
          }).then((res: any) => {
            table.value.data = res.data.list;
            table.value.total = res.data.total;
            table.value.loading = false;
          });
    }

    listDictionary();

    const handleSizeChange = (pageSize: number) => {
      table.value.pageSize = pageSize;
      listDictionary();
    }

    const handleCurrentChange = (page: number) => {
      table.value.page = page;
      listDictionary();
    }

    const searchDictionary = () => {
      table.value.loading = true,
          getDictionary({
            page: table.value.page,
            pageSize: table.value.pageSize,
            dictName: searchParams.value.dictName,
            dictCode: searchParams.value.dictCode,
            description: searchParams.value.description,
            status: searchParams.value.status,
          }).then((res: any) => {
            table.value.data = res.data.list;
            table.value.total = res.data.total;
            table.value.loading = false;
          });
    }

    const handleDelete = (id) => {
      deleteDictionary(id).then((res: any) => {
        if(!res.errCode) {
          ElMessage.success("删除成功!");
          listDictionary();
        }else{
          ElMessage.error(res.detail);
        }
      });
    }

    const handleEditClick = (row) => {
      formTitle.value = '修改字典';
      dictForm.value = JSON.parse(JSON.stringify(row)); //消除row的响应性
      formVisible.value = true;
    }

    const handleAddClick = () => {
      formTitle.value = '添加字典';
      dictForm.value = {};
      formVisible.value = true;
    }

    const handleSubmit = () => {
      //添加字典
      if(!dictForm.value.id) {
        validate.value.validate((valid) => {
          if(valid) {
            loading.value = true;
            addDictionary(dictForm.value).then((res: any) => {
              if(!res.errCode){
                ElMessage.success('新建字典成功!');
                formVisible.value = false;
                listDictionary();
              }else{
                ElMessage.error(res.detail);
              }
              loading.value = false;
            }).catch(() => {
              loading.value = false;
            });
          }
        });
        //修改字典
      }else {
        validate.value.validate((valid) => {
          if(valid){
            loading.value = true;
            updateDictionary(dictForm.value).then((res: any) => {
              if(!res.errCode) {
                ElMessage.success('更新字典成功!');
                formVisible.value = false;
                listDictionary();
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

    }

    //刷新缓存
    const handleRefreshCache = () => {
      ElMessageBox.confirm('确认刷新缓存？', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      }).then(function() {
        refreshDictionaryCache().then((res: any) => {
          if(!res.errCode) {
            ElMessage.success('刷新缓存成功!');
            listDictionary();
          } else {
            ElMessage.error(res.detail);
          }
          loading.value = false;
        }).catch(() => {
          loading.value = false;
        });
      });

    }

    const handleModalOpen = (scope) => {
      dictDataModal.value = true;
      dictDataInfo.value = scope.row.dictName + ': ' + scope.row.dictCode;
      dictCode.value = scope.row.dictCode;
    }


    return {
      table,
      handleSizeChange,
      handleCurrentChange,
      searchParams,
      searchDictionary,
      formSize,
      dictStatus,
      getStatusDict,
      handleDelete,
      handleEditClick,
      handleSubmit,
      formVisible,
      dictForm,
      loading,
      formRules,
      validate,
      formTitle,
      handleAddClick,
      handleRefreshCache,
      dictDataModal,
      dictDataInfo,
      handleModalOpen,
      dictCode
    };
  }
}
const validate = ref(null);
</script>

<style lang="scss" scoped>
.dictionary-list-header {
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
.dictionary-list-footer {
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.dictionary-table {
  width: 100%;
  overflow: auto;
}

.dict-name {
  color: #1890FF;
  font-weight: bold;
  &:hover {
    cursor: pointer;
  }
}

@media screen and (max-width: 480px){
  .dictionary-table{
    height: 28rem;
    overflow: auto;
  }
}

</style>