const HomeVue = () => import(/* webpackChunkName: 'Home' */ './views/Home.vue');
const DocumentVue = () => import(/* webpackChunkName: 'Document' */ './views/Document.vue');
const IPMaskChart = () => import (/*webpackChunkName: 'IPMaskChart'*/ './views/IPMaskChart.vue');
const IPMaskSearch = () => import (/*webpackChunkName: 'IPMaskSearch'*/ './views/IPMaskSearch.vue');

export default [
    {
        path: '/',
        redirect: 'home'
    },
    {
        path: '/home',
        component: HomeVue,
        children: [
            {
                path: '',
                component: IPMaskChart
            },
            {
                path: 'search',
                component: IPMaskSearch
            },
            {
                path: 'collect',
                component: null
            }
        ]
    },
    {
        path: '/docs',
        component: DocumentVue
    }
]