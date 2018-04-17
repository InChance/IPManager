<template>
    <nav>
        <router-link v-for="nav in navList" :to="nav.path" :key="nav.id" :style="{width: 100/navList.length + '%'}">{{nav.text}}</router-link>
    </nav>
</template>

<script>
    export default {
        name: 'Nav',
        data() {
            return {
                navList: [
                    {path: '/home', text: '首页'},
                    {path: '/about', text: '关于'},
                ]
            }
        }
    }
</script>
