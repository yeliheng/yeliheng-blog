<template>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  <div class="menu-container">
    <div class="menu-header">
      <div class="line"></div>
      <span>菜单管理</span>
    </div>
    <div class="menu-body">
      <el-form :inline="true" :model="searchParams" :size="formSize">
        <el-form-item label="菜单名称: ">
          <el-input v-model="searchParams.menuName" placeholder="菜单名称关键字"></el-input>
        </el-form-item>
        <el-form-item label="菜单状态: ">
          <el-select v-model="searchParams.hidden" clearable>
            <el-option
                v-for="item in menuHidden"
                :key="item.hidden"
                :label="item.label"
                :value="item.hidden"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchMenus()">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="menuOpen = true;dialogTitle = '新增菜单';menuForm = {};">新增菜单</el-button>
        </el-form-item>
      </el-form>


      <el-table
          class="menu-table"
          ref="multipleTable"
          :data="table.data"
          row-key="id"
          v-loading="table.loading"
          size="mini"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="menuName" label="菜单名称" width="130" align="center"/>
        <el-table-column property="icon" label="图标" width="80" align="center">
          <template #default="scope">
            <i :class="'fa ' + scope.row.icon" />
          </template>
        </el-table-column>
        <el-table-column property="permission" label="菜单权限" width="220" align="center"/>
        <el-table-column property="path" label="地址" width="130" align="center"/>
        <el-table-column property="component" label="组件路径" width="150" align="center"/>
        <el-table-column property="hidden" label="菜单状态" align="center" width="=60">
          <template #default="scope">
            <el-tag :type="getHiddenDict(scope.row.hidden).type"> {{getHiddenDict(scope.row.hidden).label}} </el-tag>
          </template>
        </el-table-column>
        <el-table-column property="createdAt" label="创建时间" width="150" align="center"/>

        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="text" size="mini" icon="fa fa-edit" @click="handleEditClick(scope.row)">修改</el-button>
            <el-popconfirm title="确定删除该菜单? " @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button type="text" size="mini" style="color: #ff8989;" icon="fa fa-trash">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>


    <!-- 添加或修改菜单 -->
    <el-dialog v-model="menuOpen" :title="dialogTitle" :close-on-click-modal="false" width="25rem">
      <el-form ref="validate" :model="menuForm" label-position="left" :rules="formRules">
        <el-form-item label="菜单名称: " label-width="100px" prop="menuName">
          <el-input autocomplete="off" placeholder="请输入菜单名称" v-model="menuForm.menuName" maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标: " label-width="100px" prop="icon">
          <el-input autocomplete="off" placeholder="font-awesome的图标名称" v-model="menuForm.icon" maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="路由地址: " label-width="100px" prop="path">
          <el-input autocomplete="off" placeholder="必须以 / 开头" v-model="menuForm.path" maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="组件路径: " label-width="100px" prop="component">
          <el-input autocomplete="off" placeholder="对应Vue的组件路径" v-model="menuForm.component" maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="菜单权限: " label-width="100px" prop="permission">
          <el-input autocomplete="off" placeholder="格式: **:**:**" v-model="menuForm.permission" maxlength="40"></el-input>
        </el-form-item>
        <el-form-item label="菜单排序: " label-width="100px" prop="orderNum">
          <el-input autocomplete="off" placeholder="数字越大越靠后" v-model="menuForm.orderNum" type="number" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单: " label-width="100px">
          <el-cascader :options="table.data"
                       :props="{checkStrictly: true,label: 'menuName',value: 'id'}"
                       v-model="menuForm.parentId"
                       ref="parentFormId"
                       clearable />
        </el-form-item>
        <el-form-item label="隐藏菜单: " label-width="100px">
          <el-switch v-model="menuForm.hidden" active-value="1" inactive-value="0"/>
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
                <el-button @click="onCancel()">取消</el-button>
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
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
import {addMenu, deleteMenu, getMenuList, updateMenu} from "@/api/menu";
import {buildMenuTree} from "@/utils/treeBuilder";

