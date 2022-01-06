import request from '../utils/request'
export function loginSubmit(data){
    return request({
        url:"/wms-user/login",
        method:"post",
        params:data
    });
}