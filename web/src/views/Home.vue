<template>
    <el-container id="home">
        <el-aside>
            <aside-content></aside-content>
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
    export default {
        data: () => {
            return {
                isCollapse: true,
                curAddress: '网段详情',
                msg: "123456"
            }
        },
        components: {
            'aside-content': Aside,
            'head-content': Head
        },
        created: function () {
            this.getData();
        },
        methods: {
            getData : function () {
                let _this = this;
//                this.$http.get("/v1/hello").then( function (response) {
//                    _this.msg = response.data;
//                });
            }
        }
    }
</script>

<style lang="scss" scoped>
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