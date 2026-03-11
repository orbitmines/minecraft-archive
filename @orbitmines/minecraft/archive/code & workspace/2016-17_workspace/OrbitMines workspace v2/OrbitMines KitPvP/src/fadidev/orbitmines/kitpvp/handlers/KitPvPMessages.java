package fadidev.orbitmines.kitpvp.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.kitpvp.handlers.messages.*;
import fadidev.orbitmines.kitpvp.handlers.messages.cmd.MessageCmdKitWhilePlaying;
import fadidev.orbitmines.kitpvp.handlers.messages.cmd.MessageCmdKitWhileSpectating;
import fadidev.orbitmines.kitpvp.handlers.messages.inv.MessageInvArrowRegen;
import fadidev.orbitmines.kitpvp.handlers.messages.inv.MessageInvBoughtKit;
import fadidev.orbitmines.kitpvp.handlers.messages.inv.MessageInvClickToTeleport;
import fadidev.orbitmines.kitpvp.handlers.messages.inv.MessageInvKitLocked;
import fadidev.orbitmines.kitpvp.handlers.messages.word.*;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitPvPMessages {

    public static Message CMD_KIT_WHILE_SPECTATING = new MessageCmdKitWhileSpectating();
    public static Message CMD_KIT_WHILE_PLAYING = new MessageCmdKitWhilePlaying();

    public static Message WORD_LOADING = new MessageWordLoading();
    public static Message WORD_COMING_SOON = new MessageWordComingSoon();
    public static Message WORD_CURRENT = new MessageWordCurrent();
    public static Message WORD_BEST = new MessageWordBest();
    public static Message WORD_BUILT_BY = new MessageWordBuiltBy();
    public static Message WORD_BACK = new MessageWordBack();
    public static Message WORD_BUY = new MessageWordBuy();
    public static Message WORD_UNAVAILABLE = new MessageWordUnavailable();
    public static Message WORD_BUY_KIT = new MessageWordBuyKit();
    public static Message WORD_SELECT_KIT = new MessageWordSelectKit();
    public static Message WORD_RIGHT_CLICK = new MessageWordRightClick();
    public static Message WORD_LEFT_CLICK = new MessageWordLeftClick();
    public static Message WORD_SATURDAY = new MessageWordSaturday();
    public static Message WORD_VOTED = new MessageWordVoted();

    public static Message INV_CLICK_TO_TELEPORT = new MessageInvClickToTeleport();
    public static Message INV_KIT_LOCKED = new MessageInvKitLocked();
    public static Message INV_BOUGHT_KIT = new MessageInvBoughtKit();
    public static Message INV_ARROW_REGEN = new MessageInvArrowRegen();

    public static Message OPENING_KIT_SELECTOR = new MessageOpeningKitSelector();
    public static Message NEW_BEST_STREAK = new MessageNewBestStreak();
    public static Message SHOT_BY = new MessageShotBy();
    public static Message KILLED_BY = new MessageKilledBy();
    public static Message REACHED_LEVEL = new MessageReachedLevel();
    public static Message KILL_STREAK = new MessageKillStreak();
    public static Message DIED = new MessageDied();
    public static Message SELECTING_KIT = new MessageSelectingKit();
    public static Message MAPS_SWITCHED = new MessageMapsSwitched();
    public static Message SWITCHING_MAPS = new MessageSwitchingMaps();
    public static Message STAY_IN_THE_ARENA = new MessageStayInTheArena();
    public static Message BOOSTER_REMAINS_FOR = new MessageBoosterRemainsFor();
    public static Message BOOSTER_EXPIRED = new MessageBoosterExpired();
    public static Message BOOSTER_ACTIVATE = new MessageBoosterActivate();
    public static Message BOOSTER_ALREADY_ACTIVE = new MessageBoosterAlreadyActive();
    public static Message BOOSTER_DURATION = new MessageBoosterDuration();
    public static Message TOGGLE_FREE_KITS = new MessageToggleFreeKits();
    public static Message YOU_KILLED = new MessageYouKilled();
    public static Message SELECT_KIT = new MessageSelectKit();
    public static Message CLICK_TO_VOTE = new MessageClickToVote();

}
