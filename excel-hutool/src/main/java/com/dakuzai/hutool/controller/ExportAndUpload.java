package com.dakuzai.hutool.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.dakuzai.hutool.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: dakuzai
 * @time: 2021/2/5 15:51
 */
@RestController
public class ExportAndUpload {

    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response){
        List<User> list = new ArrayList<>();
        list.add(new User("zhangsan",17,new Date()));
        list.add(new User("lisi",18,new Date()));
        list.add(new User("wangwu",21,new Date()));
        list.add(new User("lulu",13,new Date()));
        list.add(new User("anqila",19,new Date()));
        list.add(new User("houyi",16, DateUtil.date(new Date())));
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("birthDay", "生日");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(2, "申请人员信息");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
            String name = "user";
        response.setHeader("Content-Disposition","attachment;filename="+name+".xls");
        ServletOutputStream out= null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // 关闭writer，释放内存
            writer.close();
        }
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
}
