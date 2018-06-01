/**
 * 封装axios请求
 * @author leo
 * @Date 2018-5-30
 */
import axios from 'axios'
import qs    from 'qs'
import {bus} from '../bus'
import { Notification } from 'element-ui';

axios.defaults.withCredentials  = true;

// 打印请求失败信息
const printErrorMsg = error => {
    if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        console.error(error.response.data);
        console.error(error.response.status);
        console.error(error.response.headers);
        Notification.error({title:'错误', message:'系统发生无法想象的错误'});
    } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        console.error(error.request);
        Notification.error({title:'错误', message:'请求失败'});
    } else {
        // Something happened in setting up the request that triggered an Error
        console.error('Error', error.message);
    }
    console.error(error.config);
    bus.$emit('isShowLoad', false); // 结束加载动画
};

// 打印请求异常结果信息
const printTipsMsg = data => {
    if( data.code === 1 ) {
        return Promise.reject(new Error(data.body.msg));
    }
    return data;
};

// 弹出下载
const downloadUrl = url => {
    let iframe = document.createElement('iframe');
    iframe.style.display = 'none';
    iframe.src = url;
    iframe.onload = () => {
        document.body.removeChild(iframe);
    };
    document.body.appendChild(iframe);
};


// 请求拦截
axios.interceptors.request.use(function (conf) {
    bus.$emit('isShowLoad', true); // 启动加载动画
    return conf;
}, function (error) {
    printErrorMsg(error);
    return Promise.reject(error);
});

// 响应拦截
axios.interceptors.response.use(function (res) {
    bus.$emit('isShowLoad', false); // 结束加载动画, 事件总线
    if (res.headers && res.headers['content-type'] === 'application/octet-stream') {
        downloadUrl(res.request.responseURL);
    }
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
    }).then(res => printTipsMsg(res.data));
};

export const POST = (url, params) => {
    return axios.post(`${base}${url}`, params).then(res => printTipsMsg(res.data));
};

// 以params方式传输，后端接受是uri?key=value&...
export const GET = (url, params) => {
    return axios.get(`${base}${url}`, {params: params}).then(res => printTipsMsg(res.data));
};

export const PUT = (url, params) => {
    return axios.put(`${base}${url}`, params).then(res => printTipsMsg(res.data));
};

// 以data方式传输，后端接受是一个对象
export const DELETE = (url, params) => {
    return axios.delete(`${base}${url}`, {data: params}).then(res => printTipsMsg(res.data));
};

export const PATCH = (url, params) => {
    return axios.patch(`${base}${url}`, params).then(res => printTipsMsg(res.data));
};