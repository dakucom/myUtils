package com.dakuzai.hutool.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: dakuzai
 * @time: 2021/2/5 15:54
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1l;

    private String name;

    private int age;

    private Date birthDay;


}
