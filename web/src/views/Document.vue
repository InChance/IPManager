<template>
    <section>
        <img :src="imgs.logo"/>
        <el-button @click="show3 = !show3">Click Me</el-button>
        <div style="margin-top: 20px; height: 200px;">
            <el-collapse-transition>
                <div v-show="show3">
                    <div class="transition-box">el-collapse-transition</div>
                    <div class="transition-box">el-collapse-transition</div>
                </div>
            </el-collapse-transition>
        </div>
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