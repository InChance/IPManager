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
            series : [
                {
                    name: '',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:[
                        {value: data.usedNum, name:'已使用'},
                        {value: data.unusedNum, name:'未使用'},
                    ]
                }
            ],
            color: ['rgba(226, 101, 101, 0.6)','rgba(190, 40, 40, 0.9)']
        };
    }
 }