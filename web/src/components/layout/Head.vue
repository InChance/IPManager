<template>
    <section id="head">
        <div class="fixed-head">
            <div class="right-head">
                <div class="list-switch-icon">
                    <i class="fa fa-2x fa-outdent switch-icon"></i>
                </div>
                <div class="help-word">
                    <p>帮助文档</p>
                </div>
                <div class="account-info">
                    <p class="account-info-pointer">小明 <i class="el-icon-caret-bottom"></i></p>
                </div>
            </div>
            <hr class="right-head-hr"/>
            <div class="breadcrumb">
                <span>{{curAddress}}</span>
            </div>
        </div>
    </section>
</template>

<script>
    export default {
        props: {
            curAddress: String
        }
    }
</script>

<style scoped>
    .fixed-head {
        position: fixed;
        z-index: 999;
        width: 90%;
        width: -moz-calc(100% - 210px);
        width: -webkit-calc(100% - 210px);
        width: calc(100% - 210px);
        height: 101px;
        background-color: white;
    }
    .list-switch-icon {
        display: inline-block;
        position: relative;
        width: 80px;
        height: 100%;
        text-align: center;
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
        height: 100%;
        position: absolute;
        right: 100px;
        font-size: 14px;
        text-align: center;
        border-right: 1px solid rgba(69, 79, 58, 0.24);
    }
    .help-word p {
        position: absolute;
        width: 100%;
        top: 50%;
        transform: translateY(-50%);
    }
    .account-info {
        display: inline-block;
        width: 100px;
        height: 100%;
        position: absolute;
        right: 0;
        font-size: 14px;
        text-align: center;
    }
    .account-info p {
        position: absolute;
        width: 100%;
        top: 50%;
        transform: translateY(-50%);
    }
    .account-info-pointer {
        cursor: pointer;
    }
    .right-head {
        position: relative;
        width: 100%;
        height: 59px;
        font-size: 12px;
    }
    .right-head-hr {
        height: 2px;
        background-color: rgba(68, 70, 69, 0.24);
    }
    .breadcrumb {
        height: 40px;
        border-bottom: 0.5px solid rgba(63, 63, 63, 0.24);
        position: relative;
    }
    .breadcrumb span {
        display: inline-block;
        position: relative;
        font-size: 14px;
        color: #0088cc;
        top: 50%;
        left: 20px;
        transform: translateY(-50%);
    }
</style>