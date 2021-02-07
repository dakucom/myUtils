package com.dakuzai.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:
 * @author: dakuzai
 * @time: 2021/2/7 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCustomerDailyImport {
    /**
     * 客户名称
     */
    @ExcelProperty(index = 0)
    private String customerName;

    /**
     * MIS编码
     */
    @ExcelProperty(index = 1)
    private String misCode;

    /**
     * 月度滚动额
     */
    @ExcelProperty(index = 3)
    private BigDecimal monthlyQuota;

    /**
     * 最新应收账款余额
     */
    @ExcelProperty(index = 4)
    private BigDecimal accountReceivableQuota;

    /**
     * 本月利率(年化）
     */
    @ExcelProperty(index = 5)
    private BigDecimal dailyInterestRate;
}
