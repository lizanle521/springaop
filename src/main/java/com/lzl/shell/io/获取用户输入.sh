#!/bin/bash
# -n参数表示不换行
echo -n "enter your name":
read name
echo hello $name,welcome to my program

read -p "please enter your age:" age
days=$[$age * 365]
echo that makes you over $days days old

#指定多个变量，输入的每个数据值都会分配给表中的下一个变量，如果用完了，就全分配各最后一个变量
read -p "please enter name:" first last
echo "checking data for $first ,$last"

read -p "enter a number"
#如果不指定变量，read命令就会把它收到的任何数据都放到特殊环境变量REPLY中
factorial=1
for((count=1;count<=$REPLY;count++))
do
    factorial=$[$factorial * $count]
done
echo result is $factorial