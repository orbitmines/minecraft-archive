# OrbitMines Minecraft Archive
An archive and remaster of OrbitMines' Minecraft Server (2013-2019)

---

## Local Setup

- Prerequisites
    ```
    sudo apt install git maven openjdk-21-jdk
    ```
  - Install [Git](https://git-scm.com/downloads)
  - Install [Maven](https://maven.apache.org/install.html)
  - Install [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

- *Open Git Bash or a terminal*

- Clone this repository 
    ```
    git clone --recurse-submodules git@github.com:orbitmines/minecraft-archive.git
    ```

    ```
    cd minecraft-archive
    ```

- Start the servers
  
  *On first install, this will (1) Download Spigot, (2) Compile the code in `@orbitmines/minecraft/remastered` and (3) download all worlds (40.9 GB compressed, 74.3 GB on disk) from `archive.orbitmines.com`.*
    ```
    ./orbitmines minecraft run
    ```