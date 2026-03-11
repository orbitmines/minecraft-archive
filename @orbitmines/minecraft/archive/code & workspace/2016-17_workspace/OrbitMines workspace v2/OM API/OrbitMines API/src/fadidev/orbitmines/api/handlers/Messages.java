package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.handlers.messages.*;
import fadidev.orbitmines.api.handlers.messages.cmd.*;
import fadidev.orbitmines.api.handlers.messages.inv.*;
import fadidev.orbitmines.api.handlers.messages.pet.*;
import fadidev.orbitmines.api.handlers.messages.word.*;

/**
 * Created by Fadi on 3-9-2016.
 */
public class Messages {

    public static Message ENABLE_CHATCOLOR = new MessageEnableChatColor();

    public static Message ENABLE_HAT_BLOCK_TRAIL = new MessageEnableHatBlockTrail();
    public static Message DISABLE_HAT_BLOCK_TRAIL = new MessageDisableHatBlockTrail();

    public static Message ENABLE_TRAIL = new MessageEnableTrail();
    public static Message DISABLE_TRAIL = new MessageDisableTrail();

    public static Message ENABLE_TRAIL_TYPE = new MessageEnableTrailType();

    public static Message SET_PARTICLE_AMOUNT = new MessageSetParticleAmount();

    public static Message ENABLE_SPECIAL_TRAIL = new MessageEnableSpecialTrail();
    public static Message DISABLE_SPECIAL_TRAIL = new MessageDisableSpecialTrail();

    public static Message ENABLE_WARDROBE_DISCO = new MessageEnableWardrobeDisco();

    public static Message NOW_AFK_EMPTY = new MessageNowAfkEmpty();
    public static Message NOW_AFK = new MessageNowAfk();
    public static Message NO_LONGER_AFK_EMPTY = new MessageNoLongerAfkEmpty();
    public static Message NO_LONGER_AFK = new MessageNoLongerAfk();

    public static Message ENABLE_GADGET = new MessageEnableGadget();
    public static Message DISABLE_GADGET = new MessageDisableGadget();
    public static Message ENABLE_FIREWORK_GUN = new MessageEnableFireworkGun();
    public static Message DISABLE_SOCCER_MAGMACUBE = new MessageDisableSoccerMagmaCube();

    public static Message ENABLE_HAT = new MessageEnableHat();
    public static Message DISABLE_HAT = new MessageDisableHat();

    public static Message ENABLE_CHATCOLOR_BOLD = new MessageEnableChatColorBold();
    public static Message DISABLE_CHATCOLOR_BOLD = new MessageDisableChatColorBold();
    public static Message ENABLE_CHATCOLOR_CURSIVE = new MessageEnableChatColorCursive();
    public static Message DISABLE_CHATCOLOR_CURSIVE = new MessageDisableChatColorCursive();

    public static Message SERVER_STARTING = new MessageDisableChatColorCursive();

    public static Message ENABLE_WARDROBE = new MessageEnableWardrobe();
    public static Message DISABLE_WARDROBE = new MessageDisableWardrobe();
    public static Message ENABLE_WARDROBE_EMERALD = new MessageEnableWardrobeEmerald();
    public static Message ENABLE_WARDROBE_DIAMOND = new MessageEnableWardrobeDiamond();
    public static Message ENABLE_WARDROBE_GOLD = new MessageEnableWardrobeGold();
    public static Message ENABLE_WARDROBE_IRON = new MessageEnableWardrobeIron();

    public static Message ENABLE_PET = new MessageEnablePet();
    public static Message DISABLE_PET = new MessageDisablePet();

    public static Message ENABLE_DISGUISE = new MessageEnableDisguise();

    public static Message RANK_RECEIVE = new MessageReceiveRank();
    public static Message VIPRANK_REQUIRED = new MessageRequiredVipRank();

    public static Message SERVER_CONNECTING = new MessageServerConnecting();
    public static Message SERVER_OFFLINE = new MessageServerOffline();

    public static Message UNKNOWN_COMMAND = new MessageUnknownCommand();

    public static Message REQUIRED_CURRENCY = new MessageRequiredCurrency();

    public static Message QUIT = new MessageQuit();
    public static Message QUIT_SILENT = new MessageQuitSilent();
    public static Message JOIN = new MessageJoin();
    public static Message JOIN_SILENT = new MessageJoinSilent();

    public static Message WORD_DEVELOPED_BY = new MessageWordDevelopedBy();
    public static Message WORD_DONATE = new MessageWordDonate();
    public static Message WORD_SPAWN_BUILT_BY = new MessageWordSpawnBuiltBy();
    public static Message WORD_VIEW = new MessageWordView();
    public static Message WORD_VOTE = new MessageWordVote();
    public static Message WORD_PRICE = new MessageWordPrice();
    public static Message WORD_REQUIRED = new MessageWordRequired();
    public static Message WORD_UNLOCKED = new MessageWordUnlocked();
    public static Message WORD_LOCKED = new MessageWordLocked();
    public static Message WORD_SETTINGS = new MessageWordSettings();
    public static Message WORD_CONFIRM = new MessageWordConfirm();
    public static Message WORD_CANCEL = new MessageWordCancel();
    public static Message WORD_INVALID = new MessageWordInvalid();
    public static Message WORD_UNKNOWN = new MessageWordUnknown();
    public static Message WORD_USE = new MessageWordUse();
    public static Message WORD_AVAILABLE = new MessageWordAvailable();
    public static Message WORD_INVALID_COMMAND = new MessageWordInvalidUsage();
    public static Message WORD_AMOUNT = new MessageWordAmount();
    public static Message WORD_REWARD = new MessageWordReward();

