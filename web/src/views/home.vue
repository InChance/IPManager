<template>
    <div id="home">
        <el-row>
            <el-col :span="3">
                <div class="grid-content bg-purple-dark">
                    <div class="left-head">
                        IP地址管理系统
                    </div>
                    <div class="left-list">
                        123
                    </div>
                </div>
            </el-col>
            <el-col :span="21"><div class="grid-content">2</div></el-col>
        </el-row>
    </div>
</template>

<script>
    import ElRow from "element-ui/packages/row/src/row";
    import ElCol from "element-ui/packages/col/src/col";
    export default {
        components: {
            ElCol,
            ElRow},
        name: 'home',
        data() {
            return {

            }
        },
        methods: {

        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
    #home {
        background-color: #f9fafc;
        height: 100%;
    }
    .el-row {
        margin-bottom: 20px;
        &:last-child {
            margin-bottom: 0;
        }
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #303133;
    }
    .grid-content {
        color: #f9fafc;
        min-height: 36px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
    .left-head {
        width: 100%;
        height: 60px;
    }
    .left-list {
        background-color: #cc85d9;
        width: 100%;
        position: absolute;
        top: 60px;
        bottom: 0px;
        left: 0px;
    }
</style>