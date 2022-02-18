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