    public static Message INV_DISABLE_DISGUISE = new MessageInvDisableDisguise();
    public static Message INV_DISABLE_GADGET = new MessageInvDisableGadget();
    public static Message INV_DISABLE_HAT = new MessageInvDisableHat();
    public static Message INV_DISABLE_PET = new MessageInvDisablePet();
    public static Message INV_DISABLE_WARDROBE = new MessageInvDisableWardrobe();
    public static Message INV_DISABLE_TRAIL = new MessageInvDisableTrail();
    public static Message INV_CREATE_FIREWORK = new MessageInvCreativeFirework();
    public static Message INV_MORE_HATS = new MessageInvMoreHats();
    public static Message INV_A_HAT = new MessageInvAHat();
    public static Message INV_A_TRAIL = new MessageInvATrail();
    public static Message INV_A_PET = new MessageInvAPet();
    public static Message INV_RENAME_PET = new MessageInvRenamePet();
    public static Message INV_RENAME_PET_EMPTY = new MessageInvRenamePetEmpty();
    public static Message INV_PET_RENAMED = new MessageInvPetRenamed();
    public static Message INV_INSERT_PET_NAME = new MessageInvInsertPetName();
    public static Message INV_USES_UNLOCKED_ARMOR = new MessageInvUsesUnlockedArmor();
    public static Message INV_HAT_NOT_ENABLED = new MessageInvHatNotEnabled();
    public static Message INV_TRAIL_NOT_ENABLED = new MessageInvTrailNotEnabled();
    public static Message INV_WARDROBE_NOT_ENABLED = new MessageInvWardrobeNotEnabled();
    public static Message INV_DISGUISE_NOT_ENABLED = new MessageInvDisguiseNotEnabled();
    public static Message INV_PET_NOT_ENABLED = new MessageInvPetNotEnabled();
    public static Message INV_TELEPORTING_TO = new MessageInvTeleportingTo();

    public static Message INV_ACHIEVED_AT_CHRISTMAS = new MessageInvAchievedAtChristmas();
    public static Message INV_ACHIEVED_AT_OM_BIRTHDAY = new MessageInvAchievedAtOMBirthday();

    public static Message INV_ITEM_BOUGHT = new MessageInvItemBought();
    public static Message INV_PURCHASE_CANCELLED = new MessageInvPurchaseCancelled();

    public static Message COOLDOWN_LEFT_CLICK = new MessageCooldownLeftClick();
    public static Message COOLDOWN_RIGHT_CLICK = new MessageCooldownRightClick();

    public static Message CMD_NOT_ONLINE = new MessageCmdNotOnline();
    public static Message CMD_NOT_NUMBER = new MessageCmdNotNumber();
    public static Message CMD_NO_ITEM_IN_HAND = new MessageCmdNoItemInHand();
    public static Message CMD_LONG_AFK_TEXT = new MessageCmdLongAfkText();
    public static Message CMD_INVALID_AFK_USAGE = new MessageCmdInvalidAfkUsage();
    public static Message CMD_INVALID_DISGUISE_BLOCK_USAGE = new MessageCmdInvalidDisguiseBlockUsage();
    public static Message CMD_DISGUISED_AS = new MessageCmdDisguisedAs();
    public static Message CMD_DISGUISED_NEAR_AS = new MessageCmdDisguisedNearAs();
    public static Message CMD_DISGUISED_PLAYER_AS = new MessageCmdDisguisedPlayerAs();
    public static Message CMD_DISGUISED_AS_BLOCK = new MessageCmdDisguisedAsBlock();
    public static Message CMD_DISGUISED_NEAR_AS_BLOCK = new MessageCmdDisguisedNearAsBlock();
    public static Message CMD_DISGUISED_PLAYER_AS_BLOCK = new MessageCmdDisguisedPlayerAsBlock();
    public static Message CMD_HELP_NEAR_TO_BLOCK = new MessageCmdHelpNearToBlock();
    public static Message CMD_HELP_NEAR_TO_MOB = new MessageCmdHelpNearToMob();

