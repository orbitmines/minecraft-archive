package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.WrittenBookBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Deprecated
public class PatchNotes {

    private final static int DAYS_PATCH_IS_NEW = 2;

    private final OMServer server;

    private Instance hubInstance;

    private Map<Server, List<Instance>> instances;

    public PatchNotes(OMServer server) {
        this.server = server;
        this.instances = new HashMap<>();

        add(new Instance(Server.KITPVP, "v1.1.0", "1.14 Update", "Updated survival to 1.14!", "2019-05-18",
            new Feature(
                "1.14 UPDATE", Color.RED,
                "We have updated everything to 1.14!",
                "https://i.imgur.com/yiXVYgW.png"
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.3.1", "1.14.1 Update", "Updated survival to 1.14.1!", "2019-05-14",
            new Feature(
                "1.14.1 UPDATE", Color.YELLOW,
                "We have updated everything to 1.14.1!",
                "https://i.imgur.com/yiXVYgW.png"
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.3.0", "1.14 Update", "Updated survival to 1.14!", "2019-04-28",
            new Feature(
                "1.14 UPDATE", Color.YELLOW,
                "We have updated everything to 1.14!",
                "https://i.imgur.com/yiXVYgW.png"
            )
        ));

        add(new Instance(Server.HUB, "v1.3.0", "1.14 Update", "Added new command system and upgraded to 1.14!", "2019-04-28",
            new Feature(
                "1.14 UPDATE", Color.YELLOW,
                "We have updated everything to 1.14!",
                "https://i.imgur.com/yiXVYgW.png"
            ),
            new Feature(
                "COMMAND SYSTEM", Color.BLUE,
                "We have added an amazing command system that will help you give tab-completions while typing!"
            ),
            new Feature(
                "BUGS & PERFORMANCE", Color.RED,
                "We have greatly increased performance and fixed tons of bugs!"
            ),
            new Feature(
                "INSTANT DONATIONS", Color.TEAL,
                "Donations made on our shop are now instantly available on the server with no delay!"
            )
        ));

        add(new Instance(Server.HUB, "v1.2.0", "Chat Mentions, Chat Interactions and more!", "Added features to interact more with the chat, fixed several bugs and some small new features.", "2018-10-04",
            new Feature(
                "CHAT MENTIONS", Color.BLUE,
                "The chat will turn orange when your name is mentioned. Commands will be highlighted blue.",
                "https://i.imgur.com/VGglWyY.png"
            ),
            new Feature(
                "PATCH NOTES", Color.RED,
                "An option has been added to view previous patch notes when opening the patch notes in game."
            ),
            new Feature(
                "COMMUNITY GOAL", Color.BLUE,
                "The voting community goal has been set to 2,000 votes."
            ),
            new Feature(
                "PERSONAL ACHIEVEMENTS", Color.FUCHSIA,
                "Added three tiers of personal vote achievements. (50, 100 and 150 votes)"
            ),
            new Feature(
                "DELETE SQUAD", Color.MAROON,
                "An option has been added to /squad to delete your current Discord squad."
            ),
            new Feature(
                "AUTOSELECT SQUAD", Color.BLUE,
                "When creating a squad you will automatically select it if you have no other squad selected."
            ),
            new Feature(
                "MUTE RANK", Color.GRAY,
                "Being muted now results in having a muted rank on Discord which prevents you from talking and chatting."
            ),
            new Feature(
                "NAME HOVER", Color.AQUA,
                "Hovering over a players name will display their real name, nick name and Discord tag."
            ),
            new Feature(
                "CLICK TO PM", Color.LIME,
                "Clicking on a player's name will allow you to send private messages to them."
            ),
            new Feature(
                "TABLIST ORDER", Color.LIME,
                "Ranks are now listed from high to low in the tablist."
            ),
            new Feature(
                "DRAG ITEMS", Color.RED,
                "Fixed: You were able to drag items in custom inventories which would result in item loss."
            ),
            new Feature(
                "SKINS IN STATS", Color.RED,
                "Fixed: The skins in !stats weren't always loading properly.",
                "https://i.imgur.com/qhXR9Cs.jpg"
            ),
            new Feature(
                "ACTION BAR FLICKER", Color.RED,
                "Fixed: The Action Bar would sometimes flicker between multiple messages."
            ),
            new Feature(
                "LOOT FIX", Color.RED,
                "Fixed: Items from loot would sometimes appear in your inventory when shift-clicking."
            ),
            new Feature(
                "!ip", Color.BLUE,
                "Shows the server IP on Discord."
            ),
            new Feature(
                "/squad", Color.BLUE,
                "Alias for /discordsquad."
            ),
            new Feature(
                "/patchnotes (server) (version)", Color.BLUE,
                "Open the patch notes with this command."
            )
        ));

        add(new Instance(Server.HUB, "v1.1.0", "Private Discord Server, !list & more", "Private Discord servers, changes to solars and !list on Discord!", "2018-08-11",
            new Feature(
                "PRIVATE DISCORD", Color.BLUE,
                "EMERALD+ are now able to create their own Discord server and communicate with players in-game.",
                "https://i.imgur.com/4ViYTKT.jpg"
            ),
            new Feature(
                "SOLAR PRICES", Color.YELLOW,
                "Solar prices in OrbitMines.Buycraft.net have been updated to: 1500, 4000, 12,500 Solars.",
                "https://i.imgur.com/QzxcXdm.jpg"
            ),
            new Feature(
                "IRON SOLARS", Color.SILVER,
                "We have changed the IRON starting Solars from ~250~ to 500."
            ),
            new Feature(
                "DISCORD COMMANDS", Color.BLUE,
                "Executed Discord commands in the in-game chats will no longer be shown to online players."
            ),
            new Feature(
                "GLOBAL MENTIONS", Color.RED,
                "You are no longer able to use @everyone and @here, due to potential abuse."
            ),
            new Feature(
                "COLOR ROLES", Color.YELLOW,
                "Several color roles have been added to Discord."
            ),
            new Feature(
                "NAME CHANGES", Color.PURPLE,
                "Name changes will now be logged in #name_change."
            ),
            new Feature(
                "SCOREBOARD", Color.WHITE,
                "You are now able to disable your Scoreboard in /settings.",
                "https://i.imgur.com/MsxJZcg.jpg"
            ),
            new Feature(
                "SERVER SELECTOR", Color.TEAL,
                "When joining OrbitMines, the Server Selector will be in your selected hotkey."
            ),
            new Feature(
                "!list", Color.BLUE,
                "Added a !list command to Discord, it can be used in in-game chats & private Discord servers."
            ),
            new Feature(
                "/discord", Color.BLUE,
                "Use /discord in-game to get the Discord invite link."
            ),
            new Feature(
                "/discordsquad", Color.BLUE,
                "Open the private Discord server GUI."
            )
        ));

        add(new Instance(Server.HUB, "v1.0.0", "OM Release", "The first OrbitMines release, containing commands, stats, loot and many other features!", "2018-07-23",
            new Feature(
                "HUB", Color.TEAL,
                "We have a new Hub fitting our Space Themed server fitting our server and lore built by ReddReaperz.",
                "https://i.imgur.com/TGpD01Y.jpg"
            ),
            new Feature(
                "PATCH NOTES", Color.RED,
                "With the patch notes users will be able to view additions and changes to OrbitMines more easily through the 'Patch Notes' npc in game or through Discord.",
                "https://i.imgur.com/EsOVG5S.png"
            ),
            new Feature(
                "SERVER SELECTOR", Color.TEAL,
                "The Server Selector is the fastest way to switch servers on OrbitMines and we gave it a cool new look.",
                "https://i.imgur.com/73Z0iVe.jpg"
            ),
            new Feature(
                "SPACE TURTLE", Color.LIME,
                "The SpaceTurtle is the way to receive rewards and vote on our server. It also makes the work of our developers a lot easier so that's also fun :D.",
                "https://i.imgur.com/NuhCkbY.jpg"
            ),
            new Feature(
                "FRIENDS", Color.AQUA,
                "You can now add other players as Friends or Favorite friends and instantly see when they join the server, or to easily give them access to your stuff.",
                "https://i.imgur.com/vXmPhk6.jpg"
            ),
            new Feature(
                "STATS", Color.LIME,
                "Through Stats you can view all your statics on OrbitMines, how long you have played, when you joined, the amount of times you voted and a lot more.",
                "https://i.imgur.com/eXrrtAh.jpg"
            ),
            new Feature(
                "ACHIEVEMENTS", Color.FUCHSIA,
                "After a long time of 'Coming Soon' we have added Achievements to OrbitMines which you can access through your Stats.",
                "https://i.imgur.com/v9SiNeT.jpg"
            ),
            new Feature(
                "SETTINGS", Color.RED,
                "You can now specify these settings; Private Messages, Player Visibility, Stats & Language with use of the Friends system.",
                "https://i.imgur.com/pfh2P1w.jpg"
            ),
            new Feature(
                "VOTING SYSTEM", Color.BLUE,
                "We now have an automatic voting system that reward the top 3 players, but also the entire server if the community goal is reached.",
                "https://i.imgur.com/mb85nN0.jpg"
            ),
            new Feature(
                "DISCORD", Color.BLUE,
                "We have created a link between Discord & OrbitMines which allows you to easily chat with players on our server and access statistics from the server.",
                "https://i.imgur.com/1Zb1zbv.jpg"
            ),
            new Feature(
                "/msg <player> <message>", Color.BLUE,
                "Send Private Messages."
            ),
            new Feature(
                "/discordlink <Name>#<Id>", Color.BLUE,
                "Link your OrbitMines account with your Discord User."
            ),
            new Feature(
                "/reply <message>", Color.BLUE,
                "Reply to a Private Message."
            ),
            new Feature(
                "/hub", Color.BLUE,
                "Connect to the Hub."
            ),
            new Feature(
                "/server <server>", Color.BLUE,
                "Connect to a specific Server."
            ),
            new Feature(
                "/list", Color.BLUE,
                "Show all players online."
            ),
            new Feature(
                "/website", Color.BLUE,
                "Display our website url."
            ),
            new Feature(
                "/shop", Color.BLUE,
                "Display our shop url."
            ),
            new Feature(
                "/report <player> <reason>", Color.BLUE,
                "Report a player for breaking the rules."
            ),
            new Feature(
                "/rules", Color.BLUE,
                "Open the Rule Book."
            ),
            new Feature(
                "/servers", Color.BLUE,
                "Open the Server Selector."
            ),
            new Feature(
                "/topvoters", Color.BLUE,
                "Display the Top Voters."
            ),
            new Feature(
                "/vote", Color.BLUE,
                "Display the Vote Links."
            ),
            new Feature(
                "/loot", Color.BLUE,
                "Interact with the SpaceTurtle."
            ),
            new Feature(
                "/friends", Color.BLUE,
                "Open the Friends GUI."
            ),
            new Feature(
                "/stats <player>", Color.BLUE,
                "Open Stats for a specific player."
            ),
            new Feature(
                "/settings", Color.BLUE,
                "Open your Settings."
            ),
            new Feature(
                "/prisms", Color.BLUE,
                "Display your Prisms."
            ),
            new Feature(
                "/solars", Color.BLUE,
                "Display your Solars."
            ),
            new Feature(
                "/afk (reason)", Color.BLUE,
                "Go afk, this feature is for IRON+."
            ),
            new Feature(
                "/nick <name>|off", Color.BLUE,
                "Set your nickname, this feature is for GOLD+."
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.2.0", "Nether&End Claiming, Pet Tickets & more!", "You are now able to claim in The Nether&End. We have also added several new features to claiming and a system has been added to transfer pet ownership to other players!", "2018-10-04",
            new Feature(
                "NETHER & END CLAIMING", Color.RED,
                "Claiming has been enabled in The Nether & End. You're only allowed to create claims 200 blocks away from 0,0 in The End.",
                "https://i.imgur.com/xAUqqYc.jpg"
            ),
            new Feature(
                "RENAME CLAIMS", Color.LIME,
                "You are now able to change the name of a claim.",
                "https://i.imgur.com/DTmMYgS.jpg"
            ),
            new Feature(
                "CLAIMING TOOL", Color.ORANGE,
                "The name of your claim will appear in an action bar while standing in your claim and holding the Claiming Tool."
            ),
            new Feature(
                "FLY DISABLING", Color.WHITE,
                "Fly will now be disabled 3 seconds after leaving your claim instead of instantly.",
                "https://i.imgur.com/lOGkxds.jpg"
            ),
            new Feature(
                "ITEM MENTION", Color.WHITE,
                "Display the item you are currently holding in your hand in the chat with [item].",
                "https://i.imgur.com/rqa1tl1.jpg"
            ),
            new Feature(
                "SPAWN SHOP", Color.LIME,
                "Added Spruce Logs and Acacia Logs to the spawn shop. (16 Logs for 100 Credits)"
            ),
            new Feature(
                "FLINT AND STEEL", Color.ORANGE,
                "You are no longer able to use a flint ant steel near other players."
            ),
            new Feature(
                "BUCKETS", Color.RED,
                "Fixed: Buckets were usable at spawn."
            ),
            new Feature(
                "DISCORD DEATH MESSAGES", Color.SILVER,
                "Death messages will now be displayed in the Discord survival chat channel.",
                "https://i.imgur.com/rgt1ONo.jpg"
            ),
            new Feature(
                "VIEW CHEST SHOP", Color.ORANGE,
                "Your hand now has to be empty in order to view the contents of a chest shop."
            ),
            new Feature(
                "CLAIM MESSAGE", Color.RED,
                "Fixed: Just walking in a claim would give you the message: 'You're not allowed to do that here!'."
            ),
            new Feature(
                "FAVORITE WARPS", Color.RED,
                "Fixed: Favorite warps would reset after login back on the server."
            ),
            new Feature(
                "CRAFTING", Color.RED,
                "Fixed: The Claiming Tool and Spawner Miner were usable in a crafting table and anvil."
            ),
            new Feature(
                "CHEST SHOP", Color.RED,
                "Fixed: Chest Shops would sometimes use the wrong chest instead of the one the sign is attached to."
            ),
            new Feature(
                "/pet", Color.BLUE,
                "You are now able to transfer pet ownership with the use of Pet Tickets.",
                "https://i.imgur.com/KgkE2IQ.jpg"
            ),
            new Feature(
                "/claims", Color.BLUE,
                "Open an inventory with all your claims.",
                "https://i.imgur.com/mGu1idC.jpg"
            ),
            new Feature(
                "/echest", Color.BLUE,
                "Alias for /enderchest."
            ),
            new Feature(
                "/ec", Color.BLUE,
                "Alias for /enderchest."
            ),
            new Feature(
                "/tph", Color.BLUE,
                "Alias for /tphere."
            ),
            new Feature(
                "/wb", Color.BLUE,
                "Alias for /workbench."
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.1.0", "Spawn Shop, Silk Touch Spawners, /back & much more", "We have added a Spawn Shop and many cool features such as Silk Touch Spawners and /Back.", "2018-08-11",
            new Feature(
                "HARD DIFFICULTY", Color.RED,
                "We have changed the difficulty to hard, instead of easy."
            ),
            new Feature(
                "SPAWN SHOP", Color.LIME,
                "There is a shop at spawn with all sorts of essential items & daily rotating deals.",
                "https://i.imgur.com/SZHhTPl.jpg"
            ),
            new Feature(
                "SPAWNERS", Color.PURPLE,
                "You are now able to mine spawners with a special one-time use Silk Touch Pickaxe.",
                "https://i.imgur.com/zUeGo63.jpg"
            ),
            new Feature(
                "WARP NAME", Color.TEAL,
                "You can now use /warp <name> in order to teleport to a specific Warp."
            ),
            new Feature(
                "CLAIM VISUALIZATION", Color.TEAL,
                "Claim borders are now presented above water, instead of the bottom of an ocean.",
                "https://i.imgur.com/Vpvlu7D.jpg"
            ),
            new Feature(
                "SETHOME ACCESS", Color.ORANGE,
                "You will now need access permissions in order to set a home inside someone else's claim."
            ),
            new Feature(
                "PHANTOMS", Color.ORANGE,
                "Entering a bed will now remove the Phantoms for two hours.",
                "https://i.imgur.com/89KTaOt.jpg"
            ),
            new Feature(
                "CLAIM CATEGORY", Color.LIME,
                "Manage&Build categories now clearly show that they also contain permissions for the lower categories."
            ),
            new Feature(
                "WORKBENCH", Color.ORANGE,
                "The /workbench command has been added to GOLD+ instead of DIAMOND+."
            ),
            new Feature(
                "REMEMBER FLY", Color.WHITE,
                "You will no longer fall to your death after logging out in /fly."
            ),
            new Feature(
                "/nearby", Color.BLUE,
                "Show the most nearby Region, how far it is and in what direction."
            ),
            new Feature(
                "/back", Color.BLUE,
                "With the use of Back Charges, you will now be able to teleport to your previous location & location of death."
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.0.2", "New Spigot Version & Bug Fixes", "We've fixed a lot of bugs behind the scenes and several bugs noticeable for users.", "2018-08-01",
            new Feature(
                "SPIGOT", Color.ORANGE,
                "We have updated to a new version of Spigot 1.13, meaning Boats, Fishing & Minecarts are fixed now!",
                "https://i.imgur.com/HQrNURk.jpg"
            ),
            new Feature(
                "HEAD TEXTURES", Color.LIME,
                "Fixed: Custom Head Textures weren't generating correctly.",
                "https://i.imgur.com/Oon4z4Q.jpg"
            ),
            new Feature(
                "REGION SPEED", Color.AQUA,
                "Added Speed Potion Effect to Region Beacons."
            ),
            new Feature(
                "HOME PRICE", Color.BLUE,
                "Increased the price for Homes from ~900~ to 2,500 Prisms."
            ),
            new Feature(
                "TRUSTING", Color.LIME,
                "Fixed: Trusting players has finally been fixed!"
            ),
            new Feature(
                "PROJECTILE PVP", Color.RED,
                "Fixed: You were able to damage other players with Projectiles, that is no longer possible."
            ),
            new Feature(
                "SPAWN INTERACTING", Color.LIME,
                "Fixed: You were able to interact with certain blocks in the lobby, that is no longer possible."
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.0.1", "Commands, Claim Changes & Bug Fixes", "We've fixed a lot of bugs that occured over the past few days and added a few new features.", "2018-07-25",
            new Feature(
                "SPIGOT", Color.ORANGE,
                "We have updated the a new version of Spigot 1.13 and the spigot-related issues should be resolved."
            ),
            new Feature(
                "LOBBY FOOD", Color.LIME,
                "Fixed: You weren't able to consume food in the Lobby."
            ),
            new Feature(
                "CUSTOM ANVIL", Color.SILVER,
                "Fixed: Using a custom anvil would cost the player experience. Ex: Trusting players."
            ),
            new Feature(
                "TELEPORT ISSUE", Color.TEAL,
                "Fixed: /tphere would sometimes teleport you instead of the player you want to teleport."
            ),
            new Feature(
                "MAX HOMES", Color.ORANGE,
                "Fixed: Setting a home that already exists when having your maximum amount of homes would deny you to replace your home."
            ),
            new Feature(
                "TRUSTING", Color.LIME,
                "Fixed: Trusting players didn't work.."
            ),
            new Feature(
                "SMALL BUGS", Color.LIME,
                "Fixed: Several other small bugs were fixed."
            ),
            new Feature(
                "TNT", Color.RED,
                "You can now explode TNT within your own claim."
            ),
            new Feature(
                "CREEPERS", Color.RED,
                "Creepers will now explode in claims if the player has BUILDER access to the claim."
            ),
            new Feature(
                "HOME NAMES", Color.ORANGE,
                "You can use the character '_' in Home names."
            ),
            new Feature(
                "/prismshop", Color.BLUE,
                "Open the Prism & Solar Shop."
            ),
            new Feature(
                "/credits", Color.BLUE,
                "Display your credits."
            ),
            new Feature(
                "/pay <player> <amount>", Color.BLUE,
                "Give Credits to other players."
            )
        ));

        add(new Instance(Server.SURVIVAL, "v1.0.0", "Survival Release", "The first Survival release, presenting new systems for claiming and warps and many other features!", "2018-07-23",
            new Feature(
                "SURVIVAL", Color.LIME,
                "We have an amazing Lobby fitting our OrbitMines lore for Survival created by the entire OrbitMines Team.",
                "https://i.imgur.com/dBAPVie.jpg"
            ),
            new Feature(
                "LAND CLAIMING", Color.ORANGE,
                "We have completely redesigned Land Claiming and it is easy to use and easy to keep an oversight over your claims.",
                "https://i.imgur.com/zSt7Ekl.jpg"
            ),
            new Feature(
                "REGIONS", Color.RED,
                "We have added two types of regions, Under Water and Above Water. 100 Regions have been made teleportable in this update. (125 more that aren't)",
                "https://i.imgur.com/eMMywzQ.jpg"
            ),
            new Feature(
                "NETHER & END RESETS", Color.RED,
                "We have a system that automatically resets the Nether & End on a certain interval.",
                "https://i.imgur.com/IVduJdP.jpg"
            ),
            new Feature(
                "PRISM & SOLAR SHOP", Color.YELLOW,
                "We have added the Prism & Solar Shop to Survival which allows you to use your trade Solars and Prisms with items and currency in Survival.",
                "https://i.imgur.com/Fl8ggZR.jpg"
            ),
            new Feature(
                "WARPS", Color.TEAL,
                "We have created a new Warp System which allows you to have up to 3 Warps.",
                "https://i.imgur.com/PPnBupx.jpg"
            ),
            new Feature(
                "CHEST SHOPS", Color.LIME,
                "Buy and sell items through Chest Shops.",
                "https://i.imgur.com/TnHMHEG.jpg"
            ),
            new Feature(
                "/spawn", Color.BLUE,
                "Teleport to Spawn."
            ),
            new Feature(
                "/region (number)|random", Color.BLUE,
                "Open the Region GUI, teleport to a Region or teleport to a Random Region."
            ),
            new Feature(
                "/claim", Color.BLUE,
                "Receive the Claiming Tool."
            ),
            new Feature(
                "/home (name)", Color.BLUE,
                "Teleport to one of your Homes."
            ),
            new Feature(
                "/homes", Color.BLUE,
                "Display all your Homes."
            ),
            new Feature(
                "/sethome (name)", Color.BLUE,
                "Set a new Home."
            ),
            new Feature(
                "/delhome <name>", Color.BLUE,
                "Delete a Home."
            ),
            new Feature(
                "/warps", Color.BLUE,
                "Open the Warps GUI."
            ),
            new Feature(
                "/mywarps", Color.BLUE,
                "Open the Your Warp Slots."
            ),
            new Feature(
                "/accept", Color.BLUE,
                "Accept an incoming Teleport Request."
            ),
            new Feature(
                "/tp <player>", Color.BLUE,
                "Send a teleport request to a player, this feature is for GOLD+."
            ),
            new Feature(
                "/fly", Color.BLUE,
                "Enable fly, this only works in your claims and claims where you have Access, this feature is for DIAMOND+."
            ),
            new Feature(
                "/workbench", Color.BLUE,
                "Open a workbench, this feature is for DIAMOND+."
            ),
            new Feature(
                "/enderchest", Color.BLUE,
                "Open an encherchest, this feature is for EMERALD+."
            ),
            new Feature(
                "/tphere <player>", Color.BLUE,
                "Send a teleport here request to a player, this feature is for EMERALD+."
            )
        ));

        add(new Instance(Server.KITPVP, "v1.0.0", "KitPvP Release", "The KitPvP gamemode released on OrbitMines 5th anniversary, a revamp of OrbitMines' KitPvP.", "2018-10-10",
            new Feature(
                "KITPVP", Color.RED,
                "The KitPvP mode has been added to OrbitMines!"
            ),
            new Feature(
                "KITPVP LOBBY", Color.RED,
                "The KitPvP Lobby, built by Joep01 & Alderius.",
                "https://i.imgur.com/Sy97CIr.jpg"
            ),
            new Feature(
                "DESERT MAP", Color.RED,
                "The first KitPvP map! It's a revamp of an older OrbitMines KitPvP map built by Alderius.",
                "https://i.imgur.com/J9WF8Qz.jpg"
            ),
            new Feature(
                "KIT SELECTOR", Color.RED,
                "The Kit Selector allows you to easily see the differences between the different levels of a kit with its advanced system.",
                "https://i.imgur.com/A5ZpZIQ.jpg"
            ),
            new Feature(
                "PRISM SHOP", Color.BLUE,
                "You can buy coins with prisms in the KitPvP prism shop.",
                "https://i.imgur.com/bDAuD8U.jpg"
            ),
            new Feature(
                "STATS PER KIT", Color.LIME,
                "We're tracking kills, deaths and best kill streak per kit, and your total records!"
            ),
            new Feature(
                "KILLS PODIUM", Color.LIME,
                "The top 3 players with the highest kill count will be displayed in the lobby.",
                "https://i.imgur.com/gbd1HXe.jpg"
            ),
            new Feature(
                "COIN BOOSTERS", Color.ORANGE,
                "Coin boosters allow you to buy a booster for all players for 30 minutes with solars, increases multiplier with a higher rank.",
                "https://i.imgur.com/YtHFMwC.jpg"
            ),
            new Feature(
                "KNIGHT KIT", Color.GREEN,
                "The Knight kit has been added to KitPvP, a plain melee kit.",
                "https://i.imgur.com/YuYDAYZ.jpg"
            ),
            new Feature(
                "ARCHER KIT", Color.GREEN,
                "The Archer kit has been added to KitPvP, a kit focused on ranged attacks with a Lightning spell on their bow on later levels.",
                "https://i.imgur.com/4SAVZiZ.jpg"
            ),
            new Feature(
                "SOLDIER KIT", Color.GREEN,
                "The Soldier kit has been added to KitPvP, a plain melee kit at first, but turns out to have Thor's hammer on later levels.",
                "https://i.imgur.com/YSglGDY.jpg"
            ),
            new Feature(
                "MAGE KIT", Color.YELLOW,
                "The Mage kit has been added to KitPvP, a spellcaster kit who has a chance to receive more potions when killing an enemy.",
                "https://i.imgur.com/u6t9dKQ.jpg"
            ),
            new Feature(
                "TANK KIT", Color.YELLOW,
                "The Tank kit has been added to KitPvP, a melee kit with knockback evading, tankier but also slower than other kits.",
                "https://i.imgur.com/ETrG35bg.jpg"
            ),
            new Feature(
                "KING KIT", Color.YELLOW,
                "The King kit has been added to KitPvP, a melee kit with a regeneration spell on later levels.",
                "https://i.imgur.com/wrTzDwo.jpg"
            ),
            new Feature(
                "DRUNK KIT", Color.YELLOW,
                "The Drunk kit has been added to KitPvP, a drunk melee kit, or if you're up to a challenge: ranged.",
                "https://i.imgur.com/wPuYeeq.jpg"
            ),
            new Feature(
                "PYRO KIT", Color.YELLOW,
                "The Pyro kit has been added to KitPvP, a melee kit that causes and is immune to fire.",
                "https://i.imgur.com/e9YUGIZ.jpg"
            ),
            new Feature(
                "BUNNY KIT", Color.YELLOW,
                "The Bunny kit has been added to KitPvP, a speedy and jumpy melee kit with several spells on later levels.",
                "https://i.imgur.com/GOrJBEi.jpg"
            )
        ));
    }

    public Instance getHubInstance() {
        return hubInstance;
    }

    public void add(Instance instance) {
        if (!instances.containsKey(instance.server))
            instances.put(instance.server, new ArrayList<>());

        instances.get(instance.server).add(instance);
    }

    public void build() {
        hubInstance = new Instance(null, null, null, null, null);

        Server server = this.server.getType();

        Instance latest = getLatest(server);

        String version = getVersion(server);
        if (version == null) {
            new NullPointerException("Could not get " + "server:" + server.getPluginName() + ":version" + " from state, aborting patch notes check").printStackTrace();
            return;
        }

        if (latest.version.equals(version))
            return;

        broadcastToDiscord(latest);

        setVersion(latest.version);
    }

    private String getVersion(Server server) {
        return StateProvider.getInstance().getString("server:" + server.getPluginName() + ":version");
    }

    private void setVersion(String version) {
        StateProvider.getInstance().setString("server:" + server.getPluginName() + ":version", version);
    }

    public void open(OMPlayer omp, Server server, String version) {
        get(server, version).open(this, omp);
    }

    public Instance get(Server server, String version) {
        for (Instance instance : instances.get(server)) {
            if (instance.getVersion().equals(version))
                return instance;
        }
        return null;
    }

    public boolean exists(Server server, String version) {
        return get(server, version) != null;
    }

    public Instance getLatest(Server server) {
        return instances.get(server).get(0);
    }

    public List<Instance> getLatest() {
        List<Instance> latest = new ArrayList<>();

        for (Server server : instances.keySet()) {
            Instance latestForServer = getLatest(server);

            if (latest.size() == 0) {
                latest.add(latestForServer);
                continue;
            }

            long time1 = latestForServer.getDate().getTime();
            long time2 = latest.get(0).getDate().getTime();

            if (time1 > time2)
                latest.clear();

            if (time1 >= time2)
                latest.add(latestForServer);
        }

        return latest;
    }

    private void broadcastToDiscord(Instance instance) {
        TextChannel channel = server.getDiscordBot().getTextChannel(CustomChannel.PATCH_NOTES);

        Image image = Image.logoFrom(server.getType());

        if (image != null) {
            File file = image.getFile(image.toString().toLowerCase());

            if (file != null)
                channel.sendFiles(FileUpload.fromData(file)).queue();
        }
        channel.sendMessage("**" + instance.getServer().getName() + " " + instance.getVersion() + "** has been released! » \"**" + instance.getName() + "**\"").queue();
        channel.sendMessage("_" + DateUtils.format(instance.getDate(), DateUtils.DATE_FORMAT) + ": " + instance.getDescription() + "_").queue();

        for (Feature feature : instance.getFeatures()) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(feature.getName());
            builder.setDescription(feature.getDescription());
            builder.setColor(feature.getColor().getAwtColor());

            if (feature.getImageLink() != null)
                builder.setImage(feature.getImageLink());

            channel.sendMessageEmbeds(builder.build()).queue();
        }
    }

    public static class Instance {

        private final Server server;

        private final String version;
        private final String name;
        private final String description;

        private final Date date;

        private TextBuilder<OMPlayer>[] pages;
        private Feature[] features;

        public Instance(Server server, String version, String name, String description, String date, Feature... features) {
            this.server = server;
            this.version = version;
            this.name = name;
            this.description = description;
            this.date = date == null ? null : DateUtils.parse(date, DateUtils.DATE_FORMAT);
            this.features = features;

            this.pages = new TextBuilder[features.length];
            for (int i = 0; i < features.length; i++) {
                Feature feature = features[i];

                TextBuilder<OMPlayer> page = new TextBuilder<>();
                page.add(server.getColor(), p -> server.getName()).bold().add(Color.BLACK, p -> " " + version + "\n")
                    .add(Color.WHITE, p -> "\n")
                    .add(feature.color, p -> feature.getName() + "\n").bold()
                    .add(Color.BLACK, p -> feature.description + "\n")
                    .add(Color.WHITE, p -> "\n")

                ;

                if (feature.getImageLink() != null)
                    page.add(Color.SILVER, p -> "[IMAGE]").italic().
                        click(ClickEvent.Action.OPEN_URL, p -> feature.getImageLink()).
                        hover(HoverEvent.Action.SHOW_TEXT, p -> "§7§o" + p.translate("spigot", "player.patch_notes.image.click_to_open"));

                this.pages[i] = page;
            }
        }

        public Server getServer() {
            return server;
        }

        public String getVersion() {
            return version;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Date getDate() {
            return date;
        }

        public TextBuilder<OMPlayer>[] getPages() {
            return pages;
        }

        public Feature[] getFeatures() {
            return features;
        }

        public boolean isNew() {
            Date newTo = new Date(this.date.getTime() + TimeUnit.DAYS.toMillis(DAYS_PATCH_IS_NEW));

            return DateUtils.now().compareTo(newTo) < 0;
        }

        public void open(PatchNotes patchNotes, OMPlayer omp) {
            OMServer.getInstance().getNms().customItem().openBook(omp.bukkit(), getBook(patchNotes));
        }

        public WrittenBookBuilder getBook(PatchNotes patchNotes) {
            WrittenBookBuilder builder = new WrittenBookBuilder(1, "§f");
            builder.setTitle(p -> "§c§lPATCH NOTES");
            builder.setAuthor(p -> "§8§lOrbit§7§lMines");

            if (server != null) {
                TextBuilder<OMPlayer> page = new TextBuilder<>();
                page.add(Color.RED, p -> "     PATCH NOTES\n").bold()
                    .add(Color.GRAY, p -> "       §8§lOrbit§7§lMines\n").bold()
                    .add(Color.WHITE, p -> "\n")
                    .add(Color.WHITE, p -> "\n")
                    .add(Color.GRAY, p -> " Server: ").add(server.getColor(), p -> server.getDisplayName() + "\n").bold()
                    .add(Color.GRAY, p -> " " + p.translate("spigot", "player.patch_notes.version") + ": ").add(Color.BLACK, p -> version + "\n")
                    .add(Color.GRAY, p -> " " + p.translate("spigot", "player.patch_notes.date") + ": ").add(Color.BLACK, p -> DateUtils.format(date, DateUtils.DATE_FORMAT) + "\n")
                    .add(Color.GRAY, p -> " " + p.translate("spigot", "player.patch_notes.name") + ": ").add(Color.BLACK, p -> "\"" + name + "\"" + "\n")
                    .add(Color.WHITE, p -> "\n")
                    .add(Color.WHITE, p -> "\n");

                if (patchNotes.instances.get(server).size() != 1) {
                    Instance prev = getPrevious(patchNotes);
                    Instance next = getNext(patchNotes);

                    if (prev != null)
                        page.add(Color.BLACK, p -> "« " + prev.getVersion()).
                            click(ClickEvent.Action.RUN_COMMAND, p -> "/patchnotes " + server.toString() + " " + prev.getVersion()).
                            hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.patch_notes.version") + ": " + prev.getVersion() + "\n" + "§7" + p.translate("spigot", "player.patch_notes.name") + ": " + prev.getName());
                    else
                        page.add(Color.GREEN, p -> "X " + version).
                            hover(HoverEvent.Action.SHOW_TEXT, p -> "§a" + p.translate("spigot", "player.patch_notes.current_version"));

                    page.add(Color.WHITE, p -> "        ");

                    if (next != null)
                        page.add(Color.BLACK, p -> next.getVersion() + " »").
                            click(ClickEvent.Action.RUN_COMMAND, p -> "/patchnotes " + server.toString() + " " + next.getVersion()).
                            hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.patch_notes.version") + ": " + next.getVersion() + "\n" + "§7" + p.translate("spigot", "player.patch_notes.name") + ": " + next.getName());
                    else
                        page.add(Color.GREEN, p -> version + " X").
                            hover(HoverEvent.Action.SHOW_TEXT, p -> "§a" + p.translate("spigot", "player.patch_notes.current_version"));
                }

                builder.addPage(page);
            }

            for (TextBuilder<OMPlayer> page : pages) {
                builder.addPage(page);
            }

            return builder;
        }

        private boolean isLatest(PatchNotes patchNotes) {
            return patchNotes.instances.get(server).indexOf(this) == 0;
        }

        private Instance getPrevious(PatchNotes patchNotes) {
            int index = patchNotes.instances.get(server).indexOf(this);

            if (patchNotes.instances.get(server).size() > (index + 1))
                return patchNotes.instances.get(server).get(index + 1);

            return null;
        }

        private Instance getNext(PatchNotes patchNotes) {
            return isLatest(patchNotes) ? null : patchNotes.instances.get(server).get(patchNotes.instances.get(server).indexOf(this) - 1);
        }
    }

    public static class Feature {

        private final String name;
        private final Color color;
        private final String description;
        private final String imageLink;

        public Feature(String name, Color color, String description) {
            this(name, color, description, null);
        }

        public Feature(String name, Color color, String description, String imageLink) {
            this.name = name;
            this.color = color;
            this.description = description;
            this.imageLink = imageLink;
        }

        public String getName() {
            return name;
        }

        public Color getColor() {
            return color;
        }

        public String getDescription() {
            return description;
        }

        public String getImageLink() {
            return imageLink;
        }
    }
}
