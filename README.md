# minecraft-archive
Archive of OrbitMines' Minecraft Server (2013-2019)

- [ ] Check for java vulnerabilities
- [ ] Separate logs into private repo
- [ ] remove passwords
- [ ] remove ips, 2fa keys

---

## Local Setup

- Install [Git](https://git-scm.com/downloads)
- Install Java JDK 8 [(Winsows/Mac)](https://www.oracle.com/nl/java/technologies/javase/javase8-archive-downloads.html)
```
sudo apt install git openjdk-8-jdk
```

- Use Git Bash on Windows/Mac (TODO: Test this)
```
git bash
```

- Clone this repository
```
git clone --recurse-submodules git@github.com:orbitmines/minecraft-archive.git
```

- Run setup scripts
```
cd minecraft-archive
./setup.sh
```