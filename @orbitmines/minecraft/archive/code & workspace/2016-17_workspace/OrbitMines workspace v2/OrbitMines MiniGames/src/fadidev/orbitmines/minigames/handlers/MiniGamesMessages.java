package fadidev.orbitmines.minigames.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.minigames.handlers.messages.*;
import fadidev.orbitmines.minigames.handlers.messages.inv.*;
import fadidev.orbitmines.minigames.handlers.messages.word.*;

/**
 * Created by Fadi on 30-9-2016.
 */
public class MiniGamesMessages {

    public static Message WORD_RARITY = new MessageWordRarity();
    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_NONE = new MessageWordNone();
    public static Message WORD_WEAPON = new MessageWordWeapon();
    public static Message WORD_DAMAGE = new MessageWordDamage();
    public static Message WORD_CHANCE = new MessageWordChance();
    public static Message WORD_FROM = new MessageWordFrom();
    public static Message WORD_PLAYED = new MessageWordPlayed();
    public static Message WORD_BEST = new MessageWordBest();
    public static Message WORD_WINS = new MessageWordWins();
    public static Message WORD_RESULTS = new MessageWordResults();
    public static Message WORD_LOSE = new MessageWordLose();
    public static Message WORD_WIN = new MessageWordWin();
    public static Message WORD_BUILDERS = new MessageWordBuilders();
    public static Message WORD_REWARDS = new MessageWordRewards();
    public static Message WORD_FIRST_PLACE = new MessageWordFirstPlace();
    public static Message WORD_SECOND_PLACE = new MessageWordSecondPlace();
    public static Message WORD_THIRD_PLACE = new MessageWordThirdPlace();
    public static Message WORD_FIRST = new MessageWordFirst();
    public static Message WORD_SECOND = new MessageWordSecond();
    public static Message WORD_THIRD = new MessageWordThird();
    public static Message WORD_PATIENT = new MessageWordPatient();
    public static Message WORD_NEW_BALANCE = new MessageWordNewBalance();
    public static Message WORD_IN = new MessageWordIn();
    public static Message WORD_TIME_LEFT = new MessageWordTimeLeft();
    public static Message WORD_ALIVE = new MessageWordAlive();
    public static Message WORD_DEAD = new MessageWordDead();
    public static Message WORD_WINNER = new MessageWordWinner();
    public static Message WORD_DISTANCE = new MessageWordDistance();
    public static Message WORD_LOCATION = new MessageWordLocation();
    public static Message WORD_TOTAL_KILLS = new MessageWordTotalKills();
    public static Message WORD_WAITING = new MessageWordWaiting();
    public static Message WORD_PLAYERS = new MessageWordPlayers();
    public static Message WORD_CURRENT = new MessageWordCurrent();
    public static Message WORD_ACTIVATED = new MessageWordActivated();
    public static Message WORD_CLICK_TO_ACTIVATE = new MessageWordClickToActivate();
    public static Message WORD_UNAVAILABLE = new MessageWordUnavailable();
    public static Message WORD_SELECT = new MessageWordSelect();

    public static Message STAY_ON_PLATFORM = new MessageStayOnPlatform();
    public static Message SURVIVED = new MessageSurvived();
    public static Message STARTING_IN = new MessageStartingIn();
    public static Message RESTARTING_IN = new MessageRestartingIn();
    public static Message BLOCK_GLITCH = new MessageBlockGlitch();
    public static Message KILLED_BY = new MessageKilledBy();
    public static Message DIED = new MessageDied();
    public static Message DIED_VOID = new MessageDied();
    public static Message NEW_BEST_STREAK = new MessageNewBestStreak();
    public static Message THROWN_IN_VOID = new MessageThrownInVoid();
    public static Message USE_ABILITY = new MessageUseAbility();
    public static Message DIE_TO_ABILITY = new MessageDieToAbility();
    public static Message TICKET_USED_ON_START_1 = new MessageTicketUsedOnStart1();
    public static Message TICKET_USED_ON_START_2 = new MessageTicketUsedOnStart2();
    public static Message ACTIVATED = new MessageActivated();
    public static Message ACTIVATED_PLAYER = new MessageActivatedPlayer();
    public static Message CANNOT_SELECT_KIT = new MessageCannotSelectKit();
    public static Message KIT_SELECTED = new MessageKitSelected();
    public static Message CANT_CONNECT_TO_HUB = new MessageCantConnectToHub();
    public static Message CANNOT_FIND_DATA = new MessageCannotFindData();

