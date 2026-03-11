package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.prison.handlers.messages.*;
import fadidev.orbitmines.prison.handlers.messages.cmd.*;
import fadidev.orbitmines.prison.handlers.messages.inv.MessageInvClickToReceive;
import fadidev.orbitmines.prison.handlers.messages.inv.MessageInvClickToTeleport;
import fadidev.orbitmines.prison.handlers.messages.inv.MessageInvSell;
import fadidev.orbitmines.prison.handlers.messages.word.*;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class PrisonMessages {

    public static Message INVENTORY_FULL = new MessageInventoryFull();
    public static Message REMOVE_CHESTSHOP = new MessageRemoveChestShop();
    public static Message CLICK_TO_RENT = new MessageClickToRent();
    public static Message CLICK_TO_ADD = new MessageClickToAdd();
    public static Message RESET_MINE = new MessageResetMine();
    public static Message RESET_MINE_PLAYER = new MessageResetMineByPlayer();
    public static Message RESET_MINE_COOLDOWN = new MessageResetMineCooldown();
    public static Message RENT_SHOP = new MessageRentShop();
    public static Message CANT_DO_THAT_HERE = new MessageCantDoThatHere();
    public static Message RANKUP = new MessageRankup();
    public static Message CANCELLED_TELEPORTATION = new MessageCancelledTeleportation();
    public static Message KIT_COOLDOWN = new MessageKitCooldown();
    public static Message SELECT_KIT = new MessageSelectKit();
    public static Message COMMAND_IN_PVP_AREA = new MessageCommandInPvPArea();

    public static Message CLEARING_PLOT = new MessageClearingPlot();
    public static Message PLOT_CLEARED = new MessagePlotCleared();

    public static Message SOLD_OUT = new MessageSoldOut();
    public static Message NOT_ENOUGH_ITEMS = new MessageNotEnoughItems();
    public static Message SHOP_OWNER_NOT_ENOUGH = new MessageShopOwnerNotEnough();
    public static Message CHEST_FULL = new MessageChestFull();
    public static Message USE_OWN_SHOP = new MessageUseOwnShop();
    public static Message SHOP_CONNECT_TO_CHEST = new MessageShopConnectToChest();
    public static Message SHOP_CREATE_ERROR = new MessageShopCreateError();
    public static Message SHOP_HIGH_PRICE = new MessageShopHighPrice();
    public static Message SHOP_CREATE = new MessageShopCreate();
    public static Message SHOP_BUY = new MessageShopBuy();
    public static Message SHOP_BUY_PLAYER = new MessageShopBuyPlayer();
    public static Message SHOP_SELL = new MessageShopSell();
    public static Message SHOP_SELL_PLAYER = new MessageShopSellPlayer();
    public static Message SHOP_EXPIRED = new MessageShopExpired();
    public static Message SHOP_ADD = new MessageShopAdd();

    public static Message INV_SELL = new MessageInvSell();
    public static Message INV_CLICK_TO_TELEPORT = new MessageInvClickToTeleport();
    public static Message INV_CLICK_TO_RECEIVE = new MessageInvClickToReceive();

    public static Message CMD_INV_SEE_CANT_VIEW = new MessageCmdInvSeeCantView();
    public static Message CMD_TOP_GOLD_RICHEST = new MessageCmdTopGoldRichest();
    public static Message CMD_RANKUP_HIGHEST = new MessageCmdRankupHighest();
    public static Message CMD_PAY = new MessageCmdPay();
    public static Message CMD_PAY_PLAYER = new MessageCmdPayPlayer();
    public static Message CMD_PAY_YOURSELF = new MessageCmdPayYourself();


    public static Message CMD_PLOT_ADD_YOURSELF = new MessageCmdPlotAddYourself();
    public static Message CMD_PLOT_ALREADY_ADDED = new MessageCmdPlotAlreadyAdded();
    public static Message CMD_PLOT_CAN_ACCESS = new MessageCmdPlotCanAccess();
    public static Message CMD_PLOT_CAN_ACCESS_PLAYER = new MessageCmdPlotCanAccessPlayer();
    public static Message CMD_PLOT_HAS_NO_PLOT = new MessageCmdPlotHasNoPlot();
    public static Message CMD_PLOT_NO_LONGER_ACCESS = new MessageCmdPlotNoLongerAccess();
    public static Message CMD_PLOT_NO_LONGER_ACCESS_PLAYER = new MessageCmdPlotNoLongerAccessPlayer();
    public static Message CMD_PLOT_NO_PLOT = new MessageCmdPlotNoPlot();
    public static Message CMD_PLOT_NOT_ADDED = new MessageCmdPlotNotAdded();
    public static Message CMD_PLOT_PLOT_PREPARING = new MessageCmdPlotPreparing();
    public static Message CMD_PLOT_REMOVE_YOURSELF = new MessageCmdPlotRemoveYourself();
    public static Message CMD_PLOT_RESET_COOLDOWN = new MessageCmdPlotResetCooldown();
    public static Message CMD_PLOT_NO_MONEY = new MessageCmdPlotNoMoney();

    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_TELEPORTING_TO = new MessageWordTeleportingTo();
    public static Message WORD_TELEPORTED_TO = new MessageWordTeleportedTo();
    public static Message WORD_DAYS = new MessageWordDays();
    public static Message WORD_HIGHEST_RANK = new MessageWordHighestRank();
    public static Message WORD_YOUR = new MessageWordYour();
    public static Message WORD_YOUR_SMALL = new MessageWordYourSmall();
    public static Message WORD_CLOCK = new MessageWordClock();
    public static Message WORD_PROBLEMS = new MessageWordProblems();
    public static Message WORD_PRICE_TO = new MessageWordPriceTo();
    public static Message WORD_COMING_SOON = new MessageWordComingSoon();
    public static Message WORD_TRY = new MessageWordTry();
    public static Message WORD_EMPTY = new MessageWordEmpty();
    public static Message WORD_CREATED = new MessageWordCreated();
}
