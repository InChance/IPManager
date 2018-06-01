<template>
    <el-container id="home">
        <loader></loader>
        <el-aside>
            <aside-content @router-switch="getCurAddress"></aside-content>
        </el-aside>
        <el-main>
            <head-content :curAddress="curAddress"></head-content>
            <!--每页的局部页面跳转-->
            <transition name="fade" mode="out-in">
                <router-view class="view"></router-view>
            </transition>
        </el-main>
    </el-container>
</template>

<script>
    import Aside    from "../components/layout/Aside";
    import Head     from "../components/layout/Head";
    import Loader   from "../components/loader"
    export default {
        data: () => {
            return {
                curAddress: '',
            }
        },
        components: {
            'aside-content': Aside,
            'head-content': Head,
            'loader': Loader
        },
        methods: {
            getCurAddress: function (name) {
                this.curAddress = name;
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    #home {
        height: 100%;
    }
    .el-container {
        background-color: #f9fafc;
        height: 100%;
    }
    .el-aside {
        width: 210px !important;
        height: 100%;
        background: #1e1f21;
    }
    .el-main {
        margin: 0;
        padding: 0;
        height: 100%;
    }
</style>