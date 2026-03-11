# Dev Workspace

Test 123


Developer workspace of all projectsDeveloper workspace of all projects



# Linux Docker Setup
*  Install [Git Bash](https://git-scm.com/downloads)
*  Setup [SSH key](https://gitlab.com/help/ssh/README#generating-a-new-ssh-key-pair) for GitLab in [Your Account](https://gitlab.com/profile/keys)
*  Install Docker for Windows
    * Allow docker to access your drive, Docker -> Settings -> Shared Drives  
*  Download [Maven](https://maven.apache.org/download.cgi), the `bin-zip-archive`, then unzip it in a directory somewhere you won't delete.
    * Set `MAVEN_HOME` windows environment variable to the directory the `bin` directory is in within that unzipped folder.
    * SET `JAVA_HOME` windows environment variable to the directory your Java JDK is at.
*  Setup IntelliJ to `Git Bash` (Settings -> Terminal and set `"C:\Program Files\Git\bin\sh.exe" --login -i`)

# First Setup
First checkout to the `master` branch for all projects.

##### IntelliJ
Add Maven Projects to all `pom.xml`
```
pom.xml -> Right Click -> Add as Maven Project
```
Install the Lombok Project Plugin
```
File -> Settings -> Plugins -> Install Project Lombok Plugin
```


##### General Setup
Update all projects
```
./om update all
```
Set your developer discord user id in the `.env` file (Go to Discord Settings -> Appearance -> Enable Developer Mode, then right click on your name on Discord, and select `Copy ID`)
```
DEV_DISCORD_USER=YOUR_DISCORD_USER_ID
```
Start & Build all services
```
./om start all
```
And you're good to go! Now just use the following command to start&update servers
```
./om update <server> &&  ./om start <server>
```

# Production Setup
Setup orbitmines user
```
adduser orbitmines
usermod -aG sudo orbitmines
su - orbitmines
```

Install Packages
```
sudo apt-get update
```
Install Java
```
sudo apt install openjdk-8-jdk
```
Install Maven
```
sudo apt install maven
```
Install Docker
```
sudo apt-get install \
        apt-transport-https \
        ca-certificates \
        curl \
        gnupg-agent \
        software-properties-common &&

    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - &&
    sudo apt-key fingerprint 0EBFCD88 &&
    sudo add-apt-repository \
       "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
       $(lsb_release -cs) \
       stable" &&

    sudo apt-get update &&
    sudo apt-get install docker-ce docker-ce-cli containerd.io
```
Install docker-compose
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```
Add docker user to `orbitmines` user
```
sudo usermod -aG docker orbitmines
su - orbitmines
```

Setup Git
```
ssh-keygen -o -t rsa -b 4096 -C "business@orbitmines.com"
```
Add `key` to [Your Account (Main OrbitMines GitLab account)](https://gitlab.com/profile/keys)

TODO: New deployment system, temporary:
```
git clone --recursive git@gitlab.com:orbitmines/dev-workspace.git
```
```
chmod +x ./prod
```
Then same process as `First Setup`, but with the command `prod` instead of `om`

# Production ServerRestarter setup
#### Setup SSH Key
Enter server restarter
```
docker exec -it orbitmines_server-restarter_1 /bin/sh
```
Setup ssh key
```
ssh-keygen -o -t rsa -b 4096 -C "business@orbitmines.com"
```
#### Setup User
```
adduser --home /home/orbitmines/current/server-restarter --no-create-home server-restarter
sudo usermod -aG server-restarter orbitmines
sudo usermod -aG server-restarter docker
su - server-restarter
ssh-agent bash
ssh-add /home/orbitmines/current/server-restarter/.ssh/id_rsa
```
Then add the `id_rsa.pub` to the `authorized_keys` files in the `/home/orbitmines/current/server-restarter/.ssh` folder
