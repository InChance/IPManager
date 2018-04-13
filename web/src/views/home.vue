<template>
    <div id="home">
        <el-row>
            <el-col :span="3">
                <div class="grid-content bg-purple-dark">
                    <div class="left-head">
                        <div class="left-head-li">
                            <img src="image/qq.jpg">
                            <span>IP地址管理系统</span>
                        </div>
                    </div>
                    <div class="left-list">
                        <ul class="left-list-li">
                            <li>
                                <p>
                                    <img src="image/qq.jpg">
                                    <span>网段详情</span>
                                </p>
                            </li>
                            <li>
                                <p>
                                    <img src="image/qq.jpg">
                                    <span>IP地址查询</span>
                                </p>
                            </li>
                            <li>
                                <p>
                                    <img src="image/qq.jpg">
                                    <span>IP地址采集IP地址采集</span>
                                </p>
                            </li>
                        </ul>
                    </div>
                </div>
            </el-col>
            <el-col :span="21">
                <div class="grid-content">
                    222222
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import ElRow from "element-ui/packages/row/src/row";
    import ElCol from "element-ui/packages/col/src/col";
    export default {
        components: {
            ElCol,
            ElRow
        },
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
        height: 100%;
        &:last-child {
            margin-bottom: 0;
        }
    }
    .el-col {
        height: 100%;
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #1e1f21;
    }
    .grid-content {
        height: 100%;
    }
    .left-head {
        width: 100%;
        height: 60px;
        background-color: #313131;
        position: relative;
        font-size: 12px;
        color: #f9fafc;
    }
    .left-head-li {
        width: 100%;
        position: absolute;
        top: 50%;
        font-size: 18px;
        text-align: center;
        transform: translateY(-50%);
    }
    .left-head,.left-list img{
        padding-left:0;
        vertical-align:middle;
        display:inline-block;
        float: none;
    }
    .left-head, .left-list span{
        padding-left: 0;
        float: none;
        display:inline-block;
        vertical-align:middle;
    }
    .left-list {
        text-align: center;
    }
    .left-list-li {
        margin: 5px 0 5px 0;
        color: #f9fafc;
    }
    .left-list-li p {
        display: inline-block;
        text-align: left;
    }
</style>