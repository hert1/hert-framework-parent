#!/bin/bash
#export PATH=$PATH:/home/crm/jdk1.8.0_141/bin
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=${DEPLOY_DIR}/conf

SERVER_NAME=`sed '/application.name/!d;s/.*=//' conf/app.properties | tr -d '\r'`
LOGS_DIR=`sed '/log.dir/!d;s/.*=//' conf/app.properties | tr -d '\r'`

PID=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  |awk '{print $2}'`
if [ -z "${PID}" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
    exit 1
fi

if [ "$1" != "skip" ]; then
    sh ${BIN_DIR}/dump.sh
fi

echo -e "Stopping the $SERVER_NAME ...\c"
kill ${PID} > ${LOGS_DIR}/stdout.log 2>&1

COUNT=1
while [ $COUNT -gt 0 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  | awk '{print $2}' | wc -l`
done

echo "OK!"
echo "PID: ${PID}"
