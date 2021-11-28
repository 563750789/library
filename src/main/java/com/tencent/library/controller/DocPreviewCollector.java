package com.tencent.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.tencent.library.service.CosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * @Author Markjrzhang
 * @Date 2021/11/26 22:34
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("doc")
public class DocPreviewCollector {

    @Autowired
    private CosService cosService;

    @GetMapping("getPreviewUrl")
    public JSONObject getDocPreviewUrl(String fileName) throws URISyntaxException {
        return cosService.getDocPreviewUrl(fileName, DocHtmlRequest.DocType.html);
    }


    @GetMapping("test")
    public String test() {
        return "success";
    }
}
