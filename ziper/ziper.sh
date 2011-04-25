#!/bin/bash
## $1 stores filename of compressed file
## $2 stores number of iterations

if [ ! $# -eq 2 ] ; then
    echo "usage: $0 file_name number_of_iteration"
    exit 1
elif [ ! -f $1 ]; then
    echo "$1 - file does not exists"
    exit 1
else
    ls -l $1 | awk '{ print $5 }' 
    zip -q test_$1_0.zip $1 && ls -l test_$1_0.zip | awk '{ print $5 }' 
    for (( i=1; i<=$2; i++ )); do
        zip -q test_$1_$i.zip test_$1_$((i-1)).zip && ls -l test_$1_$i.zip | awk '{ print $5 }'
    done
    rm test*zip
fi

