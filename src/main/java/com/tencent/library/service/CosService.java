package com.tencent.library.service;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.COSVersionSummary;
import com.qcloud.cos.model.ListVersionsRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.VersionListing;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.utils.StringUtils;
import com.tencent.library.bean.CosObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Markjrzhang
 * @Date 2021/11/26 22:36
 */
@Service
public class CosService {

    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.file.dir}")
    private String dir;

    @Autowired
    private COSCredentials cred;
    @Autowired
    private ClientConfig clientConfig;

    public Boolean upload(MultipartFile file) throws IOException {
        COSClient client = new COSClient(cred, clientConfig);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        PutObjectResult putObject = client.putObject(bucketName, dir + file.getOriginalFilename(), file.getInputStream(), objectMetadata);
        String eTag = putObject.getETag();
        return !StringUtils.isNullOrEmpty(eTag);
    }

    public JSONObject getDocPreviewUrl(String obj, DocHtmlRequest.DocType type) throws URISyntaxException {
        COSClient client = new COSClient(cred, clientConfig);
        //1.创建任务请求对象
        DocHtmlRequest request = new DocHtmlRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucketName);
        request.setType(type);
        request.setObjectKey(dir + obj);
        //3.调用接口,获取任务响应对象
        String previewHtmlUrl = client.GenerateDocPreviewUrl(request);
        JSONObject object = new JSONObject();
        object.put("previewUrl", previewHtmlUrl);
        return object;
    }

    public ArrayList<CosObject> listAll() throws URISyntaxException {
        COSClient client = new COSClient(cred, clientConfig);
        ArrayList<CosObject> list = new ArrayList<>();
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName(bucketName);
        listVersionsRequest.setPrefix(dir);
        listVersionsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listVersionsRequest.setMaxResults(1000);
        VersionListing versionListing = null;
        do {
            versionListing = client.listVersions(listVersionsRequest);
            List<COSVersionSummary> cosVersionSummaries = versionListing.getVersionSummaries();
            CosObject cosObject;
            for (COSVersionSummary cosVersionSummary : cosVersionSummaries) {
                if (cosVersionSummary.isLatest() && !cosVersionSummary.isDeleteMarker()) {
                    cosObject = new CosObject();
                    if (!StringUtils.isNullOrEmpty(cosVersionSummary.getKey())) {
                        cosObject.setFileName(cosVersionSummary.getKey().substring(4));
                    }
                    cosObject.setSize(Long.toString(cosVersionSummary.getSize()));
                    cosObject.setLastModify(cosVersionSummary.getLastModified());
                    cosObject.setImg(getDocPreviewUrl(cosObject.getFileName(), DocHtmlRequest.DocType.png).getString("previewUrl"));
                    list.add(cosObject);
                }
            }
            String keyMarker = versionListing.getNextKeyMarker();
            String versionIdMarker = versionListing.getNextVersionIdMarker();
            listVersionsRequest.setKeyMarker(keyMarker);
            listVersionsRequest.setVersionIdMarker(versionIdMarker);
        } while (versionListing.isTruncated());
        return list;
    }

}
