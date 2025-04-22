#!/bin/bash

# Download BungeeCord & Compile Spigot - https://www.spigotmc.org/wiki/buildtools/
cd ./.orbitmines/spigot

curl -o BungeeCord.jar https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar
curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

#declare -a VERSIONS=("1.8.3" "1.8.8" "1.8" "1.9.4" "1.9" "1.10.2" "1.11" "1.12.2" "1.12" "1.13.1" "1.13.2" "1.13" "1.14" "1.14.1" "1.14.2" "1.14.3" "1.14.4" "1.15" "1.15.1" "1.15.2" "1.16.1" "1.16.2" "1.16.4" "1.16.5" "1.17" "1.17.1" "1.18" "1.18.1" "1.18.2" "1.19" "1.19.1" "1.19.2" "1.19.3" "1.19.4" "1.20.1" "1.20.2" "1.20.4" "1.20.6" "1.21.1" "1.21.3" "1.21.4" "1.21.5")
declare -a VERSIONS=("1.21.5")

for VERSION in "${VERSIONS[@]}"
do
  if [ ! -f "./spigot-${VERSION}.jar" ]; then
    java -jar BuildTools.jar --rev $VERSION
  fi
done