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
                    <el-button :disabled="record_disabled" @click="addMask">{{record_value}}</el-button>
                </div>
            </div>
        </el-card>
        <div class="ip-table" ref="ipTable">
            <el-table :data="tableData" style="width: 100%" max-height="425">
                <el-table-column type="index" label="#"></el-table-column>
                <el-table-column prop="ip" label="IP地址"></el-table-column>
                <el-table-column prop="name" label="计算机名"></el-table-column>
                <el-table-column prop="collectTime" label="采集时间" sortable></el-table-column>
                <el-table-column prop="operate" label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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
                ipDetailDto: {},
                isRecord: 0,
                record_disabled: false,
                record_value: '记录网段'
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
        },
        watch: {
            ipNums(newVal){
                let result = this.validIpAndMask(newVal, '', '');
                if(!result.isValid){
                    this.$notify.error({title:'错误', message: "ip地址或掩码格式错误！"});
                }else{
                    this.calculateIP(this, {ip: result.ip, mask: result.mask});
                }
            }
        },
        methods: {
            handleEdit(row) {
                this.$prompt('请输入计算机名', '更新', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(({ value }) => {
                    if(!(value && jQuery.trim(value).length > 0 && value.length < 20)){
                        this.$notify.error({title: '错误', message: '计算机名称不可为空或过长！'});
                        return;
                    }
                    API.updateIpInfo({ip: row.ip, name: value}).then(data=>{
                        this.$notify.success({title:'信息', message: "更新成功！"});
                        let result = this.validIpAndMask(this.ipNums, '', '');
                        this.calculateIP(this, {ip: result.ip, mask: result.mask});
                    }).catch(ex=>{
                        self.$notify.error({title:'错误', message:ex.message});
                    });
                }).catch(()=>{
                    console.log('取消更新操作!');
                });
            },
            handleDelete(row) {
                this.$confirm('此操作将永久删除该IP, 是否继续?', '删除', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    API.deleteIPRecord({ip: row.ip}).then(data=>{
                        this.$notify.success({title:'信息', message: "删除成功！"});
                        let result = this.validIpAndMask(this.ipNums, '', '');
                        this.calculateIP(this, {ip: result.ip, mask: result.mask});
                    }).catch(ex=>{
                        self.$notify.error({title:'错误', message:ex.message});
                    });
                }).catch(()=>{
                    //console.log("取消删除操作!");
                });

            },
            calculateIP(self, params) {
                API.calculateIP(params).then(data=>{
                    self.ipDetailDto = data.body.ipDetailDto;
                    self.tableData = data.body.listDto;
                    self.legalInput = true;
                    self.isRecord = data.body.isRecord;
                    if(self.isRecord === 1){
                        self.record_disabled = true;
                        self.record_value = '网段已记录';
                    }else{
                        self.record_disabled = false;
                        self.record_value = '记录网段';
                    }
                }).catch(ex=>{
                    self.ipDetailDto = {};
                    self.tableData = [];
                    self.legalInput = false;
                    self.isRecord = 0;
                    self.record_disabled = false;
                    self.record_value = '记录网段';
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
            },
            addMask(){
                if( this.record_disabled ){
                    return;
                }
                if(this.ipDetailDto.usableCount && this.ipDetailDto.usableCount > 10000){
                    this.$notify.error({title: '错误', message: '网段范围过大，无记录意义！'});
                    return;
                }
                let ip   = this.ipDetailDto.netAddress;
                let mask = this.ipDetailDto.maskAddress;
                if(!(ip && mask)){
                    return;
                }
                API.saveIPMask({netAddress: ip, maskAddress: mask}).then(data=>{
                    this.$notify.success({title: '信息', message: '记录成功'});
                }).catch(ex=>{
                    this.$notify.error({title: '错误', message: ex.message});
                });
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