<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 物料出库
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-tabs v-model="tab"  @tab-remove="removeTab">
        <el-tab-pane label="出库查询" name="first">
          <div class="handle-box">
            <el-input v-model="query.code" placeholder="出库单号" class="handle-input mr10"></el-input>
            <el-input v-model="query.materialName" placeholder="物料名称" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <el-button type="primary" icon="el-icon-search" @click="handleAddEdit(null,null,'添加')">添加</el-button>
          </div>
          <el-table :data="tableData" border stripe class="table" ref="multipleTable" header-cell-class-name="table-header">
            <el-table-column prop="code" label="出库单号" width="150px"></el-table-column>
            <el-table-column prop="deliverDate" label="出库时间">
              <template #default="scope">
                {{operatingTime_yyyyMMDD(scope.row.deliverDate)}}
              </template>
            </el-table-column>
            <el-table-column prop="warehouseCode" label="仓库编码" width="150px"></el-table-column>
            <el-table-column prop="warehouseName" label="仓库名称"></el-table-column>
            <el-table-column prop="materialNameConcat" label="物料名称" width="400px"></el-table-column>
            <el-table-column prop="moneyAll" label="出库总金额"></el-table-column>
            <el-table-column prop="creator" label="创建人"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="150px">
              <template #default="scope">
                {{operatingTime(scope.row.createTime)}}
              </template>
            </el-table-column>
            <el-table-column fixed label="操作" width="180" align="center">
              <template #default="scope">
                <el-button type="text" icon="el-icon-edit" @click="handleAddEdit(scope.$index, scope.row,'查看')">查看</el-button>
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
            <el-form-item label="出库单号">
              <el-input v-model="form.code" placeholder="系统自动生成" readonly></el-input>
            </el-form-item>
            <el-form-item label="出库时间" prop="deliverDate">
              <el-date-picker
                  :readonly="isAdd"
                  v-model="form.deliverDate"
                  type="date"
                  placeholder="请输入日期"
                  :default-value="new Date()"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item label="领料单号" prop="referenceNo">
              <el-select v-model="form.referenceNo"
                         :disabled="isAdd"
                         filterable
                         remote
                         :remote-method="queryRequisitionData"
                         class="m-2"
                         placeholder="请输入仓库"
                         size="large">
                <el-option
                    v-for="item in requisitionData"
                    :key="item.code"
                    :label="item.code"
                    :value="item.code"
                >
                  <span style="float: left; font-size: 13px">申请单号：{{item.code }}</span>
                  <span style="float: right; font-size: 13px">物料名称：{{ item.materialNameConcat }}</span>
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="仓库名称" prop="warehouseCode">
              <el-select v-model="form.warehouseCode"
                         :disabled="isAdd"
                         filterable
                         remote
                         :remote-method="queryWarehouseData"
                         class="m-2"
                         placeholder="请输入仓库"
                         size="large">
                <el-option
                    v-for="item in warehouseData"
                    :key="item.code"
                    :label="item.name"
                    :value="item.code"
                >
                  {{ item.name }}
                </el-option>
              </el-select>
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
          <el-table :data="deliverData" border highlight-current-row
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
            <el-table-column label="价格" width="100">
              <template #default="scope">￥{{ scope.row.price }}</template>
            </el-table-column>
            <el-table-column label="数量" width="250">
              <template #default="scope">
                <el-input-number  :disabled="isAdd" v-model="scope.row.quantity" :min="0" precision="4" @change="quantityChange(scope.row)"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column prop="unit" label="单位" width="100"></el-table-column>
            <el-table-column label="金额" width="100">
              <template #default="scope">￥{{ scope.row.money }}</template>
            </el-table-column>
          </el-table>

        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import moment from "moment"
import {reactive, ref} from "vue";
import {
  queryMaterialList,
  queryWarehouseList,
  queryEntryListPage,
  queryEntryDetail,
  saveEntry,
  queryRequisitionNo
} from "../api";
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
    const warehouseData=ref([]);
    const requisitionData=ref([]);
    const materialData=ref([]);
    const tableData = ref([]);
    const pageTotal = ref(0);
    const rules = {
      deliverDate: [
        {
          required: true,
          message: '请输入出库时间',
          trigger: 'blur',
        }
      ],
      referenceNo:[
        {
          required: true,
          message: '请选择领料单',
          trigger: 'blur',
        },
      ],
      warehouseCode:[
        {
          required: true,
          message: '请选择仓库',
          trigger: 'blur',
        },
      ]
    }
    let formRef=ref(null);
    const deliverData=ref([])
    //获取领料单数据
    const queryRequisitionData=(query)=>{
      if (query) {
        queryRequisitionNo({"value":query}).then((res) => {
          requisitionData.value = res.data;
        });
      } else {
        requisitionData.value = []
      }
    };
    // 获取仓库数据
    const queryWarehouseData = (query) => {
      if (query) {
        queryWarehouseList({"value":query}).then((res) => {
          warehouseData.value = res.data;
        });
      } else {
        warehouseData.value = []
      }
    };

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
      queryEntryListPage(query).then((res) => {
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
      deliverDate:new Date(),
      code: null,
      referenceNo:null,
      warehouseCode: null
    });

    const handleAddEdit = (index, row,type) => {
      if (row!==null){
        isAdd.value=true;
        queryWarehouseList({"value":row.warehouseCode}).then((res) => {
          warehouseData.value = res.data;
        });
        //编辑
        Object.keys(form).forEach((item) => {
          form[item] = row[item];
        });
        queryEntryDetail({code:row.code}).then((res)=>{
          deliverData.value=res.data;
        });
      }else {
        isAdd.value=false;
        form.deliverDate=new Date();
        form.code=null;
        form.warehouseCode=null;
        deliverData.value=[];
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
      deliverData.value.push({
        materialNo:null,
        materialName:null,
        brand:null,
        space:null,
        price:null,
        unit:null,
        quantity:0,
        money:0
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
      deliverData.value.splice(currentRowIndex.value,1)
    };

    const quantityChange=(row)=>{
      row.money=row.quantity*row.price;
    };
    const save = () => {
      formRef.value.validate((valid) => {
        if (valid) {
          if (deliverData.value.length===0){
            ElMessage.warning("请添加入库物料")
            return
          }
          form.deliverData=deliverData.value;
          saveEntry(form).then((res) => {
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
      requisitionData,
      warehouseData,
      materialData,
      deliverData,
      isAdd,
      queryRequisitionData,
      queryWarehouseData,
      queryMaterialData,
      materialChange,
      quantityChange,
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