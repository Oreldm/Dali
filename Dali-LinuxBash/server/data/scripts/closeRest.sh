#!/bin/sh

sudo kill -9 $(lsof -i:5000 -t) 2> /dev/null || return 0

