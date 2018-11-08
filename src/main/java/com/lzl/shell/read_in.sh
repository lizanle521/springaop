#!/bin/bash
test ".$1" = . && echo "param 1 not found"
#  -n "$1" 当串$1的长度大于0时为真
# if [ -z str1 ]　　　　　　　 当串的长度为0时为真(空串)
# if [ str1 ]　　　　　　　　 当串str1为非空时为真
# if [ str1 = str2 ]　　　　　  当两个串有相同内容、长度时为真
# if [ str1 != str2 ]　　　　　 当串str1和str2不等时为真
if [ -n "$1" ]; then
    read -p "Input $1:" $1
    clear
    echo "Input `$1` end"
fi