import Vue from 'vue';
import App from './App.vue';
import VueRouter from 'vue-router';
import routes from './routes';
import axios from 'axios';
import ElementUI from 'element-ui';

// 全局SCSS变量
import './assets/reset.css'
import './assets/font-awesome.min.css'
import './assets/common.scss';

Vue.use(ElementUI);  // ElementUI组件注册
Vue.use(VueRouter); //路由注册

//将 axios 改写为 Vue 的原型$http属性,使在其它的组件中可以使用 $http
Vue.prototype.$http = axios;

// 实例化路由
const router = new VueRouter({
    routes
});

new Vue({
    router,
    el: '#app',
    render: h => h(App)
});