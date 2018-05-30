/**
 * 封装axios请求
 * @author leo
 * @Date 2018-5-30
 */
import axios from 'axios'
import qs    from 'qs'
import {bus} from '../bus'

axios.defaults.withCredentials  = true;

let printErrorMsg = error => {
    if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
    } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        console.log(error.request);
    } else {
        // Something happened in setting up the request that triggered an Error
        console.log('Error', error.message);
    }
    console.log(error.config);
};

// 请求拦截
axios.interceptors.request.use(function (conf) {
    bus.$emit('isShowLoad', true);
    return conf;
}, function (error) {
    printErrorMsg(error);
    return Promise.reject(error);
});

// 响应拦截
axios.interceptors.response.use(function (res) {
    bus.$emit('isShowLoad', false);
    return res;
}, function (error) {
    printErrorMsg(error);
    return Promise.reject(error);
});


let base = '/v1'; // 接口代理地址

// 通用请求（默认JSON请求）

// 表单请求
export const FORM_POST = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [params => qs.stringify(params)],
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
    }).then(res => res.data);
};

export const POST = (url, params) => {
    return axios.post(`${base}${url}`, params).then(res => res.data);
};

// 以params方式传输，后端接受是uri?key=value&...
export const GET = (url, params) => {
    return axios.get(`${base}${url}`, {params: params}).then(res => res.data)
};

export const PUT = (url, params) => {
    return axios.put(`${base}${url}`, params).then(res => res.data)
};

// 以data方式传输，后端接受是一个对象
export const DELETE = (url, params) => {
    return axios.delete(`${base}${url}`, {data: params}).then(res => res.data)
};

export const PATCH = (url, params) => {
    return axios.patch(`${base}${url}`, params).then(res => res.data)
};