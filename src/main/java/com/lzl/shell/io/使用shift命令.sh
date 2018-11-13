#!/bin/bash
count=1
while [ -n "$1" ]
do
    echo "Parameter #$count = $1"
    count=$[ $count+1 ]
    shift
done

echo -e "\n"

echo "the original parameter is $*"
shift 2
echo "Here is the new first param:$1"