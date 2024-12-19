#!/bin/bash

cd ./archive

# BungeeCord
cd ./bungeecord
mvn clean install

cp ./target/bungeecord-1.0-SNAPSHOT.jar ./../../.orbitmines/plugins/archive-bungeecord.jar
cd ..

# Spigot
cd ./spigot
mvn clean install

cp ./target/spigot-1.0-SNAPSHOT.jar ./../../.orbitmines/plugins/archive-spigot.jar
cd ..