    public static Message SG_INFO = new MessageSgInfo();
    public static Message SG_DEATHMATCH_STARTING_IN = new MessageSgDeathmatchStartingIn();
    public static Message SG_DEATHMATCH_ENDING_IN = new MessageSgDeathmatchEndingIn();
    public static Message SG_RESTOCK_CHEST = new MessageSgRestockChests();
    public static Message SG_RUNNER_DEAL_TO = new MessageSgRunnerDealTo();
    public static Message SG_RUNNER_ATTACK = new MessageSgRunnerAttack();

    public static Message GA_GHOST_KILLED_BY = new MessageGaGhostKilledBy();
    public static Message GA_GHOST_KILLED_ALL = new MessageGaGhostKilledAll();
    public static Message GA_IS_GHOST = new MessageGaIsGhost();
    public static Message GA_INFO_1 = new MessageGaInfo1();
    public static Message GA_INFO_2 = new MessageGaInfo2();
    public static Message GA_WON_AS_GHOST = new MessageGaWonAsGhost();
    public static Message GA_KILLED_GHOST = new MessageGaKilledGhost();

    public static Message CF_INFO = new MessageCfInfo();

    public static Message UHC_INFO_PVP_1 = new MessageUhcInfoPvP1();
    public static Message UHC_INFO_PVP_2 = new MessageUhcInfoPvP2();
    public static Message UHC_PVP_ENABLED_IN = new MessageUhcPvPEnabledIn();
    public static Message UHC_INFO_1 = new MessageUhcInfo1();
    public static Message UHC_INFO_2 = new MessageUhcInfo2();
    public static Message UHC_INFO_3 = new MessageUhcInfo3();
    public static Message UHC_INFO_4 = new MessageUhcInfo4();
    public static Message UHC_INFO_5 = new MessageUhcInfo5();
    public static Message UHC_TELEPORTING_ALL_PLAYERS = new MessageUhcTeleportingAllPlayers();
    public static Message UHC_GAME_ENDING_IN = new MessageUhcGameEndingIn();
    public static Message UHC_TELEPORTING_PLAYERS = new MessageUhcTeleportingPlayers();

    public static Message SW_INFO = new MessageSwInfo();

    public static Message INV_LEATHER_WILL_BE = new MessageInvLeatherWillBe();
    public static Message INV_TAKEN_DAMAGE = new MessageInvTakenDamage();
    public static Message INV_TAKEN_KNOCKBACK = new MessageInvTakenKnockback();
    public static Message INV_SPAWN_IN_CAGE = new MessageInvSpawnInCage();
    public static Message INV_MINER_KIT = new MessageInvMinerKit();
    public static Message INV_APPLETREE_KIT = new MessageInvAppleTreeKit();
    public static Message INV_WARRIOR_KIT = new MessageInvWarriorKit();
    public static Message INV_RUNNER_KIT = new MessageInvRunnerKit();
    public static Message INV_DOUBLE_LOOT_CHESTS = new MessageInvDoubleLootChests();
    public static Message INV_POTION_CHESTS = new MessageInvPotionChests();
    public static Message INV_EFFECTS_ALL = new MessageInvEffectsAll();
    public static Message INV_EFFECTS_YOU = new MessageInvEffectsYou();
    public static Message INV_CLICK_FOR_DETAILS = new MessageInvClickForDetails();
    public static Message INV_CLICK_TO_TELEPORT = new MessageInvClickToTeleport();
}
