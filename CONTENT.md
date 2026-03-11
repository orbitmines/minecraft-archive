# OrbitMines Archive

Complete archive of the OrbitMines Minecraft server network (2013–2019). Consolidated from:

---

## Structure

```
worlds/             — 461 world saves (YYYY-MM-DD_WorldName or YYYY_WorldName)
servers/            — full server backups, test servers, plugin/shop snapshots
code & workspace/   — 33 dated Java/Eclipse workspaces + standalone projects
visuals/            — design/branding assets, screenshots, marketplace
databases/          — sanitized MySQL dumps (IPs removed)
documents/          — admin docs, ideas, polls
players/skins/      — 671 player UUID skin directories
private/            — separate repo: logs, player data, server jars, credentials, financials
```

---

## servers/

### Production backups (full server trees)
| Directory | Date | Contents |
|---|---|---|
| `2014-04-30/` | 2014-04-30 | Original multi-game server (openmod): all game modes, plugins, configs |
| `2014-07-11_SummerEventServer/` | 2014-07-11 | Summer event server (Creative, KitPvP, Prison, Survival events) |
| `2015-02-06/` | 2015-02-06 | Full production: alpha, hub, survival, kitpvp, prison, creative, skyblock, minigames, bungeecord |
| `2015-03-08_survival-ftp/` | 2015-03-08 | Survival FTP backup |
| `2015-08-01/` | 2015-08-01 | Full production: hub, survival, kitpvp, prison, creative, skyblock, minigames, bungeecord |
| `2018-12-09/` | 2018-12-09 | Docker-era dump: HUB, KitPvP, Survival, Bungeecord |
| `2019-06-17/` | 2019-06-17 | **Final production at shutdown**: hub, kitpvp, survival, build, bungeecord |

### Year-approximate servers
| Directory | Era | Contents |
|---|---|---|
| `2014/` | ~2014 | First dedicated servers: creative, creative-2, hub, kitpvp, openmod, prison, skyblock, skyblock-2, survival |
| `2014-test/` | ~2014 | Test instances: 1.7.9-test, orbitmines-luckyblock, orbitmines-test-server, summer-event, uhc, prullenbak |
| `2014-pixelmon/` | ~2014 | 3 Pixelmon servers (Cauldron/Forge+Bukkit 1.7.2) |
| `2015-test/` | ~2015 | test-servers-august: hub, survival, kitpvp, prison, creative, skyblock, minigames, proxy |
| `2015-spigot-test/` | ~2015 | Spigot dev servers 1–4 + spigot-bungee |
| `2015-pixelmon/` | ~2015 | OrbitMines Pixelmon server |
| `2018-2019_builder/` | 2018–2019 | Builder server for map construction |
| `2018-2019-test/` | 2018–2019 | Docker-era dev instances: hub, survival, kitpvp, prison, build, bungeecord |

### Other
| Directory | Contents |
|---|---|
| `2014-02-25_OrbitMines-PvP/` | PvP server plugins/configs (20K) |
| `2014-02-27_OrbitMines-PvP/` | PvP server with 33 arena maps (246M) |
| `2015_server-jars/` | OrbitMines' own compiled plugin jars (Hub, KitPvP, Survival, etc.) |
| `2019_deployment/` | Docker compose files, Dockerfiles, nginx config |
| `2019_server-jars/` | Server jar references (actual jars in `private/server-jars/`) |
| `plugin-snapshots/` | Dated plugin/config snapshots from 2014 |
| `shop-snapshots/` | OrbitMines Shop iterations (7 snapshots, 2014-03 through 2014-07) |

Note: Logs, crash reports, and player-private data replaced with symlinks to `private/`.

---

## worlds/ — 461 World Saves

All Minecraft world directories extracted from every server backup, named by date and world name.

### 2014 (PvP maps — Feb 2014)
33 arena maps: `2014-02-25_pvp_1-1` through `2014-02-25_pvp_4-5` (29 grid maps) + `2014-02-27_pvp_PvPSelectEarthShip`, `_PvPSelectMarsShip`, `_PvPSelectVenusShip`, `_PvPWorld`

### 2014 (very early — March 2014)
`2014-03-19_luckyblock_*`, `2014-03-19_orbitmines-test_*`

### 2014 (openmod server — April 30, 2014)
All worlds from the original multi-game server: Dropper, Event, Factions, HideAndSeek arenas, Hub, KitPvP, MiniGames, OITC, PaintBall, PrisonPlots, Quake, SkyWars, Spleef, Tutorial, VIPCity, and more.

### 2014 (dedicated servers — dated by level.dat)
- `2014-04-17_*` — LuckyBlock test server
- `2014-04-30_*` through `2014-07-08_*` — Survival, KitPvP, Creative, SkyBlock, Pixelmon worlds
- `2014-07-11_*` — Pixelmon + summer event worlds
- `2014-07-27_*` — 1.7.9 test server, TorchCraft
- `2014-10-04_*` through `2014-11-24_*` — Hub, Pixelmon, UHC worlds
- `2014_client_*` — Client-side saves: NewWorld, PixelmonWorld, world
- `2014_KitPvPMap` — KitPvP arena (undated)

### 2015 (Feb–Mar: test servers and FTP)
- `2015-02-03_*` — MiniGames maps (11)
- `2015-02-06_*` — Full Feb 6 production (67 worlds)
- `2015-02-07_*` through `2015-03-08_*` — KitPvP, Creative, Prison, Survival, Hub, SkyBlock

