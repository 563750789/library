# library
>导语
> 腾讯云对象存储 COS 依托于 数据万象（Cloud Infinite，CI）的文档预览能力，集成了一套文档在线预览解决方案。本文简述了文库场景的接入方案，助力业务根据自身场景快速接入COS文档预览。
## 背景
数据万象的文档预览服务提供了便捷的接入方式，用户通过简单的url参数拼接或引入SDK就可以快速上手。由于文档预览常用于知识库、在线教育等场景，本项目搭建了简易的文库框架，并集成了文档预览的相关SDK。用户仅需部署代码后修改特定参数，即可获得开箱即用的文库系统。

## 文库效果
[在线demo](http://121.5.66.217/#/) 

本项目实现了可交互的前端页面，用户可在主页选择上传本地文档或浏览文库中的现有文档（文档封面通过数据万象文档转码功能生成），点击打开后即调用数据万象文档HTML预览服务，实现文档的云端存储以及在线快速查看。

## 配置内容获取指引

1.注册腾讯云账号并获取秘钥得到sid，skey。
2.开通对象存储（COS）服务，创建bucket，在bucket中开启文档处理功能。
3.获取bucket的region信息以及填入BucketName。

## 接口描述
### 1.上传文档
上传文档接口，对上传文档进行处理，这里配置文件中对文件的大小做了限制。
```
spring.servlet.multipart.max-file-size=200MB
spring.servlet.multipart.max-request-size=200MB
```
### 2.获取文档列表
获取文档列表接口，获取指定目录下的所有的cos对象。接口较为简略，在前端做了分页效果。有需要可以进行参数控制分页等操作。
```
#指定的目录位置
cos.file.dir=doc/
```
这里获取了文档首页的图片以用作前端展示文档内容。（文档支持转为html或图片，这里转为图片生成文档首页内容图）


### 3.获取预览url
获取指定文件的预览url，例如
```
https://markjrzhang-1251704708.cos.ap-chongqing.myqcloud.com/doc/文档预览.pptx?ci-process=doc-preview&dsttype=html&sign=xxx
```
前端对于预览url的处理就更加简单，只需要iframe引入即可。 

## 参考资料
[秒级接入、效果满分的文档预览方案——COS文档预览](https://mp.weixin.qq.com/s/plqLa0qBQlUAF1Ic2XqaZg) 
