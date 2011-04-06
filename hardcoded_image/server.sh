#!/usr/bin/env sh
# usage: script_name port binary_to_execute
OUT=/tmp/foo
mkfifo $OUT
(cat $OUT) | nc -l -p $1 | ($2 > $OUT)
rm $OUT
