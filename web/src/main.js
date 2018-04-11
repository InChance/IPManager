import Vue from 'vue';
import App from './App.vue';
import VueRouter from 'vue-router';
import routes from './routes';
import VueResource from 'vue-resource';

import ElementUI from 'element-ui';
import './static/element-variables.scss';

Vue.use(ElementUI);  // ElementUI组件注册
Vue.use(VueResource); //http请求注册
Vue.use(VueRouter); //路由注册

// 实例化路由
const router = new VueRouter({
    routes
});

new Vue({
    router,
    el: '#app',
    render: h => h(App)
});