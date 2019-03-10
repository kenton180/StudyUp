#!/bin/bash
PARAM=$1

sed -i'' "s/redis:/$PARAM:/" /etc/nginx/nginx.conf

/usr/sbin/nginx -s reload 
