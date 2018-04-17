import LoginVue from './Login.vue';

export default [
    {
        path: '/',
        redirect: 'login'
    },
    {
        path: '/login',
        component: LoginVue
    },
]