package com.dakuzai.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 导出类
 * @author: dakuzai
 * @time: 2021/2/7 14:38
 */
@Data
@Builder
public class RespCustomerDailyExport {

    @ExcelProperty("客户编码")
    private String customerName;

    @ExcelProperty("MIS编码")
    private String misCode;

    @ExcelProperty("月度滚动额")
    private BigDecimal monthlyQuota;

    @ExcelProperty("最新应收账款余额")
    private BigDecimal accountReceivableQuota;

    @NumberFormat("#.##%")
    @ExcelProperty("本月利率(年化）")
    private BigDecimal dailyInterestRate;
}
