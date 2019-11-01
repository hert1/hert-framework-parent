#!/bin/bash

#守护进程
source /etc/profile
echo "PATH="$PATH
echo "JAVA_HOME="$JAVA_HOME
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=${DEPLOY_DIR}/conf

SERVER_NAME=`sed '/application.name/!d;s/.*=//' conf/app.properties | tr -d '\r'`
LOGS_DIR=`sed '/log.dir/!d;s/.*=//' conf/app.properties | tr -d '\r'`
MAIN_CLASS=`sed '/main.class/!d;s/.*=//' conf/app.properties | tr -d '\r'`

PID=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep |awk '{print $2}'`
if [ -z "$PID" ]; then
	curTime=`date +'%Y-%m-%d %H:%M:%S'`
    echo "ERROR:$curTime The $SERVER_NAME was dead!!! start!!!"
	cd $BIN_DIR
	sh ./start.sh
fi



