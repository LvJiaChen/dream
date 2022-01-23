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
export const queryMaterialList = data => {
    return Axios.post(baseUrl+"/wms-material/queryMaterialList",data);
};
/*编辑保存物料*/
export const saveMaterial = data => {
    return Axios.post(baseUrl+"/wms-material/saveMaterial",data);
};
/*删除物料*/
export const deleteMaterial = data => {
    return Axios.post(baseUrl+"/wms-material/deleteMaterial",data);
};
