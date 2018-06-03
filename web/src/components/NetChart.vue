<template>
    <section id="netChart">
        <ul>
            <li class="chart-item el-card is-hover-shadow" v-for="(ch, index) in chart_list">
                <span class="ip-mask">{{ch.netIp}} / {{ch.mask}}</span>
                <router-link :to="{name:'search', params:{ip: ch.netIp, mask: ch.mask}}">
                    <span :id="'chart'+(index+1)" class="ip-chart"></span>
                </router-link>
                <span class="ip-used"><span class="ip-used-num">{{ch.unusedNum}}</span> <br> 网段剩余可用IP数量 </span>
                <span class="icon-delete" @click="deleteMask(ch.netIp)"><i class="el-icon-delete"></i></span>
            </li>
        </ul>
    </section>
</template>

<script>
    import echarts from 'echarts';
    import API     from '../remote/api';
    import ChartOption  from '../utils/ChartOption';
    export default {
        data() {
            return {
                chart_list: []
            }
        },
        mounted(){
            this.reloadChartData();
        },
        updated(){ // 重新渲染都会调用
            this.drawPhoto(this.chart_list);
        },
        methods: {
            reloadChartData(){
                let self = this;
                API.getIPMaskChartInfo().then(data => {
                    self.chart_list = data.body;
                }).catch(ex=>{
                    self.chart_list = [];
                    self.$notify.error({title:'错误', message:ex.message});
                });
            },
            drawPhoto(chart_list){
                for(let k = 0; k < chart_list.length; k++){
                    // 基于准备好的dom，初始化echarts实例
                    let myChart = echarts.init( document.getElementById('chart'+(k+1)) );
                    // 绘制图表
                    let option = ChartOption.getOption(chart_list[k]);
                    myChart.setOption(option);
                }
            },
            deleteMask(ip){
                let self = this;
                let params = {ip: ip};
                this.$confirm('确定删除该网段IP？', '删除', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    API.deleteMask(params).then(data => {
                        this.reloadChartData();
                        self.$notify.success({title: '错误', message: '删除成功！'});
                    }).catch(ex => {
                        self.$notify.error({title: '错误', message: ex.message});
                    });
                }).catch(ex=>{});
            },
        }
    }
</script>

<style scoped>
    #netChart {
        width: 100%;
    }
    .chart-item {
        height: 300px;
        margin-bottom: 20px;
        position: relative;
    }
    .ip-mask {
        display: inline-block;
        position: absolute;
        width: 48%;
        font-size: 70px;
        text-align: center;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
    }
    .ip-chart {
        display: inline-block;
        position: absolute;
        width: 25%;
        height: 100%;
        text-align: center;
        left: 48%;
        top: 50%;
        transform: translateY(-50%);
    }
    .ip-used {
        display: inline-block;
        position: absolute;
        width: 27%;
        font-size: 18px;
        letter-spacing:3px;
        color: #1e1f21;
        text-align: center;
        left: 73%;
        top: 50%;
        transform: translateY(-50%);
    }
    .ip-used-num {
        font-size: 100px;
        line-height: 100px;
    }
    .icon-delete {
        display: inline-block;
        position: absolute;
        width: 50px;
        height: 50px;
        top: 0;
        right: 0;
        font-size: 32px;
        cursor: pointer;
        background-color: #f1eaff;
    }
</style>