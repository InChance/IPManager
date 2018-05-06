import 'babel-polyfill';    // 兼容IE浏览器
import Vue from 'vue';
import VueRouter from 'vue-router';
import ElementUI from 'element-ui';
import axios from 'axios';
import routes from './routes';
import App from './App.vue';

import './assets/reset.css';
import './assets/font-awesome.min.css';
import './assets/common.scss';

Vue.use(ElementUI);
Vue.use(VueRouter);

Vue.prototype.$http = axios;

const router = new VueRouter({
    routes
});

new Vue({
    router,
    el: '#app',
    render: h => h(App)
});