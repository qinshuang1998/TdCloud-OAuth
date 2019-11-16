#!/bin/bash

workdir=$(cd $(dirname $0); pwd)

echo "+================+"
echo "+ 检查docker环境 +"
echo -e "+================+\n"
docker -v
check_docker=$?

if [ $check_docker -gt 0 ]
then
    exit 1
fi
echo -e "\n--------ok--------\n"

echo "+================+"
echo "+ 创建docker网络 +"
echo -e "+================+\n"
docker network create -d bridge oauth-net
echo -e "\n--------ok--------\n"

echo "+================+"
echo "+ 运行MySQL容器  +"
echo -e "+================+\n"
read -p "Enter your MySQL password, please(Default is root): " password
if [ -z "$password" ];then
    password="root"
fi
docker pull mysql:5.7.28
docker run -itd --name mysql-oauth -v $workdir/mysql/conf:/etc/mysql/conf.d \
-v $workdir/mysql/data:/var/lib/mysql \
--network oauth-net -p 3306:3306 -e MYSQL_ROOT_PASSWORD=$password mysql:5.7.28
echo -e "\n--------导入sql文件(Waiting 15s)--------\n"
docker cp ./oauth.sql mysql-oauth:/oauth.sql
sleep 15
docker exec mysql-oauth bash -c "mysql -uroot -p$password < /oauth.sql"
echo -e "\n--------ok--------\n"

echo "+================+"
echo "+ 运行Redis容器  +"
echo -e "+================+\n"
docker pull redis:latest
#mkdir -p ./redis/conf
#curl https://raw.githubusercontent.com/antirez/redis/5.0/redis.conf >> ./redis/conf/redis.conf
#mkdir -p ./redis/data
docker run -itd --name redis-oauth -v $workdir/redis/conf/redis.conf:/etc/redis/redis.conf \
-v $workdir/redis/data:/data \
--network oauth-net -p 6379:6379 redis
echo -e "\n--------ok--------\n"

echo "+================+"
echo "+ 运行OAuth2 APP +"
echo -e "+================+\n"
#docker build -t oauth:1.0.0 .
docker pull qinshuang/oauth:1.0.0
mkdir -p logs
docker run -itd --name oauth --network oauth-net \
-v $workdir/config:/config \
-v $workdir/ocr:/ocr \
-v $workdir/logs:/logs \
-p 8080:8080 qinshuang/oauth:1.0.0
echo -e "\n--------ok, build successd--------"