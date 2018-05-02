<template>
    <div id="main">
        <!--每页的页面跳转-->
        <transition name="fade" mode="out-in">
            <router-view class="view"></router-view>
        </transition>
    </div>
</template>

<style>
    body, html {
        font-size: 12px;
        height: 100%;
    }
    #main {
        height: 100%;
    }
</style>