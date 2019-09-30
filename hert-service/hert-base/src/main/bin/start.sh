#!/bin/bash
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
if [ -n "$PID" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PID"
    exit 1
fi

LIB_DIR=${DEPLOY_DIR}/lib
LIB_JARS=`ls ${LIB_DIR}|grep .jar|awk '{print "'${LIB_DIR}'/"$0}'|tr "\n" ":"`


JAVA_OPTS="-server -Dfile.encoding=utf-8 -Duser.timezone=GMT+08 -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:${LOGS_DIR}/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:ErrorFile=${LOGS_DIR}/java_error_%p.log -XX:HeapDumpPath=${LOGS_DIR}/java_error.hprof"

JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi

JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=5010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false  "
fi

JAVA_MEM_OPTS="-Xms128m -Xmx128m -Xmn64m -Xss256k -XX:PermSize=64M -XX:MaxPermSize=128m -XX:SurvivorRatio=8"

export LD_LIBRARY_PATH=${DEPLOY_DIR}/lib
echo -e "Starting the $SERVER_NAME ...\c"
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS ${MAIN_CLASS} 1>/dev/null  2>&1 &

COUNT=0
NUM=0
while [[ $COUNT -lt 1 && $NUM < 10 ]]; do    
    echo -e ".\c"
    sleep 1 
    COUNT=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  | awk '{print $2}' | wc -l`
	let "NUM++"
done

if [ $COUNT -lt 1 ]; then
	echo "start $MAIN_CLASS failed!!!"
	exit 1
fi 

echo "OK!"
PID=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  | awk '{print $2}'`
echo "PID: $PID"


