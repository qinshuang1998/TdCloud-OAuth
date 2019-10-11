# TdCloud-OAuth

## 南邮通达学院 OAuth2.0的校内授权中心

![](https://img.shields.io/github/license/qinshuang1998/TdCloud-OAuth)
![](https://img.shields.io/github/v/release/qinshuang1998/TdCloud-OAuth)

### 1.快速部署

**1）docker（待补充）**

> 待补充

**2）一般部署**

- 准备环境

> 1. MySQL 5.7
> 2. Java 8
> 3. Redis

- 下载最新release版本或自行下载源码构建jar包

> https://github.com/qinshuang1998/TdCloud-OAuth/releases

- 解压程序
- 下载sql文件（oauth.sql），并导入到MySQL数据库中

> https://github.com/qinshuang1998/TdCloud-OAuth/releases/download/1.0.0-SNAPSHOT/oauth.sql

- 下载正方验证码训练集图片，并解压到自定义路径

> https://github.com/qinshuang1998/TdCloud-OAuth/releases/download/1.0.0-SNAPSHOT/OCR.tar

- 在之前解压的程序的**config**目录中的**application.properties**文件配置程序相关参数

> 参照文件中已有的样例配置，包括数据库连接、Redis连接、正方教务验证码训练集路径等信息

- 运行jar程序

> nohup java -jar oauth-1.0.0-SNAPSHOT.jar >> output.log 2>&1 &

```shell
# 查看程序是否在后台运行
ps -ef | grep oauth
```

