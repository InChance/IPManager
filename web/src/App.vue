<template>
    <div>
        <app-nav></app-nav>
        <transition name="fade" mode="out-in">
            <router-view class="view"></router-view>
        </transition>
    </div>
</template>

<script>
    import Nav from './components/Nav.vue';
    import Home from './views/home.vue';
    export default {
        name: 'App',
        components: {
            "app-nav": Nav,
            "app-home": Home
        }
    };
</script>

<style>
    body, html {
        font-size: 12px;
        margin: 0;
        padding: 0;
    }
</style>