#!/bin/bash
# 如果输入的是全路径，那么$0就是全路径
echo this file name is $0
name=`basename $0`
echo this file name is $name