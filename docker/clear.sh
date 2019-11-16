#!/bin/bash

docker stop oauth
docker stop mysql-oauth
docker stop redis-oauth
docker rm oauth
docker rm mysql-oauth
docker rm redis-oauth
docker network rm oauth-net

rm -rf ./mysql/data
mkdir -p ./mysql/data

rm -rf ./redis/data
mkdir -p ./redis/data