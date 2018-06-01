import * as Request from './request'

export default {

    addNewIPAddress     : params => Request.POST('/ip/collect', params),            // 增加新的IP地址记录
    updateIpInfo        : params => Request.PUT('/ip/update', params),              // 更新IP信息
    deleteIPRecord      : params => Request.DELETE('/ip/delete', params),           // 删除IP记录
    calculateIP         : params => Request.FORM_POST('/ip/calculate', params),     // 计算网段信息
    saveIPMask          : params => Request.POST('/mask/save', params),             // 保存子网掩码
    getIPMaskChartInfo  : params => Request.GET('/mask/chart', params),             // 列出掩码图表信息
    deleteMask          : params => Request.DELETE('/mask/delete', params),         // 删除掩码图表
    exportDataByExcel   : params => Request.GET('/excel/export', params),           // Excel导出数据

}

