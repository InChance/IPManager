import * as Request from './request'

/**
 * @module IP操作协议接口模块
 */
export default {
    /**
     * 增加新的IP地址记录<br>
     *     name: 计算机名
     * @param {json} params
     * {
     *     ip: string,
     *     name: string
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    addNewIPAddress(params) {
        return Request.POST('/ip/collect', params);
    },
    /**
     * 更新IP信息<br>
     *     ip: 参数不变
     *     name: 计算机名，将要改变的
     * @param {json} params
     * {
     *     ip: string,
     *     name: string
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    updateIpInfo(params) {
        return Request.PUT('/ip/update', params);
    },
    /**
     * 删除IP记录<br>
     * @param {json} params
     * {
     *     ip: string
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    deleteIPRecord(params) {
        return Request.DELETE('/ip/delete', params);
    },
    /**
     * 计算网段信息<br>
     *      ip: 网络地址
     *      mask: 子网掩码
     * @param {json} params
     * 格式 {
     *     ip: string,
     *     mask: string
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    calculateIP(params) {
        return Request.FORM_POST('/ip/calculate', params)
    },
    /**
     * 保存子网掩码
     *      maskAddress: 字符串类型掩码，非数字
     * @param {json} params
     * {
     *     netAddress: string,
     *     maskAddress: string
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    saveIPMask(params) {
        return Request.POST('/mask/save', params);
    },
    /**
     * 列出掩码图表信息
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    getIPMaskChartInfo() {
        return Request.GET('/mask/chart');
    },
    /**
     * 删除掩码图表
     * @param {json} params 根据掩码
     * {
     *     ip: string
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    deleteMask(params) {
        return Request.DELETE('/mask/delete', params);
    },
    /**
     * Excel导出数据
     *     type: 0 下载未使用的, type 1 下载已使用的
     * @param {json} params
     * {
     *     ip: string,
     *     mask: string,
     *     type: number
     * }
     * @return {Promise} 返回一个组装好数据的Promise对象
     */
    exportDataByExcel(params) {
        return Request.WIN_OPEN('/excel/export', params);
    },

}

