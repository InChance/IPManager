<template>
    <div>
        <!--路由过渡切换-->
        <transition name="fade" mode="out-in">
            <router-view class="view"></router-view>
        </transition>
    </div>
</template>

<style>
    body, html {
        font-size: 12px;
        margin: 0;
        padding: 0;
    }
</style>