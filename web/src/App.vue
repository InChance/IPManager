<template>
    <div id="main">
        <!--<app-nav></app-nav>-->
        <transition name="fade" mode="out-in">
            <router-view class="view"></router-view>
        </transition>
    </div>
</template>

<script>
    import Nav from './components/Nav.vue';
    import Home from './views/home.vue';
    export default {
        data: () => {
            return {

            }
        },
        components: {
            "app-nav": Nav,
            "app-home": Home
        },
        methods: {

        }
    };
</script>

<style>
    body, html {
        font-size: 12px;
        height: 100%;
    }
    #main {
        height: 100%;
    }
</style>