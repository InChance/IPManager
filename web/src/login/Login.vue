<template>
    <div>
        {{msg}}
    </div>
</template>

<script>
    export default {
        name: 'login',
        data() {
            return {
                msg : "测试多页面"
            }
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