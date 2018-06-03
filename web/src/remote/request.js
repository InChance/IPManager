/**
 * 网络层处理，封装axios请求
 * @author leo
 * @Date 2018-5-30
 */
import axios from 'axios'
import qs from 'qs'
import {bus} from '../bus'


const PROXY_URL = '/v1';                        // 接口转发代理地址
axios.defaults.withCredentials  = true;         // 支持加密连接
axios.defaults.retry = 3;                       // 重试次数
axios.defaults.retryDelay = 500;                // 重试延时
axios.defaults.shouldRetry = (error) => true;   // 重试条件，默认只要是错误都需要重试

// 处理请求失败结果
const handleErrorMsg = error => {
    bus.$emit('isShowLoad', false); // 结束加载动画
    if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        console.error(error.response);
        // 可自定义错误信息返回，如下：
        return Promise.reject(new Error("系统崩溃，程序员小哥哥正在努力修复中..."));
    } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        console.error(error.request);
        return Promise.reject(new Error("当前浏览器不支持访问..."));
    } else {
        // Something happened in setting up the request that triggered an Error
        console.error(error.message);
        return Promise.reject(error);
    }
};


// 处理正常返回的请求结果
const handleTipsMsg = data => {
    // 服务器校验不通过
    if( data.code === 1 ) {
        return Promise.reject(new Error(data.body.msg));
    }
    return Promise.resolve(data);
};


// 网络请求失败，重试尝试
const errorRetry = error => {
    let config = error.config;
    // 判断是否配置了重试
    if(!config || !config.retry) {
        return Promise.reject(error);
    }
    // 判断是否配置了重试条件
    if(!config.shouldRetry(error)){
        return Promise.reject(error);
    }
    // 设置重置次数，默认0
    config.__retryCount = config.__retryCount || 0;
    // 判断是否超过了重试次数
    if(config.__retryCount >= config.retry) {
        return Promise.reject(error);
    }
    //重试次数自增
    config.__retryCount += 1;
    console.log("尝试重连第", config.__retryCount, "次。。。");
    //延时处理
    let backoff = new Promise(resolve=>{
        setTimeout(()=>{ // 定时执行一次
            resolve();
        }, config.retryDelay || 1);
    });
    //重新发起axios请求
    return backoff.then(()=>{ // 因为上面延时处理的resolve方法不带参，这里then也无参数可接受
        return axios(config);
    });
};


// 请求拦截
axios.interceptors.request.use(function (config) {
    bus.$emit('isShowLoad', true); // 启动加载动画
    return config;
}, function (error) {
    return Promise.reject(error);
});

// 响应拦截
axios.interceptors.response.use(function (response) {
    bus.$emit('isShowLoad', false); // 结束加载动画, 事件总线
    return response;
}, function (error) {
    return errorRetry(error);
});


// 表单请求
export const FORM_POST = (url, params) => {
    return axios({
        method: 'post',
        url: `${PROXY_URL}${url}`,
        data: params,
        transformRequest: [params => qs.stringify(params)],
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
    }).then(res => {
        return handleTipsMsg(res.data);
    }).catch(ex => {
        return handleErrorMsg(ex);
    });
};

// 打开新界面，GET方式请求
export const WIN_OPEN = (url, params) => {
    window.open(`${PROXY_URL}${url}` + '?' + qs.stringify(params));
};

// 默认JSON请求
export const POST = (url, params) => {
    return axios.post(`${PROXY_URL}${url}`, params).then(res => {
        return handleTipsMsg(res.data);
    }).catch(ex => {
        return handleErrorMsg(ex);
    });
};

// 以params方式传输，后端接受是uri?key=value&...
export const GET = (url, params) => {
    return axios.get(`${PROXY_URL}${url}`, {params: params}).then(res => {
        return handleTipsMsg(res.data);
    }).catch(ex => {
        return handleErrorMsg(ex);
    });
};

// 默认JSON请求
export const PUT = (url, params) => {
    return axios.put(`${PROXY_URL}${url}`, params).then(res => {
        return handleTipsMsg(res.data);
    }).catch(ex => {
        return handleErrorMsg(ex);
    });
};

// 以data方式传输，后端接受是一个JSON对象
export const DELETE = (url, params) => {
    return axios.delete(`${PROXY_URL}${url}`, {data: params}).then(res => {
        return handleTipsMsg(res.data);
    }).catch(ex => {
        return handleErrorMsg(ex);
    });
};