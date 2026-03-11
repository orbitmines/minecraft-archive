package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.hub.handlers.messages.*;
import fadidev.orbitmines.hub.handlers.messages.cmd.*;
import fadidev.orbitmines.hub.handlers.messages.inv.*;
import fadidev.orbitmines.hub.handlers.messages.word.*;

/**
 * Created by Fadi on 8-9-2016.
 */
public class HubMessages {

    public static Message CAN_CHAT_ENABLED = new MessageCanChatEnabled();
    public static Message JOINED_ORBITMINES = new MessageJoinedOrbitMines();
    public static Message CLAIM_MONTHLY_VIPPOINTS = new MessageClaimMonthlyVipPoints();
    public static Message LEAVE_LAPIS_PARKOUR = new MessageLeaveLapisParkour();
    public static Message CANT_STOP_SPRINTING = new MessageCantStopSprinting();
    public static Message GOOD_LUCK = new MessageGoodLuck();
    public static Message COMPLETED_LAPIS_PARKOUR = new MessageCompletedLapisParkour();
    public static Message BASED_ON_MASTER_MIND = new MessageBasedOnMasterMind();
    public static Message LEAVE_MINDCRAFT = new MessageLeaveMindcraft();
    public static Message RESET_COLORS = new MessageResetColors();
    public static Message END_TURN = new MessageEndTurn();
    public static Message KICK_SIGN = new MessageKickSign();
    public static Message RIGHT_COMBINATION = new MessageRightCombination();
    public static Message YOU_WON = new MessageYouWon();
    public static Message YOU_LOST = new MessageYouLost();
    public static Message NOT_USED_ALL_SLOTS = new MessageNotUsedAllSlots();

    public static Message SERVER_CLOSED = new MessageServerClosed();
    public static Message SERVER_FULL = new MessageServerFull();
    public static Message SERVER_JOINING = new MessageServerJoining();
    public static Message SERVER_RESTARTING = new MessageServerRestarting();

    public static Message WORD_RARITY = new MessageWordRarity();
    public static Message WORD_STAAN_NU = new MessageWordStaanNu();
    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_BEST = new MessageWordBest();
    public static Message WORD_NONE = new MessageWordNone();
    public static Message WORD_TURN = new MessageWordTurn();
    public static Message WORD_TURNS = new MessageWordTurns();
    public static Message WORD_ALL_PLAYERS = new MessageWordAllPlayers();
    public static Message WORD_WINS = new MessageWordWins();
    public static Message WORD_WEAPON = new MessageWordWeapon();
    public static Message WORD_DAMAGE = new MessageWordDamage();
    public static Message WORD_CHANCE = new MessageWordChance();
    public static Message WORD_FROM = new MessageWordFrom();
    public static Message WORD_PLAYED = new MessageWordPlayed();
    public static Message WORD_COMING_SOON = new MessageWordComingSoon();
    public static Message WORD_OTHER_PLACE = new MessageWordOtherPlace();

    public static Message CMD_CAGE_CREATE = new MessageCmdCageCreate();
    public static Message CMD_CAGE_GENERATED = new MessageCmdCageGenerated();
    public static Message CMD_CAGE_GENERATING = new MessageCmdCageGenerating();
    public static Message CMD_CAGE_WAND = new MessageCmdCageWand();
    public static Message CMD_CAGE_FIRST_NEW = new MessageCmdCageFirstNew();

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
    public static Message INV_YOU_FOUND = new MessageInvYouFound();
    public static Message INV_PLAYER_FOUND = new MessageInvPlayerFound();
    public static Message INV_ALREADY_UNLOCKED = new MessageInvAlreadyUnlocked();
    public static Message INV_CLICK_FOR_DETAILS = new MessageInvClickForDetails();
}
