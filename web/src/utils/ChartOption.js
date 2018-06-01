 export default {
    getOption: (data) => {
        let array = [];
        array.push({value: data.usedNum, name: '已使用'});
        array.push({value: data.unusedNum, name: '未使用'});
        return {
            tooltip : {
                trigger: 'item',
                formatter: "{b} : {c} ({d}%)"
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
                    name:'',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:array.sort(function (b, a) { return a.value - b.value; }),
                    roseType: 'radius',
                    itemStyle: {
                        normal: {
                            color: '#d74352',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.3)'
                        }
                    },
                    animationType: 'scale',
                    animationEasing: 'elasticOut'
                }
            ]
        }
    }
 }