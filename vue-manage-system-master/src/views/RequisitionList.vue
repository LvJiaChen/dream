<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 领料单管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-tabs v-model="tab"  @tab-remove="removeTab">
        <el-tab-pane label="领料单查询" name="first">
          <div class="handle-box">
            <el-input v-model="query.code" placeholder="申请单号" class="handle-input mr10"></el-input>
            <el-input v-model="query.materialName" placeholder="物料名称" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <el-button type="primary" icon="el-icon-search" @click="handleAddEdit(null,null,'添加')">添加</el-button>
          </div>
          <el-table :data="tableData" border stripe class="table" ref="multipleTable" header-cell-class-name="table-header">
            <el-table-column prop="code" label="申请单号" width="150px"></el-table-column>
            <el-table-column prop="requisitionDate" label="申请时间">
              <template #default="scope">
                {{operatingTime_yyyyMMDD(scope.row.requisitionDate)}}
              </template>
            </el-table-column>
            <el-table-column prop="materialNameConcat" label="物料名称" width="400px"></el-table-column>
            <el-table-column prop="status" label="状态" width="100px"></el-table-column>
            <el-table-column prop="creator" label="创建人"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="150px">
              <template #default="scope">
                {{operatingTime(scope.row.createTime)}}
              </template>
            </el-table-column>
            <el-table-column fixed label="操作" width="180" align="center">
              <template #default="scope">
                <el-button type="text" icon="el-icon-edit" @click="handleAddEdit(scope.$index, scope.row,'查看')">查看</el-button>
                <el-button type="text" icon="el-icon-edit" @click="handleAddEdit(scope.$index, scope.row,'修改')">修改</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                           :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
          </div>
        </el-tab-pane>
        <el-tab-pane closable
                     v-for="item in editableTabs"
                     :key="item.name"
                     :label="item.title"
                     :name="item.name"
        >
          <el-form :inline="true" label-width="90px" :model="form" :rules="rules" ref="formRef">
            <el-form-item>
              <el-button type="primary" @click="save" v-if="!isAdd">保 存</el-button>
            </el-form-item>
            <br>
            <el-form-item label="申请单号">
              <el-input v-model="form.code" placeholder="系统自动生成" readonly></el-input>
            </el-form-item>
            <el-form-item label="申请时间" prop="requisitionDate">
              <el-date-picker
                  :readonly="isAdd"
                  v-model="form.requisitionDate"
                  type="date"
                  placeholder="请输入日期"
                  :default-value="new Date()"
              >
              </el-date-picker>
            </el-form-item>
            <br>
            <el-form-item>
              <el-button type="primary" v-if="!isAdd" @click="addMaterial">添 加</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-if="!isAdd" @click="deleteMaterial">删 除</el-button>
            </el-form-item>
          </el-form>
          <!--表格添加-->
          <el-table :data="requisitionData" border highlight-current-row
                    class="table" ref="multipleTable"
                    header-cell-class-name="table-header"
                    @row-click="onRowClick"
                    :row-class-name="tableRowClassName">
            <el-table-column type="index" width="50" />
            <el-table-column label="物料编码">
              <template #default="scope">
                <el-select v-model="scope.row.materialNo"
                           :disabled="isAdd"
                           filterable
                           remote
                           :remote-method="queryMaterialData"
                           class="m-2"
                           placeholder="请输入物料信息"
                           @change="materialChange(scope.row)"
                           size="large">
                  <el-option
                      v-for="item in materialData"
                      :key="item.materialNo"
                      :label="item.materialName"
                      :value="item.materialNo"
                  >
                    <span style="float: left; font-size: 13px">物料编码：{{item.materialNo }}</span>
                    <span style="float: right; font-size: 13px">物料名称：{{ item.materialName }}</span>
                    <span style="float: right; font-size: 13px">品牌：{{ item.brand }}</span>
                    <span style="float: right; font-size: 13px">规格：{{ item.space }}</span>
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="materialName" label="物料名称"></el-table-column>
            <el-table-column prop="brand" label="品牌"></el-table-column>
            <el-table-column prop="space" label="规格型号"></el-table-column>
            <el-table-column label="参考价格" width="100">
              <template #default="scope">￥{{ scope.row.price }}</template>
            </el-table-column>
            <el-table-column label="数量" width="250">
              <template #default="scope">
                <el-input-number  :disabled="isAdd" v-model="scope.row.quantity" :min="0" precision="4"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="已出库数量" width="250" v-if="isAdd">
              <template #default="scope">
                <el-input-number  :disabled="isAdd" v-model="scope.row.deliverQuantity" :min="0" precision="4"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column prop="unit" label="单位" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" v-if="isAdd"></el-table-column>
          </el-table>

        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import moment from "moment"
import {reactive, ref} from "vue";
import {queryMaterialList, queryRequisitionListPage,queryRequisitionDetail, saveRequisition} from "../api";
import {ElMessage} from "element-plus";