### 2015 (Apr–Aug: spigot test and production)
- `2015-04-28_*` through `2015-07-29_*` — Spigot test servers, FTP downloads, minigame maps
- `2015-08-01_*` — Full Aug 1 production (40+ worlds)
- `2015-08-02_*` — Spigot server 2 worlds
- `2015_client_*` — Client-side saves: NewWorld, OM-PvPLobby, OM-PVPNether
- `2015_design_*` — Hub, Hub-1Year, OM-PvPLobby (from design maps)

### 2018–2019 (builder + Docker era)
- `2018-09-07_*` through `2018-10-22_*` — Builder construction snapshots, Halloween worlds
- `2019-04-22_*` through `2019-06-17_*` — Builder, test, and **final production worlds at shutdown** (incl. SurvivalWorld 18.3GB)
- `2019-09-27_*` — Late 2019 snapshots

---

## code & workspace/

| Directory | Contents |
|---|---|
| `2014-10-30` through `2016-04-30` | 27 dated Eclipse workspace snapshots |
| `2015-08-03/` | 65+ Eclipse Java projects (largest workspace snapshot) |
| `2015_old-plugins/` | Compiled .jar plugins only (ChatColors, HubFly, OnKill, etc.) |
| `2016-17?_workspace/` | 2016–2017 plugin workspace |
| `2018-2019_dev-workspace/` | GitLab `orbitmines/dev-workspace` (12 submodules, 279 Java files) |
| `2019-09-27_orbitmines/` | Code repo snapshot with submodules |
| `2019_world-generator/` | Maven world generator project |
| `2014-12-15_ServerSelectorInv.java` | Single code file |

---

## visuals/

| Directory | Contents |
|---|---|
| `banners/` | Dated banner graphics (2014-02-08 through 2018, incl. .avi, .aep, .psd) |
| `logo/` | Logo variants |
| `server-icons/` | Game mode icons (creative.png, kitpvp.png, etc.) |
| `server-names/` | Game mode name text graphics |
| `shop/` | VIP tier graphics (Iron/Gold/Emerald/Diamond) |
| `vip/` | VIP rank icons |
| `marketplace/` | SpigotSpleef releases, private plugins, photos |
| `player-skins/` | Player skin renders (2013–2014) |
| `screenshots/` | `2014/`, `2015/`, `2018-2019/` client screenshots |
| `2015-design/` | Adobe assets (OMIntro.avi 890M, ProfilePicture.psd), shop graphics, KitPvP config |
| `2014-03-05_OrbitMines-App/` | iOS app design assets |
| Loose files | `OrbitMinesLogo_twitter.psd`, `OrbitMines Staff.png`, `ProfilePicture.*` |

---

## databases/

| File | Contents |
|---|---|
| `2015-05-08_sql-3.verygames.net.sql` | VeryGames MySQL dump |
| `2015-08-03_sql-3.verygames.net.sql` | VeryGames MySQL dump |
| `2018-12-09_test_database_dump.sql` | Test database dump |
| `2019-04-28_dump.sql` | Main MySQL dump |
| `2019-04-29_dump2.sql` | MySQL dump |
| `2019-04-xx_dump3.sql` | Additional MySQL dump |
| `fixtures_development.sql` | Development fixtures |
| `fixtures_production.sql` | Production fixtures |

---

## documents/

| Path | Contents |
|---|---|
| `invoices/` | Hetzner/Strato invoices (symlink to `private/financial/`) |
| `2014/` | Ideas, polls from 2014 |
| `2015/` | Ideas, polls, claimdata, claimconfig, player inventory backups |
| `2018-10-12_Balans.docx` | Financial doc (symlink to `private/financial/`) |
| Dated files | Checklist, PrisonSellPrice, PrisonRankBackup, mycmdSurvival, OMT.yml, 25%Off, OrbitMines.docx, OrbitMines Datapoints.xlsx |

---

## private/ (separate repository)

Contains content that should not be publicly distributed.

| Directory | Contents |
|---|---|
| `databases/` | Original SQL dumps with player IP addresses |
| `logs/` | All server logs, crash reports, proxy logs (symlinked from `servers/`) |
| `player-data/` | Player-private plugin data: Essentials userdata (IPs), banned-ips/players, whitelist, ops, usercache, GroupManager users, LoginSecurity, ChestShop/PvpLevels/Jobs/PlayerPoints/PlotMe/OnTime/Pixelmon databases, mstore factions, Backpack data, Votifier RSA keys, EnjinMinecraftPlugin API keys, StaffSecure configs, Plan serverConfiguration, error_logs |
| `server-jars/` | Minecraft/Spigot/CraftBukkit/BungeeCord JARs (not redistributable per Mojang EULA) |
| `financial/` | Hetzner/Strato invoices, Balans.docx |
| `space-spigot/` | Custom Spigot server fork (private code) |
| `third-party-code/` | Code for other servers: ArcticMC*, FootballCraft*, ComuGamersHubShop, Ophion*, EnderSkies |
| Password files | `2014-04-16_Password Server.txt`, `2019-05-26_Pw OM.docx` |

---

## Sources

See [SOURCES.md](SOURCES.md) for complete provenance mapping.
