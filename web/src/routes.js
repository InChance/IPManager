import HomeVue from './views/home.vue';
const AboutVue = () => import(/* webpackChunkName: 'about' */ './views/about.vue');

export default [
    {
        path: '/',
        redirect: 'home'
    },
    {
        path: '/home',
        component: HomeVue
    },
    {
        path: '/about',
        component: AboutVue
    }
]