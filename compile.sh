#!/bin/bash
cd ./archive/entrypoint
mvn clean install

cp ./target/minecraft-1.0-SNAPSHOT.jar ./../../orbitmines-minecraft-archive.jar