export default {
  setup(){
    const menuOpen = ref(false);

    const validate: any = ref(null);

    const loading = ref(false);

    const store = useStore();

    const formSize = ref('large');

    const dialogTitle = ref('');

    const parentFormId = ref();

    const formRules = {
      menuName: [
        {
          required: true,
          message: '请输入菜单名',
        },
      ],
    };


    const getHiddenDict = (hidden) => {
      let label: string;
      let type: string;
      if(hidden){
        label = '隐藏';
        type = 'warning';
      }else{
        label = '显示';
        type = 'success';
      }
      return {"label": label,"type": type};
    };


    const menuHidden = [
      {
        hidden: 1,
        label: '隐藏',
      },
      {
        hidden: 0,
        label: '显示',
      }
    ];
    //适配移动端
    if(store.state.app.isMobile){
      formSize.value = 'mini';
    }else{
      formSize.value = 'large';
    }

    const table:any = ref({
      loading: false,  // 加载中
      data: [],       // 表格数据
      isMobile: false,// 表格是否移动端
    });

    const searchParams: any = ref({
      menuName: '',
      hidden: '',
    });

    const menuForm: any = ref({
      id: 0,
      parentId: '',
      menuName: '',
      orderNum: '',
      path: '',
      component: '',
      hidden: '',
      permission: '',
      icon: '',
      createdAt: '',
    });


    table.value.isMobile = store.state.app.isMobile;

    //获取菜单列表
    const listMenus = () => {
      table.value.loading = true;
          getMenuList({}).then((res: any) => {
            table.value.data = buildMenuTree(res.data,'id','parentId','children');
            table.value.loading = false;
          });
    }

    listMenus();

    const searchMenus = () => {
      table.value.loading = true;
      if(!searchParams.value.menuName && !searchParams.value.hidden) {
        listMenus();
      }else {
        getMenuList({
          menuName: searchParams.value.menuName,
          hidden: searchParams.value.hidden,
        }).then((res: any) => {
          table.value.data = res.data;
          table.value.loading = false;
        });
      }
    }

    const handleDelete = (id) => {
      deleteMenu(id).then((res: any) => {
        if(!res.errCode) {
          ElMessage.success("删除成功!");
          listMenus();
        }else{
          ElMessage.error(res.detail);
        }
      });
    }

    const handleEditClick = (row) => {
      const formData = JSON.parse(JSON.stringify(row)); //消除row的响应性
      dialogTitle.value = '修改菜单';
      menuForm.value = formData;
      menuOpen.value = true;
    }

    const onCancel = () => {
      menuOpen.value = false;
    }


    const submitForm = () => {
      console.log(parentFormId.value.getCheckedNodes());
      if(parentFormId.value.getCheckedNodes().length > 0) {
        menuForm.value.parentId = parentFormId.value.getCheckedNodes()[0].value;
      }else {
        menuForm.value.parentId = 0;
      }

      if(menuForm.value.id) {
        //更新
        loading.value = true;
        updateMenu(menuForm.value).then((res: any) => {
          if(!res.errCode){
            ElMessage.success('更新菜单成功!');
            menuOpen.value = false;
            menuForm.value = {};
            listMenus();
          }else{
            ElMessage.error(res.detail);
          }
          loading.value = false;
        }).catch(() => {
          loading.value = false;
        });
      }else{
        //新增菜单
        validate.value.validate((valid) => {
          if(valid) {
            loading.value = true;
            addMenu(menuForm.value).then((res: any) => {
              if(!res.errCode){
                ElMessage.success('新建菜单成功!');
                menuOpen.value = false;
                menuForm.value = {};
                listMenus();
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
      searchParams,
      searchMenus,
      formSize,
      menuHidden,
      getHiddenDict,
      handleDelete,
      handleEditClick,
      submitForm,
      menuOpen,
      menuForm,
      loading,
      formRules,
      validate,
      dialogTitle,
      onCancel,
      parentFormId,
    };
  }
}
</script>

<style lang="scss" scoped>
.menu-header {
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
.menu-footer {
  display: flex;
  justify-content: flex-end;
  .pagination-nav{
    margin-top: 1rem;
  }
}
.menu-table {
  width: 100%;
  overflow: auto;
}

@media screen and (max-width: 480px){
  .menu-table{
    overflow: auto;
  }
}

</style>