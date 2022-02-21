import Axios from "../response/index";

const baseUrl="http://localhost:8081";
/*登录、登出*/
export const submitFormLogin = data => {
    return Axios.post(baseUrl+"/wms-user/login",data);
};

export const loginOutHeader = data => {
    return Axios.post(baseUrl+"/wms-user/logout",data);
};
/*物料管理*/
/*查询物料*/
export const queryMaterialListPage = data => {
    return Axios.post(baseUrl+"/wms-material/queryMaterialListPage",data);
};
/*编辑保存物料*/
export const saveMaterial = data => {
    return Axios.post(baseUrl+"/wms-material/saveMaterial",data);
};
/*删除物料*/
export const deleteMaterial = data => {
    return Axios.post(baseUrl+"/wms-material/deleteMaterial",data);
};
/*物料下拉选择*/
export const selectMaterial = data => {
    return Axios.post(baseUrl+"/wms-material/selectMaterial",data);
};

/*查询物料信息-选择器*/
export const queryMaterialList = data => {
    return Axios.post(baseUrl+"/wms-material/queryMaterialList",data);
};

/*仓库管理*/
/*查询仓库*/
export const queryWarehouseListPage = data => {
    return Axios.post(baseUrl+"/wms-warehouse/queryWarehouseListPage",data);
};
/*编辑保存仓库*/
export const saveWarehouse = data => {
    return Axios.post(baseUrl+"/wms-warehouse/saveWarehouse",data);
};
/*删除仓库*/
export const deleteWarehouse = data => {
    return Axios.post(baseUrl+"/wms-warehouse/deleteWarehouse",data);
};
/*查询仓库信息-选择器*/
export const queryWarehouseList = data => {
    return Axios.post(baseUrl+"/wms-warehouse/queryWarehouseList",data);
};

/*入库管理*/
/*查询入库*/
export const queryEntryListPage = data => {
    return Axios.post(baseUrl+"/wms-entry/queryEntryListPage",data);
};
/*保存入库单*/
export const saveEntry = data => {
    return Axios.post(baseUrl+"/wms-entry/saveEntry",data);
};
/*查询入库单的物料明细*/
export const queryEntryDetail = data => {
    return Axios.post(baseUrl+"/wms-entry/queryEntryDetail",data);
};

/*领料单管理*/
/*查询领料单*/
export const queryRequisitionListPage = data => {
    return Axios.post(baseUrl+"/wms-requisition/queryRequisitionListPage",data);
};
/*保存领料单*/
export const saveRequisition = data => {
    return Axios.post(baseUrl+"/wms-requisition/saveRequisition",data);
};
/*查询领料单的物料明细*/
export const queryRequisitionDetail = data => {
    return Axios.post(baseUrl+"/wms-requisition/queryRequisitionDetail",data);
};
/*出库单管理*/
/*查询出库单信息*/
export const queryDeliverListPage = data => {
    return Axios.post(baseUrl+"/wms-deliver/queryDeliverListPage",data);
};
/*查询领料单号*/
export const queryRequisitionNo = data => {
    return Axios.post(baseUrl+"/wms-requisition/queryRequisitionNo",data);
};
/*
查询未出库和部分出库的领料单，通过领料单号
*/
export const queryRequisitionForRequisitionNo = data => {
    return Axios.post(baseUrl+"/wms-requisition/queryRequisitionForRequisitionNo",data);
};
/*保存出库单*/
export const saveDeliver = data => {
    return Axios.post(baseUrl+"/wms-deliver/saveDeliver",data);
};
/*查询出库单详情*/
export const queryDeliverDetail = data => {
    return Axios.post(baseUrl+"/wms-deliver/queryDeliverDetail",data);
};
/*系统首页*/
/*查询代办事项*/
export const queryToDoMattersList = data => {
    return Axios.post(baseUrl+"/wms-to-do-matters/queryToDoMattersList",data);
};
/*修改状态*/
export const queryToDoMattersStatus = data => {
    return Axios.post(baseUrl+"/wms-to-do-matters/queryToDoMattersStatus",data);
};
/*保存待办事项*/
export const saveMatterPost = data => {
    return Axios.post(baseUrl+"/wms-to-do-matters/saveMatterPost",data);
};
/*查询今日出库入库金额，及当前库存金额*/
export const queryEntryDeliverStockToDate = data => {
    return Axios.post(baseUrl+"/wms-to-do-matters/queryEntryDeliverStockToDate",data);
};
/*查询出入库金额图标数据*/
export const queryEntryDeliverSchart = data => {
    return Axios.post(baseUrl+"/wms-to-do-matters/queryEntryDeliverSchart",data);
};