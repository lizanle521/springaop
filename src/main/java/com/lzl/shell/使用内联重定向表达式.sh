#!/bin/bash
var1=10.11
var2=20.22
var3=30.33
var4=71
var5=`bc <<EOF
scale=4
a1 = $var1 * $var2
b2 = $var3 * var4
a1 + b2
EOF
`
echo the final answer is $var5