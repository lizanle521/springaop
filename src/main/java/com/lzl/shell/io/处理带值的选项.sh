#!/bin/bash
while [ -n "$1" ]
do
    case "$1" in
    -a) echo "found the -a option";;
    -b) param="$2"
        echo "found the -b option,with parameter $param"
        shift;;
    -c) echo "found the -c option";;
    --) shift  # 如果参数有 --，那么就跳出循环，生下来的参数 会给 下边的 $@ 变量
        break;;
    *)  echo "$1 is not option";;
    esac
    shift;
done

count=1
for param in "$@"
do
    echo "Parameter #$count : $param"
    count=$[ $count + 1 ]
done
