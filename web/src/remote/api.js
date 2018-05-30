import * as Request from './request'

export default {

    // 获取图标列表
    getCharList: params => Request.GET('/mask/chart', params),
    // 删除掩码图表
    deleteChart: params => Request.DELETE('/mask/delete', params),
    // 计算网段信息
    calculateIP: params => Request.FORM_POST('/ip/calculate', params),
    // 保存子网掩码
    saveMask   : params => Request.POST('/mask/save', params),

}

