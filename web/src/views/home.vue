<template>
    <div id="home">
        <el-container>
            <el-aside width="210px">
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
            </el-aside>
            <el-main>
                <div class="grid-content">
                    <div class="right-head">
                        <div class="list-switch-icon">
                            <i class="fa fa-2x fa-outdent switch-icon"></i>
                        </div>
                        <div class="help-word">
                            <p>帮助文档</p>
                        </div>
                        <div class="account-info">
                            <p>小明</p>
                        </div>
                    </div>
                    <hr class="right-head-hr"/>
                </div>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    export default {
        data: () => {
            return {
                isCollapse: true
            }
        },
        components: {
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

<style rel="stylesheet/scss" lang="scss" scoped>
    #home {
        background-color: #f9fafc;
        height: 100%;
    }
    .el-container {
        height: 100%;
    }
    .el-aside {
        height: 100%;
    }
    .el-main {
        margin: 0;
        padding: 0;
        height: 100%;
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
        font-size: 14px;
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
        font-size: 14px;
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
    .list-switch-icon {
        display: inline-block;
        position: relative;
        width: 80px;
        height: 100%;
        text-align: center;
        float: none;
        color: #313131;
        border-right: 1px solid rgba(69, 79, 58, 0.24);
    }
    .list-switch-icon .switch-icon {
        position: absolute;
        cursor: pointer;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    .list-switch-icon .switch-icon:hover {
        color: #4d4d4d;
    }
    .help-word {
        display: inline-block;
        width: 100px;
        height: 21px;
        position: absolute;
        top: 50%;
        right: 100px;
        font-size: 14px;
        text-align: center;
        background-color: #999999;
    }
    .help-word p {
        transform: translateY(-50%);
    }
    .account-info {
        display: inline-block;
        width: 100px;
        height: 21px;
        position: absolute;
        top: 50%;
        right: 0;
        font-size: 14px;
        text-align: center;
        background-color: #c0c0c0;
    }
    .account-info p {
        transform: translateY(-50%);
    }
    .right-head {
        position: relative;
        width: 100%;
        height: 59px;
        font-size: 12px;
    }
    .right-head-hr {
        background-color: rgba(143, 147, 146, 0.24);
    }
</style>