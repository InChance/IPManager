<template>
    <div id="home">
        <el-row>
            <el-col :span="3">
                <div class="grid-content bg-purple-dark">
                    <div class="left-head">
                        <div class="left-head-li">
                            <i class="fa fa-skyatlas" aria-hidden="true"></i>
                            <span>IP地址管理系统</span>
                        </div>
                    </div>
                    <div class="left-list">
                        <ul class="left-list-ul">
                            <li class="left-list-li">
                                <p class="sliding-middle-out">
                                    <i class="fa fa-pie-chart" aria-hidden="true"></i>
                                    <span>网段详情</span>
                                </p>
                            </li>
                            <li class="left-list-li">
                                <p class="sliding-middle-out">
                                    <i class="fa fa-search" aria-hidden="true"></i>
                                    <span>IP地址查询</span>
                                </p>
                            </li>
                            <li class="left-list-li">
                                <p class="sliding-middle-out">
                                    <i class="fa fa-envira" aria-hidden="true"></i>
                                    <span>IP地址采集</span>
                                </p>
                            </li>
                        </ul>
                    </div>
                </div>
            </el-col>
            <el-col :span="21">
                <div class="grid-content">
                    <div class="right-head">
                           123
                    </div>
                    <hr class="right-head-hr"/>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import ElRow from "element-ui/packages/row/src/row";
    import ElCol from "element-ui/packages/col/src/col";
    export default {
        data() {
            return {
                msg : 'null'
            }
        },
        components: {
            ElCol,
            ElRow
        },
        created: function () {
            this.getData();
        },
        methods: {
            getData : function () {
                let _this = this;
                this.$http.get("/v1/hello").then( function (response) {
                    _this.msg = response.data;
                });
            }
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
    .left-head-li,.left-list-li i {
        padding-left:0;
        vertical-align:middle;
        display:inline-block;
        float: none;
    }
    .left-head-li,.left-list-li img{
        padding-left:0;
        vertical-align:middle;
        display:inline-block;
        float: none;
    }
    .left-head-li, .left-list-li span{
        padding-left: 0;
        float: none;
        display:inline-block;
        vertical-align:middle;
    }
    .left-list-ul {
        display:table;
        margin: 0 auto;
        padding-top: 10px;
    }
    .left-list-li {
        padding: 8px 5px 8px 5px;
        font-size: 15px;
        color: #f9fafc;
    }
    .sliding-middle-out {
        display: inline-block;
        position: relative;
        cursor: pointer;
    }
    .sliding-middle-out:after {
        content: '';
        display: block;
        margin: auto;
        height: 2px;
        width: 0;
        background: transparent;
        transition: width .3s ease, background-color .3s ease;
    }
    .sliding-middle-out:hover:after {
        width: 100%;
        background: red;
    }
    .right-head {
        width: 100%;
        height: 59px;
        font-size: 12px;
    }
    .right-head-hr {
        background-color: rgba(143, 147, 146, 0.24);
    }
</style>