export default {
  name: "WarehouseList",
  setup() {
    const tab=ref("first")
    let tabIndex = 0
    const editableTabs = ref([]);
    let isAdd=ref(false);
    const query = reactive({
      code: null,
      name: null,
      pageIndex: 1,
      pageSize: 10,
    });
    const materialData=ref([]);
    const tableData = ref([]);
    const pageTotal = ref(0);
    const rules = {
      requisitionDate: [
        {
          required: true,
          message: '请输入申请时间',
          trigger: 'blur',
        }
      ]
    }
    let formRef=ref(null);
    const requisitionData=ref([])


    // 获取物料数据
    const queryMaterialData = (query) => {
      if (query) {
        queryMaterialList({"value":query}).then((res) => {
          materialData.value = res.data;
        });
      } else {
        materialData.value = []
      }
    };

    const materialChange=(row)=>{
      let material=null;
      let flag=0;
      for (let item of requisitionData.value) {
        if (item.materialNo==row.materialNo){
          flag++;
        }
      }
      if (flag>1){
        ElMessage.error("物料编码不能重复添加")
        row.materialNo=null;
        return
      }
      materialData.value.forEach(a=>{
        if (a.materialNo===row.materialNo){
          material=a;
        }
      })
      row.materialName=material.materialName;
      row.brand=material.brand;
      row.space=material.space;
      row.price=material.price;
      row.unit=material.unit;
    };
    // 获取表格数据
    const getData = () => {
      queryRequisitionListPage(query).then((res) => {
        ElMessage.success("查询成功");
        tableData.value = res.data.records;
        pageTotal.value = res.data.total;
      });
    };
    //界面初始化
    getData();

    // 查询操作
    const handleSearch = () => {
      query.pageIndex = 1;
      getData();
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageIndex = val;
      getData();
    };

    // 表格编辑时弹窗和保存
    let form = reactive({
      requisitionDate:new Date(),
      code: null
    });

    const handleAddEdit = (index, row,type) => {
      if (row!==null){
        if (type=='查看'){
          isAdd.value=true;
        }else {
          isAdd.value=false;
        }
        //编辑
        Object.keys(form).forEach((item) => {
          form[item] = row[item];
        });
        queryRequisitionDetail({code:row.code}).then((res)=>{
          requisitionData.value=res.data;
        });
      }else {
        isAdd.value=false;
        form.requisitionDate=new Date();
        form.code=null;
        requisitionData.value=[];
      }
      const newTabName = `${++tabIndex}`
      editableTabs.value.push({
        title: type,
        name: newTabName,
      })
      tab.value=newTabName
    };
    const removeTab=(targetName)=>{
      const tabs = editableTabs.value
      let activeName = tab.value
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            const nextTab = tabs[index + 1] || tabs[index - 1]
            if (nextTab) {
              activeName = nextTab.name
            }
          }
        })
      }
      tab.value = 'first'
      editableTabs.value = tabs.filter((tab) => tab.name !== targetName)
    };

    const addMaterial=()=>{
      requisitionData.value.push({
        materialNo:null,
        materialName:null,
        brand:null,
        space:null,
        price:null,
        unit:null,
        quantity:0,
      })
    };
    const currentRowIndex = ref();
    const tableRowClassName=({row, rowIndex})=>{
      row.row_index=rowIndex;
    };
    const onRowClick=(row, event, column)=>{
      currentRowIndex.value=row.row_index;
    };
    const deleteMaterial=()=>{
      requisitionData.value.splice(currentRowIndex.value,1)
    };
    const save = () => {
      formRef.value.validate((valid) => {
        if (valid) {
          if (requisitionData.value.length===0){
            ElMessage.warning("请添加入库物料")
            return
          }
          form.requisitionData=requisitionData.value;
          saveRequisition(form).then((res) => {
            ElMessage.success("保存成功");
            removeTab(tab.value);
            getData();
          });
        }
      })
    };
    const operatingTime=(timeDate)=>{
      return  moment(timeDate).format('yyyy-MM-DD HH:mm:ss');
    };
    const operatingTime_yyyyMMDD=(timeDate)=>{
      return  moment(timeDate).format('yyyy-MM-DD');
    };
    return {
      tab,
      editableTabs,
      query,
      tableData,
      pageTotal,
      form,
      rules,
      formRef,
      materialData,
      requisitionData,
      isAdd,
      queryMaterialData,
      materialChange,
      removeTab,
      operatingTime,
      operatingTime_yyyyMMDD,
      handleSearch,
      handlePageChange,
      handleAddEdit,
      save,
      addMaterial,
      deleteMaterial,
      tableRowClassName,
      onRowClick
    };
  },
}
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.table {
  width: 100%;
  font-size: 14px;
}
.mr10 {
  margin-right: 10px;
}
</style>