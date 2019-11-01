#!/usr/bin/env bash

cd `dirname $0`
if [ "$1" = "start" ]; then
	sh ./start.sh
else
	if [ "$1" = "stop" ]; then
		sh ./stop.sh
	else
		if [ "$1" = "restart" ]; then
			sh ./stop.sh
			sh ./splitAndCat.sh cat ../lib true
			sh ./start.sh
		else
			if [ "$1" = "dump" ]; then
				sh ./dump.sh
			else
				echo "ERROR: Please input argument: start or stop or restart or dump"
			    exit 1
			fi
		fi
	fi
fi