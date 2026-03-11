package fadidev.orbitmines.creative.handlers;


import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.creative.handlers.messages.*;
import fadidev.orbitmines.creative.handlers.messages.cmd.*;
import fadidev.orbitmines.creative.handlers.messages.inv.*;
import fadidev.orbitmines.creative.handlers.messages.word.*;

/**
 * Created by Fadi on 14-9-2016.
 */
public class CreativeMessages {

    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_EMPTY = new MessageWordEmpty();
    public static Message WORD_SET = new MessageWordSet();
    public static Message WORD_FINISH = new MessageWordFinish();
    public static Message WORD_UNAVAILABLE = new MessageWordUnavailable();
    public static Message WORD_BUILDING = new MessageWordBuilding();
    public static Message WORD_CREATED = new MessageWordCreated();
    public static Message WORD_NUMBER = new MessageWordNumber();

    public static Message CLEARING_PLOT = new MessageClearingPlot();
    public static Message PLOT_CLEARED = new MessagePlotCleared();
    public static Message SELECTED_KIT = new MessageSelectedKit();
    public static Message CANT_DO_THAT_HERE = new MessageCantDoThatHere();
    public static Message SPAWN_TELEPORTING = new MessageCmdSpawnTeleporting();
    public static Message TELEPORTED_TO_SPAWN = new MessageTeleportedToSpawn();
    public static Message LEFT_PLOT = new MessageLeftPlot();
    public static Message LEFT_PLOT_PLAYER = new MessageLeftPlotPlayer();
    public static Message TELEPORTING_TO_SPAWN = new MessageTeleportingToSpawn();
    public static Message LEAVING_PLOT = new MessageLeavingPlot();
    public static Message TELEPORTED_TO_YOUR_PLOT = new MessageTeleportedToYourPlot();
    public static Message CANCEL_SPAWN_TP = new MessageCancelSpawnTp();
    public static Message CANCEL_PLOT_TP = new MessageCancelPlotTp();

    public static Message INV_ADD_KIT = new MessageInvAddKit();
    public static Message INV_CAN_NOW_PVP = new MessageInvCanNowPvP();
    public static Message INV_RESET_SPAWNPOINTS = new MessageInvResetSpawnpoints();
    public static Message INV_SET_LOBBY = new MessageInvSetLobby();
    public static Message INV_SET_SPAWNPOINT = new MessageInvSetSpawnpoint();
    public static Message INV_SETUP_FINISHED = new MessageInvSetupFinished();
    public static Message INV_CAN_FINISH_SETUP = new MessageInvCanFinishSetup();
    public static Message INV_DELETE_KIT = new MessageInvDeleteKit();
    public static Message INV_KIT_DELETED = new MessageInvKitDeleted();
    public static Message INV_CREATE_KIT = new MessageInvCreateKit();
    public static Message INV_ONLY_CHARACTERS = new MessageInvOnlyCharacters();
    public static Message INV_TOO_LONG_NAME = new MessageInvTooLongName();
    public static Message INV_KIT_NAME_USED = new MessageInvKitNameUsed();
    public static Message INV_INSERT_KIT_NAME = new MessageInvInsertKitName();
    public static Message INV_SET_BUILD_MODE = new MessageInvSetBuildMode();
    public static Message INV_SET_BUILD_MODE_PLAYER = new MessageInvSetBuildModePlayer();
    public static Message INV_SETUP_NOT_FINISHED = new MessageInvSetupNotFinished();
    public static Message INV_BROADCAST_INFO = new MessageInvBroadcastInfo();
    public static Message INV_SET_PVP_MODE = new MessageInvSetPvPMode();

    public static Message CMD_PLOT_ADD_YOURSELF = new MessageCmdPlotAddYourself();
    public static Message CMD_PLOT_ALREADY_ADDED = new MessageCmdPlotAlreadyAdded();
    public static Message CMD_PLOT_ALREADY_ON = new MessageCmdPlotAlreadyOn();
    public static Message CMD_PLOT_BROADCAST = new MessageCmdPlotBroadcast();
    public static Message CMD_PLOT_BROADCAST_CLICK = new MessageCmdPlotBroadcastClick();
    public static Message CMD_PLOT_CAN_ACCESS = new MessageCmdPlotCanAccess();
    public static Message CMD_PLOT_CAN_ACCESS_PLAYER = new MessageCmdPlotCanAccessPlayer();
    public static Message CMD_PLOT_CANNOT_SET_HOME = new MessageCmdPlotCannotSetHome();
    public static Message CMD_PLOT_FULL = new MessageCmdPlotFull();
    public static Message CMD_PLOT_HAS_NO_PLOT = new MessageCmdPlotHasNoPlot();
    public static Message CMD_PLOT_HOME_SET = new MessageCmdPlotHomeSet();
    public static Message CMD_PLOT_IN_BUILD_MODE = new MessageCmdPlotInBuildMode();
    public static Message CMD_PLOT_IN_BUILD_MODE_PLAYER = new MessageCmdPlotInBuildModePlayer();
    public static Message CMD_PLOT_JOINED_PLAYER = new MessageCmdPlotJoinedPlayer();
    public static Message CMD_PLOT_JOIN_PLOT = new MessageCmdPlotJoinPlot();
    public static Message CMD_PLOT_LEAVING = new MessageCmdPlotLeaving();
    public static Message CMD_PLOT_MUST_BE_PVP = new MessageCmdPlotMustBePvP();
    public static Message CMD_PLOT_NO_LONGER_ACCESS = new MessageCmdPlotNoLongerAccess();
    public static Message CMD_PLOT_NO_LONGER_ACCESS_PLAYER = new MessageCmdPlotNoLongerAccessPlayer();
    public static Message CMD_PLOT_NO_PLOT = new MessageCmdPlotNoPlot();
    public static Message CMD_PLOT_NOT_ADDED = new MessageCmdPlotNotAdded();
    public static Message CMD_PLOT_PLOT_PREPARING = new MessageCmdPlotPreparing();
    public static Message CMD_PLOT_REMOVE_YOURSELF = new MessageCmdPlotRemoveYourself();
    public static Message CMD_PLOT_RESET_COOLDOWN = new MessageCmdPlotResetCooldown();
    public static Message CMD_PLOT_TP_TO_PLOT = new MessageCmdPlotTpToPlot();
    public static Message CMD_PLOT_TP_TO_YOUR_PLOT = new MessageCmdPlotTpToYourPlot();
    public static Message CMD_PLOT_ON_PVP_PLOT = new MessageCmdPlotOnPvPPlot();

    public static Message CMD_WE_OUTSIDE_PLOT = new MessageCmdWEOutsidePlot();
    public static Message CMD_WE_IN_PVP_PLOT = new MessageCmdWEInPvPPlot();
    public static Message CMD_WE_BUY_AT_SHOP = new MessageCmdWEBuyAtShop();

}
