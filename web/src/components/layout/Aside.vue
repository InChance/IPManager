<template>
    <section id="aside">
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
                        <i class="fa fa-pie-chart link-color" aria-hidden="true"></i>
                        <router-link to="/home"><span class="link-color">网段详情</span></router-link>
                    </p>
                </li>
                <li class="left-list-li">
                    <p class="sliding-middle-out">
                        <i class="fa fa-search link-color" aria-hidden="true"></i>
                        <router-link to="/home/search"><span class="link-color">IP地址查询</span></router-link>
                    </p>
                </li>
                <li class="left-list-li">
                    <p class="sliding-middle-out">
                        <i class="fa fa-envira link-color" aria-hidden="true"></i>
                        <router-link to="/home/collect"><span class="link-color">IP地址采集</span></router-link>
                    </p>
                </li>
            </ul>
        </div>
    </section>
</template>

<style scoped>
    #aside {
        height: 100%;
    }
    .left-head {
        width: 100%;
        height: 60px;
        background-color: #313131;
        position: relative;
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
        background: #d75c81;
    }
    .link-color {
        color: #f9fafc;
    }
</style>