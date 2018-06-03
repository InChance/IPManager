<template>
    <section id="document">
        <img :src="imgs.logo"/>
    </section>
</template>

<script>
    import logoImg from '../assets/img/logo.jpg';
    export default {
        data: () => ({
            show3: true,
            imgs: {
                logo : logoImg
            }
        })
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    #document {

    }
    .transition-box {
        margin-bottom: 10px;
        width: 200px;
        height: 100px;
        border-radius: 4px;
        background-color: #409EFF;
        text-align: center;
        color: #fff;
        padding: 40px 20px;
        box-sizing: border-box;
        margin-right: 20px;
    }
</style>