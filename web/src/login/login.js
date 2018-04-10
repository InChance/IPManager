import Vue from 'vue';
import App from './Login.vue';
import VueResource from 'vue-resource';
import VueRouter from 'vue-router';
import routes from './loginR';

Vue.use(VueResource); //http请求注册
Vue.use(VueRouter); //路由注册

// 实例化路由
const router = new VueRouter({
    routes
});

new Vue({
    router,
    el: '#test',
    render: h => h(App)
});