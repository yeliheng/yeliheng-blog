<template>
  <div class="dictionary-list-container">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
    <div class="dictionary-list-header">
      <div class="line"></div>
      <span>字典数据管理</span>
    </div>
    <div class="dictionary-list-body">
      <el-form :inline="true" class="search-dictionary" :size="formSize">
        <el-form-item>
          <el-button type="primary" @click="handleAddClick()">添加字典数据</el-button>
        </el-form-item>
      </el-form>

      <el-table
          class="dictionary-table"
          ref="multipleTable"
          :data="table.data"
          v-loading="table.loading"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="id" label="数据编号" width="100" align="center" sortable/>
        <el-table-column property="dictLabel" label="数据标签" width="150" align="center" sortable>
          <template #default="scope">
            <el-tag :type="scope.row.classType">{{scope.row.dictLabel}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column property="dictValue" label="数据值" width="120" align="center" sortable />
        <el-table-column property="description" label="数据描述" width="220" align="center">
          <template #default="scope">
            <span v-if="scope.row.description == null"> - </span>
            {{scope.row.description}}
          </template>
        </el-table-column>
        <el-table-column property="sort" label="排序" width="80" align="center" sortable />
        <el-table-column property="createdAt" label="创建时间" width="150" align="center" sortable/>
        <el-table-column property="updatedAt" label="更新时间" width="150" align="center" sortable/>
        <el-table-column property="status" label="状态" align="center" sortable>
          <template #default="scope">
            <el-tag :type="getStatusDict(scope.row.status).type"> {{getStatusDict(scope.row.status).label}} </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
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

    <!-- 添加/修改字典 -->
    <el-dialog v-model="formVisible" :title="formTitle" :close-on-click-modal="false" width="25rem">
      <el-form ref="validate" :model="dictForm" label-position="left" :rules="formRules">
        <el-form-item label="字典代码: " label-width="100px" required>
          <el-input autocomplete="off"  v-model="this.$props.code" disabled></el-input>
        </el-form-item>
        <el-form-item label="数据标签: " label-width="100px" prop="dictLabel" required>
          <el-input autocomplete="off" v-model="dictForm.dictLabel"></el-input>
        </el-form-item>
        <el-form-item label="数据值: " label-width="100px" prop="dictValue">
          <el-input autocomplete="off" v-model="dictForm.dictValue"></el-input>
        </el-form-item>
        <el-form-item label="数据描述: " label-width="100px" prop="description">
          <el-input autocomplete="off" v-model="dictForm.description" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="字典样式: " label-width="100px" prop="classType">
          <el-select v-model="dictForm.classType" clearable>
            <el-option
                v-for="item in classTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据排序: " label-width="100px" prop="orderNum">
          <el-input autocomplete="off" v-model="dictForm.sort" type="number" maxlength="10" placeholder="0"></el-input>
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
  </div>
</template>

<script lang="ts">
import {ref, watch} from 'vue';
import {
  addDictionaryData,
  deleteDictionaryData,
  getDictionaryDataByCode,
  updateDictionaryData
} from "@/api/dictionaryData";
import {useStore} from 'vuex';
import {ElMessage} from 'element-plus';

export default {
  props: {
    code: String,
  },
  setup(props){

    const dictCode = ref(props.code);
    watch( () => props.code,
        (newValue) => {
          dictCode.value = newValue;
          listDictionary();
    });

    const formVisible = ref(false);

    const validate: any = ref(null);

    const loading = ref(false);

    const store = useStore();

    const formSize = ref('large');

    const formRules = {
      dictLabel: [
        {
          required: true,
          message: '请输入数据标签',
          trigger: 'blur',
        },
        {
          max: 20,
          message: '数据标签不能超过20字符',
          trigger: 'blur',
        }
      ],
      dictValue: [
        {
          required: true,
          message: '请输入数据值',
          trigger: 'blur',
        },
        {
          max: 20,
          message: '数据值不能超过20字符',
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

    const dictForm: any = ref({});

    table.value.isMobile = store.state.app.isMobile;

    //获取字典数据列表
    const listDictionary = () => {
        dictForm.value.dictCode = dictCode.value;
        table.value.loading = true;
        getDictionaryDataByCode(dictCode.value).then((res: any) => {
          table.value.data = res.data;
          table.value.loading = false;
        });
    }

    listDictionary();

    const handleDelete = (id) => {
      deleteDictionaryData(id).then((res: any) => {
        if(!res.errCode) {
          ElMessage.success("删除成功!");
          listDictionary();
        }else{
          ElMessage.error(res.detail);
        }
      });
    }

    const handleEditClick = (row) => {
      formTitle.value = '修改字典数据';
      dictForm.value = JSON.parse(JSON.stringify(row)); //消除row的响应性
      formVisible.value = true;
    }

    const handleAddClick = () => {
      formTitle.value = '添加字典数据';
      dictForm.value = {};
      formVisible.value = true;
    }

    const classTypeOptions = [
      {
        value: "success",
        label: "成功"
      },
      {
        value: "info",
        label: "信息"
      },
      {
        value: "warning",
        label: "警告"
      },
      {
        value: "danger",
        label: "危险"
      }
    ];

    const handleSubmit = () => {
      dictForm.value.dictCode = dictCode.value;
      //添加字典数据
      if(!dictForm.value.id) {
        validate.value.validate((valid) => {
          if(valid) {
            loading.value = true;
            addDictionaryData(dictForm.value).then((res: any) => {
              if(!res.errCode){
                ElMessage.success('新建字典数据成功!');
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
        //修改字典数据
      }else {
        validate.value.validate((valid) => {
          if(valid){
            loading.value = true;
            updateDictionaryData(dictForm.value).then((res: any) => {
              if(!res.errCode) {
                ElMessage.success('更新字典数据成功!');
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


    return {
      table,
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
      classTypeOptions,
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

@media screen and (max-width: 480px){
  .dictionary-table{
    height: 28rem;
    overflow: auto;
  }
}

</style>