    public static Message CMD_RESTORE_FOODBAR = new MessageCmdRestoreFoodbar();
    public static Message CMD_RESTORE_FOODBAR_PLAYER = new MessageCmdRestoreFoodbarPlayer();
    public static Message CMD_FOODBAR_RESTORED = new MessageCmdFoodbarRestored();
    public static Message CMD_TOGGLE_FLY = new MessageCmdToggleFly();
    public static Message CMD_TOGGLE_FLY_PLAYER = new MessageCmdToggleFlyPlayer();
    public static Message CMD_FLY_TOGGLED = new MessageCmdFlyToggled();
    public static Message CMD_GM_SET = new MessageCmdGmSet();
    public static Message CMD_GM_SET_PLAYER = new MessageCmdGmSetPlayer();
    public static Message CMD_GM_SET_BY = new MessageCmdGmSetBy();
    public static Message CMD_GIVE = new MessageCmdGive();
    public static Message CMD_GIVE_PLAYER = new MessageCmdGivePlayer();
    public static Message CMD_GIVEN = new MessageCmdGiven();
    public static Message CMD_HEAL = new MessageCmdHeal();
    public static Message CMD_HEAL_PLAYER = new MessageCmdHealPlayer();
    public static Message CMD_HEALED = new MessageCmdHealed();
    public static Message CMD_NICK_REMOVE = new MessageCmdNickRemove();
    public static Message CMD_NICK_CHANGE = new MessageCmdNickChange();
    public static Message CMD_NICK_TOO_LONG = new MessageCmdNickTooLong();
    public static Message CMD_SET_OP = new MessageCmdSetOp();
    public static Message CMD_SKULL_GIVE = new MessageCmdSkullGive();
    public static Message CMD_TP = new MessageCmdTp();
    public static Message CMD_TP_TO_SELF = new MessageCmdTpToSelf();
    public static Message CMD_TP_PLAYER = new MessageCmdTpPlayer();
    public static Message CMD_TP_PLAYER_CORDS = new MessageCmdTpPlayerCords();
    public static Message CMD_TP_CORDS = new MessageCmdTpCords();
    public static Message CMD_UNDISGUISE = new MessageCmdUnDisguise();
    public static Message CMD_NOT_DISGUISED = new MessageCmdNotDisguised();
    public static Message CMD_INFO_LOADING_UUID = new MessageCmdInfoLoadingUuid();
    public static Message CMD_INFO_LOADING = new MessageCmdInfoLoading();
    public static Message CMD_INFO_COPY_UUID = new MessageCmdInfoCopyUuid();
    public static Message CMD_INFO_HISTORY = new MessageCmdInfoHistory();
    public static Message CMD_VOTE_FOR = new MessageCmdVoteFor();
    public static Message CMD_VOTE_SERVER = new MessageCmdVoteServer();
    public static Message CMD_VOTE_AT = new MessageCmdVoteAt();
    public static Message CMD_VOTE_TOTAL = new MessageCmdVoteTotal();
    public static Message CMD_VOTE_THANKS = new MessageCmdVoteThanks();
    public static Message CMD_VOTE_PLAYER = new MessageCmdVotePlayer();
    public static Message CMD_TOTAL_VIPPOINTS = new MessageCmdTotalVipPoints();
    public static Message CMD_TOTAL_OMT = new MessageCmdTotalOmt();

    public static Message PET_CHANGE_TYPE = new MessagePetChangeType();
    public static Message PET_EXPLODE = new MessagePetExplode();
    public static Message PET_CHANGE_COLOR = new MessagePetChangeColor();
    public static Message PET_CHANGE_SPEED = new MessagePetChangeSpeed();
    public static Message PET_SPEED_CHANGED = new MessagePetSpeedChanged();
    public static Message PET_BOMB = new MessagePetBomb();
    public static Message PET_TOGGLE_SHEEP_DISCO = new MessagePetToggleSheepDisco();
    public static Message PET_CHANGE_AGE = new MessagePetChangeAge();
    public static Message PET_CHANGE_AGE_BABY = new MessagePetChangeAgeBaby();
    public static Message PET_CHANGE_AGE_ADULT = new MessagePetChangeAgeAdult();
    public static Message PET_CHANGE_SIZE = new MessagePetChangeSize();
    public static Message PET_SIZE_CHANGED = new MessagePetSizeChanged();
    public static Message PET_TOGGLE_BABY_PIGS = new MessagePetToggleBabyPigs();
    public static Message PET_TOGGLE_SHROOM_TRAIL = new MessagePetToggleShroomTrail();
    public static Message PET_ENABLE_MAGMACUBE_BALL = new MessagePetEnableMagmaCubeBall();
    public static Message PET_TELEPORT_MAGMACUBE_BALL = new MessagePetTeleportMagmaCubeBall();
    public static Message PET_SUMMON_SGA = new MessagePetSummonSga();

    public static Message NO_FIREWORK_PASSES = new MessageNoFireworkPasses();

    public static Message STACK_DISABLED = new MessageStackDisabled();
    public static Message PLAYERS_DISABLED = new MessagePlayersDisabled();
    public static Message STACKER_DISABLED = new MessageStackerDisabled();
    public static Message STACK = new MessageStack();
    public static Message STACK_PLAYER = new MessageStackPlayer();
    public static Message SWAP_TP = new MessageSwapTp();
    public static Message SWAP_TP_PLAYER = new MessageSwapTpPlayer();
}
