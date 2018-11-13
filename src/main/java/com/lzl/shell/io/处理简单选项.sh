#!/bin/bash
# 处理选项，while语法
while [ -n "$1" ]
do
    case "$1" in
    -a) echo "found the -a option";;
    -b) echo "found the -b option";;
    -c) echo "found the -c option";;
    *) echo "$1 is a invalid option";;
    esac # case esac是配对的语法
    shift # 参数起始位置右移 原来第一个为 $1,shift后，起始位置右移一位后，$1 为 第二个参数了
done
