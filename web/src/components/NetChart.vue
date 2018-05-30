<template>
    <section id="netChart">
        <ul>
            <li class="chart-item el-card is-hover-shadow" v-for="i in 3">
                <span class="ip-mask">173.85.0.0 / 24</span>
                <span :id="'myChart_'+i" class="ip-chart"></span>
                <span class="ip-used"><span class="ip-used-num">57</span> <br> 网段剩余可用IP数量 </span>
            </li>
        </ul>
    </section>
</template>

<script>
    import echarts from 'echarts';
    import API     from '../remote/api';
    export default {
        data: () => {
            return {

            }
        },
        mounted(){
            this.drawLine();
        },
        methods: {
            drawLine(){


                API.getCharList();
                API.deleteChart({mask:'192.0.0.1'});
                API.saveMask({netAddress: '192.125.37.252', maskAddress: '255.255.255.0'});
                API.calculateIP({ip: '192.125.37.252', mask: '24'});



                for(let k = 1; k <= 3; k++){
                    // 基于准备好的dom，初始化echarts实例
                    let myChart = echarts.init(document.getElementById('myChart_'+k));
                    // 绘制图表
                    let option = {
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },

                        visualMap: {
                            show: false,
                            min: 80,
                            max: 600,
                            inRange: {
                                colorLightness: [0, 1]
                            }
                        },
                        series : [
                            {
                                name:'访问来源',
                                type:'pie',
                                radius : '55%',
                                center: ['50%', '50%'],
                                data:[
                                    {value:335, name:'直接访问'},
                                    {value:310, name:'邮件营销'},
                                    {value:274, name:'联盟广告'},
                                    {value:235, name:'视频广告'},
                                    {value:400, name:'搜索引擎'}
                                ].sort(function (a, b) { return a.value - b.value; }),
                                roseType: 'radius',
                                label: {
                                    normal: {
                                        textStyle: {
                                            color: 'rgba(0, 0, 0, 0.3)'
                                        }
                                    }
                                },
                                labelLine: {
                                    normal: {
                                        lineStyle: {
                                            color: 'rgba(0, 0, 0, 0.3)'
                                        },
                                        smooth: 0.2,
                                        length: 10,
                                        length2: 20
                                    }
                                },
                                itemStyle: {
                                    normal: {
                                        color: '#d75c81',
                                        shadowBlur: 200,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                },

                                animationType: 'scale',
                                animationEasing: 'elasticOut'
                            }
                        ]
                    };
                    myChart.setOption(option);
                }
            }
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
</style>