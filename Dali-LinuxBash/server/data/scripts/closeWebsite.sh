#!/bin/sh

sudo kill -9 $(lsof -i:3000 -t) 2> /dev/null || return 0
sudo kill -9 $(lsof -i:3001 -t) 2> /dev/null || return 0
