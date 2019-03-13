#!/bin/bash

PARAM=$1
PORT=6379
FILE=/etc/nginx/nginx.conf

# Bool to see if same server
same_server=false

# Get the line we're interested in
line=$(awk '/6379;/{print}' ${FILE})

# From that line, extract the server name 
old_server=$(echo $line | cut -d ':' -f1)

# Compare old server with new server, if same give warning msg and set
# bool to true so it can be checked later
if [ "$old_server" == "server $PARAM" ]; then
    echo "Warning, same server."
    same_server=true
fi

new_server="server $PARAM"
sed -i "s/$old_server/$new_server/g" ${FILE}

# Check bool before reloading nginx
if [ "$same_server" == false ]; then
    /usr/sbin/nginx -s reload
fi
