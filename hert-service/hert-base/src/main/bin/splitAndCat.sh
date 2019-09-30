#!/usr/bin/env bash
cd `dirname $0`

function splitFile(){

dir=$1
maxSize=$2
delete=$3
cd $dir

echo "catFiles,pwd=$(pwd),dir=${dir}"

for fileVo in $dir/*
do
	size=`du -hb $fileVo | awk '{print $1}'`
	name=$fileVo

	if [ $size -gt $maxSize ] ; then
		echo $name
		split -b $2 $name ${name}-
		if [ "$delete" = "true" ] ; then
			rm $name
		fi
	fi

done
}

function catFiles(){

dir=$1
delete=$2
cd $dir

echo "catFiles,pwd=$(pwd),dir=${dir}"

filenames=`echo $(ls | grep '\-aa' | awk -F"jar-" '{print $1}')`
for f in $filenames
do
    filename="${f}jar"
	cat ${filename}-* > ${filename}
	if [ "$delete" = "true" ] ; then
		rm ${filename}-*
	fi
done

}

if [ "$1" = "split" ] ; then
	splitFile $2 $3 $4
else
	if [ "$1" = "cat" ] ; then
		catFiles $2 $3
	else
		echo "Usage:$0 split dir maxSize delete"
		echo "Usage:$0 cat dir delete"
	fi
fi