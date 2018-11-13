#!/bin/bash
# 测试 $* 和 $@
# $* 将所有参数都当做一个参数
count=1
for param in "$*"
do
    echo "\$* Parameter #$count=$param"
    count=$[ $count + 1]
done

# $@将所有的参数都挨个输出
count=1
for param in "$@"
do
    echo "\$* Parameter #$count=$param"
    count=$[ $count + 1]
done