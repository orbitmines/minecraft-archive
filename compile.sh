#!/bin/bash

cd ./archive

# Entrypoint
cd ./entrypoint
mvn clean install

cp ./target/entrypoint-1.0-SNAPSHOT.jar ./../../orbitmines-minecraft-archive.jar
cd ..

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

