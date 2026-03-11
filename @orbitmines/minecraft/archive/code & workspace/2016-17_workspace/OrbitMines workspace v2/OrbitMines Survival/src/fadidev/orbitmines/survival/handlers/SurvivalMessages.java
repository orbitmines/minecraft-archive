package fadidev.orbitmines.survival.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.survival.handlers.messages.*;
import fadidev.orbitmines.survival.handlers.messages.cmd.*;
import fadidev.orbitmines.survival.handlers.messages.inv.*;
import fadidev.orbitmines.survival.handlers.messages.word.*;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class SurvivalMessages {

    public static Message CLOSE_TO_REGION = new MessageCloseToRegion();
    public static Message INVENTORY_FULL = new MessageInventoryFull();
    public static Message TIME_REMAINING = new MessageTimeRemaining();
    public static Message CANCELLED_PVP_TP = new MessageCancelledPvPTp();
    public static Message CANCELLED_TELEPORTATION = new MessageCancelledTeleportation();
    public static Message REMOVE_CHESTSHOP = new MessageRemoveChestShop();
    public static Message JOINED_PVP_AREA = new MessageJoinedPvPArea();
    public static Message CANNOT_PERFORM_COMMANDS = new MessageCannotPerformCommands();
    public static Message CANNOT_PERFORM_COMMANDS_JOINING = new MessageCannotPerformCommandsJoining();

    public static Message SOLD_OUT = new MessageSoldOut();
    public static Message NOT_ENOUGH_ITEMS = new MessageNotEnoughItems();
    public static Message SHOP_OWNER_NOT_ENOUGH = new MessageShopOwnerNotEnough();
    public static Message CHEST_FULL = new MessageChestFull();
    public static Message USE_OWN_SHOP = new MessageUseOwnShop();
    public static Message SHOP_LIMIT_REACHED = new MessageShopLimitReached();
    public static Message SHOP_CONNECT_TO_CHEST = new MessageShopConnectToChest();
    public static Message SHOP_CREATE_ERROR = new MessageShopCreateError();
    public static Message SHOP_HIGH_PRICE = new MessageShopHighPrice();
    public static Message SHOP_CREATE = new MessageShopCreate();
    public static Message SHOP_BUY = new MessageShopBuy();
    public static Message SHOP_BUY_PLAYER = new MessageShopBuyPlayer();
    public static Message SHOP_SELL = new MessageShopSell();
    public static Message SHOP_SELL_PLAYER = new MessageShopSellPlayer();

    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_CHANGE_TO = new MessageWordChangeTo();
    public static Message WORD_TELEPORTED_TO = new MessageWordTeleportedTo();
    public static Message WORD_TELEPORTING_TO = new MessageWordTeleportingTo();
    public static Message WORD_YOUR = new MessageWordYour();
    public static Message WORD_FAVORITE = new MessageWordFavorite();
    public static Message WORD_SEARCH = new MessageWordSearch();
    public static Message WORD_SCROLL_DOWN = new MessageWordScrollDown();
    public static Message WORD_SCROLL_UP = new MessageWordScrollUp();
    public static Message WORD_UNAVAILABLE = new MessageWordUnavailable();
    public static Message WORD_EMPTY = new MessageWordEmpty();
    public static Message WORD_MONEY = new MessageWordMoney();

    public static Message CMD_BACK_CANNOT = new MessageCmdBackCannot();
    public static Message CMD_BACK_TELEPORTED = new MessageCmdBackTeleported();
    public static Message CMD_MONEY_SHOW = new MessageCmdMoneyShow();
    public static Message CMD_SET_HOME = new MessageCmdSetHome();
    public static Message CMD_SET_NEW_HOME = new MessageCmdSetNewHome();
    public static Message CMD_TP_HERE = new MessageCmdTpHere();
    public static Message CMD_TP_HERE_PLAYER = new MessageCmdTpHerePlayer();
    public static Message CMD_EDIT_WARP_NOT_ALLOWED = new MessageCmdEditWarpNotAllowed();
    public static Message CMD_SET_WARP_OVERWORLD = new MessageCmdSetWarpOverworld();
    public static Message CMD_SET_WARP_CANT_CREATE = new MessageCmdSetWarpCantCreate();
    public static Message CMD_SET_WARP_LIMIT_REACHED = new MessageCmdSetWarpLimitReached();
    public static Message CMD_TOP_MONEY_RICHEST = new MessageCmdTopMoneyRichest();
    public static Message CMD_NO_HOMES = new MessageCmdNoHomes();
    public static Message CMD_HOME_NONE = new MessageCmdHomeNone();
    public static Message CMD_HOME_NO_HOME_NAMED = new MessageCmdHomeNoHomeNamed();
    public static Message CMD_SET_HOME_ONLY_CHARACTERS = new MessageCmdSetHomeOnlyCharacters();
    public static Message CMD_SET_HOME_LIMIT_REACHED = new MessageCmdSetHomeLimitReached();
    public static Message CMD_DEL_HOME = new MessageCmdDelHome();
    public static Message CMD_INV_SEE_CANT_VIEW = new MessageCmdInvSeeCantView();
    public static Message CMD_ACCEPT_NOT_ONLINE = new MessageCmdAcceptNotOnline();
    public static Message CMD_ACCEPTED = new MessageCmdAccepted();
    public static Message CMD_ACCEPT_TO_THEM = new MessageCmdAcceptToThem();
    public static Message CMD_ACCEPT_TO_YOU = new MessageCmdAcceptToYou();
    public static Message CMD_MY_WARPS_NONE = new MessageCmdMyWarpsNone();
    public static Message CMD_TP_PLAYER = new MessageCmdTpPlayer();
    public static Message CMD_FLY_DISABLED_IN_END = new MessageCmdFlyDisabledInEnd();
    public static Message CMD_FLY_ONLY_IN_CLAIM = new MessageCmdFlyOnlyInClaim();
    public static Message CMD_INVSEE = new MessageCmdInvSee();
    public static Message CMD_INVSEE_PLAYER = new MessageCmdInvSeePlayer();
    public static Message CMD_INVSEE_ACCEPT = new MessageCmdInvSeeAccept();

    public static Message INV_CHANGE_WARP_ITEM = new MessageInvChangeWarpItem();
    public static Message INV_LOCATION_CHANGED = new MessageInvLocationChanged();
    public static Message INV_SET_LOCATION = new MessageInvSetLocation();
    public static Message INV_CHANGE_ITEM = new MessageInvChangeItem();
    public static Message INV_RENAME_WARP = new MessageInvRenameWarp();
    public static Message INV_WARP_DISABLED = new MessageInvWarpDisabled();
    public static Message INV_TELEPORT = new MessageInvTeleport();
    public static Message INV_REMOVE_FAVORITE = new MessageInvRemoveFavorite();
    public static Message INV_ADD_FAVORITE = new MessageInvAddFavorite();
    public static Message INV_WARP_EXISTS = new MessageInvWarpExists();
    public static Message INV_MAX_CHARACTERS = new MessageInvMaxCharacters();
    public static Message INV_ONLY_CHARACTERS = new MessageInvOnlyCharacters();
    public static Message INV_CHANGE_WARP_NAME = new MessageInvChangeWarpName();
    public static Message INV_CREATE_WARP = new MessageInvCreateWarp();
}
