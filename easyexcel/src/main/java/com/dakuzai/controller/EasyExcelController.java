package com.dakuzai.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.dakuzai.config.CustomerDailyImportListener;
import com.dakuzai.config.StringConverter;
import com.dakuzai.pojo.ReqCustomerDailyImport;
import com.dakuzai.pojo.RespCustomerDailyExport;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

/**
 * @description:
 * @author: dakuzai
 * @time: 2021/2/7 11:12
 */
@RestController
public class EasyExcelController {

    @PostMapping("/import")
    public void importCustomerDaily(@RequestParam MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<ReqCustomerDailyImport> reqCustomerDailyImports = EasyExcel.read(inputStream)
                // 这个转换是成全局的， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                .registerConverter(new StringConverter())
                // 注册监听器，可以在这里校验字段
                .registerReadListener(new CustomerDailyImportListener())
                .head(ReqCustomerDailyImport.class)
                .sheet()
                .headRowNumber(2)
                .doReadSync();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        // 生成数据
        List<RespCustomerDailyExport> respCustomerDailyImports = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            RespCustomerDailyExport respCustomerDailyImport = RespCustomerDailyExport.builder()
                    .misCode(String.valueOf(i))
                    .customerName("customerName" + i)
                    .monthlyQuota(new BigDecimal(String.valueOf(i)))
                    .accountReceivableQuota(new BigDecimal(String.valueOf(i)))
                    .dailyInterestRate(new BigDecimal(String.valueOf(i))).build();
            respCustomerDailyImports.add(respCustomerDailyImport);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("导出", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), RespCustomerDailyExport.class)
                .sheet("sheet0")
                // 设置字段宽度为自动调整，不太精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(respCustomerDailyImports);
    }

}
