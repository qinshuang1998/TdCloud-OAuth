# TdCloud-OAuth

## 南邮通达学院 OAuth2.0的校内授权中心

![](https://img.shields.io/github/license/qinshuang1998/TdCloud-OAuth)
![](https://img.shields.io/github/v/release/qinshuang1998/TdCloud-OAuth)

[开发者文档请移步 ✈](https://github.com/qinshuang1998/TdCloud-OAuth/wiki)

### 1.快速部署方案

**1）Docker(推荐方案)**

以Centos为例，首先安装Docker社区版环境，如已安装则可跳到步骤4继续

1. 卸载旧版本docker(可选)：

```shell
# 较旧的 Docker 版本称为 docker 或 docker-engine 。如果已安装这些程序，请卸载它们以及相关的依赖项。
$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```
2. 设置仓库

```shell
# 安装所需的软件包。yum-utils 提供了 yum-config-manager ，并且 device mapper 存储驱动程序需要 device-mapper-persistent-data 和 lvm2。
$ sudo yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
```

```shell
# 使用以下命令来设置稳定的仓库。
$ sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
```
3. 从yum安装

```shell
$ sudo yum install docker-ce docker-ce-cli containerd.io
```
```shell
# 此处针对可能出现的linux配置问题，需要进行自查，否则会影响后续构建
1. 解决创建容器的时候报错WARNING: IPv4 forwarding is disabled. Networking will not work.
$ vim /usr/lib/sysctl.d/00-system.conf
然后添加如下配置：
net.ipv4.ip_forward=1
最后重启network服务：
$ systemctl restart network
```

4. 下载本git项目，进入项目下docker目录

```shell
$ git clone git@github.com:qinshuang1998/TdCloud-OAuth.git
$ cd TdCloud-OAuth/docker/
```
5. 赋予权限，运行脚本，一键构建

```shell
$ chmod 777 build.sh && ./build.sh
```
6. 测试访问 - http://{IP}:8080

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

