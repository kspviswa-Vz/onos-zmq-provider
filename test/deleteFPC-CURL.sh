#!/bin/bash

curl -u onos:rocks -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d @deleteFPC.json http://localhost:8181/onos/zeromqprovider/zmq/flows