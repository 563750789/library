package com.tencent.library.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author Markjrzhang
 * @Date 2021/11/27 0:57
 */
@Data
public class CosObject {
    private String fileName;
    private Date lastModify;
    private String size;
    private String img;
}
