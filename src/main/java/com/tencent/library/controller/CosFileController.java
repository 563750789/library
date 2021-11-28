package com.tencent.library.controller;

import com.tencent.library.bean.CosObject;
import com.tencent.library.service.CosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @Author Markjrzhang
 * @Date 2021/11/26 22:34
 */
@RestController()
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("cos")
public class CosFileController {

    @Autowired
    private CosService cosService;

    @PostMapping("upload")
    public Boolean upload(@RequestParam("file") MultipartFile file) throws IOException {
        return cosService.upload(file);
    }

    @GetMapping("listAll")
    public ArrayList<CosObject> listAll() throws IOException, URISyntaxException {
        return cosService.listAll();
    }

}
