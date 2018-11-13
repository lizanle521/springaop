#!/bin/bash
# 输入超时
if read -t 5 -p "plz enter your name:" name
then
    echo "$name,welcome to my program"
else
    echo
    # 输入计数  -n1
    read -n1 -p "Do you want to continue?[Y/N]" answer
    case $answer in
    Y | y) echo "ok,continue on!";; # case 语句里要用分号断句
    N | n) echo "ok,good bye!";;
    *) echo "wrong answer!"
    esac
    echo "this is end"
fi