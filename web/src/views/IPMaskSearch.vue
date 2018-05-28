<template>
    <section id="ipMaskChart">
        <el-card shadow="hover">
            <div class="ip-input-main">
                <div class="ip-input">
                    <el-input size="large" class="input-style" v-model="inputObj.input1"></el-input>
                    <span class="point-style">.</span>
                    <el-input size="large" class="input-style" v-model="inputObj.input2"></el-input>
                    <span class="point-style">.</span>
                    <el-input size="large" class="input-style" v-model="inputObj.input3"></el-input>
                    <span class="point-style">.</span>
                    <el-input size="large" class="input-style" v-model="inputObj.input4"></el-input>
                    <span class="line-style">/</span>
                    <el-input size="large" class="input-style" v-model="inputObj.input5"></el-input>
                    <el-button @click="searchData" icon="el-icon-search" class="search-style"></el-button>
                    <div class="export-div-style">
                        <el-radio-group v-model="radio2">
                            <el-radio :label="1">未用</el-radio>
                            <el-radio :label="2">已用</el-radio>
                        </el-radio-group>
                        <span class="export-style">
                        <i class="fa fa-2x fa-sign-out"></i>
                    </span>
                    </div>
                </div>
            </div>
            <div :class="legalInput ? ipDetailOpen : ipDetailClose">
                <p class="ip-info">B类地址</p>
                <p class="ip-info">&nbsp;</p>
                <p class="ip-info">网络地址：158.74.56.0</p>
                <p class="ip-info">可用IP数：254台</p>
                <p class="ip-info">掩 码：255.255.255.0</p>
                <p class="ip-info">第一可用：158.74.56.1</p>
                <p class="ip-info">广播地址：158.74.56.255</p>
                <p class="ip-info">最后可用：158.74.56.254</p>
                <p class="ip-info">已用数量：523</p>
                <p class="ip-info">未用数量：25684</p>
            </div>
            <div class="record-button">
                <el-button>记录网段</el-button>
            </div>
        </el-card>
        <div class="ip-table" ref="ipTable">
            <el-table :data="tableData" @cell-mouse-enter="cellMouseEnter" style="width: 100%" max-height="480">
                <el-table-column prop="id" label="#"></el-table-column>
                <el-table-column prop="ipAddr" label="IP地址"></el-table-column>
                <el-table-column prop="computer" label="计算机名"></el-table-column>
                <el-table-column prop="collectTime" label="采集时间" sortable></el-table-column>
                <el-table-column prop="operate" label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

    </section>
</template>

<script>
    export default {
        data() {
            return {
                inputObj: {
                    input1: '',
                    input2: '',
                    input3: '',
                    input4: '',
                    input5: '',
                },
                radio2: 3,
                legalInput: true,
                isload: false,
                ipDetailOpen: 'ip-detail-open clearfix',
                ipDetailClose: 'ip-detail-close',
                tableData: []
            }
        },
        created: function () {
            this.getTableeData(1, 50);
        },
        methods: {
            getTableeData: function (start, end) {
                for (let i = start; i < end; i++){
                    this.tableData.push({id: i, ipAddr: '127.0.0.1', computer: 'liwenhao'+'_'+i, collectTime: new Date().toDateString()});
                }
            },
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            },
            searchData: function () {
                console.log("searchData...");
            },
            cellMouseEnter: function (row) {
                if(!this.isload && row.id > this.tableData.length-10) {
                    this.isload = true; // 锁住，防止并发
                    this.getTableeData(this.tableData.length, this.tableData.length + 50);
                    console.log('触发懒加载。。。');
                }
                this.isload = false;
            }
        }
    }
</script>

<style scoped>
    #ipMaskChart {
        padding: 20px;
    }
    .ip-input-main {
        position: relative;
        width: 100%;
        height: 60px;
    }
    .ip-input {
        position: absolute;
        width: 100%;
        height: 40px;
        top: 50%;
        transform: translateY(-50%);
        text-align: center;
    }
    .ip-detail-open {
        display: block;
        margin-top: 20px;
        border-top: 1px solid #ebeef5;
        min-height: 50px;
        padding: 10px 30% 0;
    }
    .record-button{
        margin-top: 10px;
        margin-left: 30%;
    }
    .record-button button{
        width: 50%;
    }
    .ip-detail-close {
        display: none;
    }
    .ip-info {
        display: inline-block;
        float: left;
        width: 50%;
        font-size: 15px;
        line-height: 35px;
        letter-spacing: 2px;
        font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
    }
    .input-style {
        width: 65px;
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        vertical-align:middle;
        display:inline-block;
    }
    .point-style {
        font-size: 20px;
        font-weight: bold;
        padding-bottom: 10px;
        margin: 0 10px;
        vertical-align:middle;
        display:inline-block;
    }
    .line-style {
        font-size: 20px;
        font-weight: bold;
        vertical-align:middle;
        display:inline-block;
        margin: 0 10px;
    }
    .search-style {
        width: 65px;
        vertical-align:middle;
        display:inline-block;
        margin: 0 20px;
    }
    .export-div-style {
        display: inline-block;
        padding-left: 20px;
        border-radius: 5px;
        border: 1px solid #ebeef5;
    }
    .export-style {
        display: inline-block;
        width: 65px;
        height: 38px;
        vertical-align:middle;
        position: relative;
        margin-left: 10px;
    }
    .export-style i {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    .ip-table {
        margin-top: 20px;
        border-radius: 5px;
    }
</style>