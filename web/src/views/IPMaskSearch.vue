<template>
    <section id="ipMaskChart">
        <el-card shadow="hover">
            <div class="ip-input-main">
                <div class="ip-input">
                    <el-input size="large" class="input-style" v-model="ipNums[0]"></el-input>
                    <span class="point-style">.</span>
                    <el-input size="large" class="input-style" v-model="ipNums[1]"></el-input>
                    <span class="point-style">.</span>
                    <el-input size="large" class="input-style" v-model="ipNums[2]"></el-input>
                    <span class="point-style">.</span>
                    <el-input size="large" class="input-style" v-model="ipNums[3]"></el-input>
                    <span class="line-style">/</span>
                    <el-input size="large" class="input-style" v-model="ipNums[4]"></el-input>
                    <div class="export-div-style">
                        <el-radio-group v-model="radio2">
                            <el-radio :label="0">未用</el-radio>
                            <el-radio :label="1">已用</el-radio>
                        </el-radio-group>
                        <span class="export-style" @click="exportExcel(ipNums, radio2)">
                            <i class="fa fa-2x fa-sign-out"></i>
                        </span>
                    </div>
                </div>
            </div>
            <div :class="legalInput ? ipDetailOpen : ipDetailClose">
                <p class="ip-info">{{ipDetailDto.ipType}}</p>
                <p class="ip-info">&nbsp;</p>
                <p class="ip-info">网络地址：{{ipDetailDto.netAddress}}</p>
                <p class="ip-info">可用IP数：{{ipDetailDto.usableCount}}台</p>
                <p class="ip-info">掩 码：{{ipDetailDto.maskAddress}}</p>
                <p class="ip-info">第一可用：{{ipDetailDto.firstUsable}}</p>
                <p class="ip-info">广播地址：{{ipDetailDto.broadcast}}</p>
                <p class="ip-info">最后可用：{{ipDetailDto.lastUsable}}</p>
                <p class="ip-info">已用数量：{{ipDetailDto.usedCount}}</p>
                <p class="ip-info">未用数量：{{ipDetailDto.unusedCount}}</p>
                <div class="record-button">
                    <el-button>记录网段</el-button>
                </div>
            </div>
        </el-card>
        <div class="ip-table" ref="ipTable">
            <el-table :data="tableData" style="width: 100%" max-height="480">
                <el-table-column type="index" label="#"></el-table-column>
                <el-table-column prop="ip" label="IP地址"></el-table-column>
                <el-table-column prop="name" label="计算机名"></el-table-column>
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
    import API      from '../remote/api'
    import TimeUtil from '../utils/timeUtil'
    export default {
        data() {
            return {
                ipNums: ['','','','',''],
                radio2: 1, // 0 未用， 1 已用
                legalInput: false,
                isload: false,
                ipDetailOpen: 'ip-detail-open clearfix',
                ipDetailClose: 'ip-detail-close',
                tableData: [],
                ipDetailDto: {}
            }
        },
        mounted(){
            let params = this.$route.params;
            if(!(params.ip && params.mask)){
                this.legalInput = false;
                return;
            }

            // 填充IP和掩码
            let str = params.ip.split(".");
            str.push(params.mask);
            this.ipNums = str;

            // 请求IP计算的数据
            this.calculateIP(this, {ip:params.ip, mask:params.mask});
        },
        watch: {
            ipNums(newVal){
                console.log("newVal:" + newVal);
                let result = this.validIpAndMask(newVal, '', '');
                if(!result.isValid){
                    this.$notify.error({title:'错误', message: "ip地址或掩码格式错误！"});
                }else{
                    this.calculateIP(this, {ip: result.ip, mask: result.mask});
                }
            }
        },
        methods: {
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            },
            searchData: function () {
                console.log("searchData...");
            },
            calculateIP(self, params) {
                API.calculateIP(params).then(data=>{
                    console.log(data);
                    self.ipDetailDto = data.body.ipDetailDto;
                    self.tableData = data.body.listDto;
                    self.legalInput = true;
                }).catch(ex=>{
                    self.ipDetailDto = {};
                    self.tableData = [];
                    self.legalInput = false;
                    self.$notify.error({title:'错误', message:ex.message});
                });
            },
            validIpAndMask(newVal, ip, mask) {
                let isValid = newVal && newVal.length === 5;
                newVal.forEach((value, index)=>{
                    isValid = isValid && !(value === "" || value === null);
                    isValid = isValid && !isNaN(value);
                    if( index === 4 ){
                        mask = value;
                    }else{
                        ip += value + "."
                    }
                });
                ip = ip.substring(0, ip.length - 1);
                return {isValid:isValid, ip:ip, mask:mask};
            },
            exportExcel(ipNums, type) {
                let result = this.validIpAndMask(ipNums, '', '');
                if(!result.isValid){
                    this.$notify.error({title:'错误', message: "ip地址或掩码格式错误！"});
                }else{
                    if( this.ipDetailDto.unusedCount > 150000 ){
                        this.$notify.warning({title:'警告', message: "导出数据超过15w行，只导出前15w行数据！"});
                    }
                    API.exportDataByExcel({ip:result.ip, mask:result.mask, type:type});
                }
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
    .record-button{
        display: inline-block;
        float: left;
        width: 85%;
        margin-top: 10px;
    }
    .record-button button{
        width: 100%;
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
        margin-left: 20px;
        padding-left: 20px;
        border-radius: 5px;
        border: 1px solid #dee1e7;
    }
    .export-style {
        display: inline-block;
        width: 65px;
        height: 38px;
        vertical-align:middle;
        position: relative;
        margin-left: 10px;
        cursor: pointer;
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