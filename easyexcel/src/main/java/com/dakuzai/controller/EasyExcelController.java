package com.dakuzai.controller;

import com.alibaba.excel.EasyExcel;
import com.dakuzai.config.CustomerDailyImportListener;
import com.dakuzai.config.StringConverter;
import com.dakuzai.pojo.ReqCustomerDailyImport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

}
