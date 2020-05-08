#!/usr/bin/env bash

## Build and install the modules locally
mvn clean install

## build the containers to update changes
docker-compose build

## run the containers
docker-compose up -d

## Attach to existing client container
# docker attach player-async_player1_1 --detach-keys=ctrl-c
# Press Enter
