import Axios from "../response/index";

const baseUrl="http://localhost:8081";

export const submitFormLogin = data => {
    return Axios.post(baseUrl+"/wms-user/login",data);
};

export const loginOutHeader = data => {
    return Axios.post(baseUrl+"/wms-user/logout",data);
};
