#!/bin/bash

# Download BungeeCord & Compile Spigot - https://www.spigotmc.org/wiki/buildtools/
cd ./.orbitmines/spigot

curl -o BungeeCord.jar https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar
curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

declare -a VERSIONS=("1.8.3" "1.8.7" "1.8.8" "1.8" "1.9.4" "1.9" "1.10" "1.11" "1.21.1" "1.12.2" "1.12" "1.13.1" "1.13.2" "1.13")

for VERSION in "${VERSIONS[@]}"
do
  java -jar BuildTools.jar --rev $VERSION
done
