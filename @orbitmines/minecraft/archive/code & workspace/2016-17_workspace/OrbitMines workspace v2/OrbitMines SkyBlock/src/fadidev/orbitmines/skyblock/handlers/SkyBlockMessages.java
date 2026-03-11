package fadidev.orbitmines.skyblock.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.skyblock.handlers.messages.*;
import fadidev.orbitmines.skyblock.handlers.messages.cmd.*;
import fadidev.orbitmines.skyblock.handlers.messages.word.*;

/**
 * Created by Fadi on 20-9-2016.
 */
public class SkyBlockMessages {

    public static Message INVENTORY_FULL = new MessageInventoryFull();
    public static Message GENERATING_ISLAND = new MessageGeneratingIsland();
    public static Message NOT_ON_OWN_ISLAND = new MessageNotOnOwnIsland();
    public static Message CANCELLED_TELEPORT = new MessageCancelledTeleportation();
    public static Message NO_ISLAND = new MessageNoIsland();
    public static Message USE_KIT = new MessageUseKit();
    public static Message KIT_COOLDOWN = new MessageKitCooldown();
    public static Message NOT_OWNER = new MessageNotOwner();
    public static Message NOT_ENOUGH_ITEMS = new MessageNotEnoughItems();
    public static Message HAVE_TO_FINISH = new MessageHaveToFinish();
    public static Message REQUIRES_CHALLENGES = new MessageRequiresChallenges();

    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_NUMBER = new MessageWordNumber();
    public static Message WORD_TELEPORTING_TO = new MessageWordTeleportingTo();
    public static Message WORD_TELEPORTED_TO = new MessageWordTeleportedTo();
    public static Message WORD_YOUR = new MessageWordYour();
    public static Message WORD_EMPTY = new MessageWordEmpty();
    public static Message WORD_CREATED = new MessageWordCreated();
    public static Message WORD_PROTECTION = new MessageWordProtection();
    public static Message WORD_COMPLETED = new MessageWordCompleted();
    public static Message WORD_TIMES_COMPLETED = new MessageWordTimesCompleted();
    public static Message WORD_COMIMG_SOON = new MessageWordComingSoon();

    public static Message CMD_INV_SEE_CANT_VIEW = new MessageCmdInvSeeCantView();

    public static Message CMD_ISLAND_ALREADY_HAS = new MessageCmdIslandAlreadyHas();
    public static Message CMD_ISLAND_ALREADY_HAS_PLAYER = new MessageCmdIslandAlreadyHasPlayer();
    public static Message CMD_ISLAND_CANT_DO_THAT = new MessageCmdIslandCantDoThat();
    public static Message CMD_ISLAND_DENY = new MessageCmdIslandDeny();
    public static Message CMD_ISLAND_DENY_PLAYER = new MessageCmdIslandDenyPlayer();
    public static Message CMD_ISLAND_HAS_LIMIT_REACHED = new MessageCmdIslandHasLimitReached();
    public static Message CMD_ISLAND_HOME_SET = new MessageCmdIslandHomeSet();
    public static Message CMD_ISLAND_INVITE = new MessageCmdIslandInvite();
    public static Message CMD_ISLAND_INVITE_ACCEPT = new MessageCmdIslandInviteAccept();
    public static Message CMD_ISLAND_INVITE_MEMBERS = new MessageCmdIslandInviteMembers();
    public static Message CMD_ISLAND_INVITE_PLAYER = new MessageCmdIslandInvitePlayer();
    public static Message CMD_ISLAND_INVITE_YOURSELF = new MessageCmdIslandInviteYourself();
    public static Message CMD_ISLAND_IN_VOID = new MessageCmdIslandInVoid();
    public static Message CMD_ISLAND_JOIN = new MessageCmdIslandJoin();
    public static Message CMD_ISLAND_JOIN_MEMBER = new MessageCmdIslandJoinMember();
    public static Message CMD_ISLAND_LEAVE = new MessageCmdIslandLeave();
    public static Message CMD_ISLAND_LEAVE_MEMBER = new MessageCmdIslandLeaveMember();
    public static Message CMD_ISLAND_LIMIT_REACHED = new MessageCmdIslandLimitReached();
    public static Message CMD_ISLAND_NO_ISLAND_PLAYER = new MessageCmdIslandNoIslandPlayer();
    public static Message CMD_ISLAND_NOT_INVITED = new MessageCmdIslandNotInvited();
    public static Message CMD_ISLAND_NOT_ON_ISLAND = new MessageCmdIslandNotOnIsland();
    public static Message CMD_ISLAND_NOW_OWNER = new MessageCmdIslandNowOwner();
    public static Message CMD_ISLAND_NOW_OWNER_MEMBER = new MessageCmdIslandNowOwnerMember();
    public static Message CMD_ISLAND_REMOVE = new MessageCmdIslandRemove();
    public static Message CMD_ISLAND_REMOVE_MEMBER = new MessageCmdIslandRemoveMember();
    public static Message CMD_ISLAND_REMOVE_PLAYER = new MessageCmdIslandRemovePlayer();
    public static Message CMD_ISLAND_REMOVE_YOURSELF = new MessageCmdIslandRemoveYourself();
    public static Message CMD_ISLAND_TP_DISABLED = new MessageCmdIslandTpDisabled();
}
