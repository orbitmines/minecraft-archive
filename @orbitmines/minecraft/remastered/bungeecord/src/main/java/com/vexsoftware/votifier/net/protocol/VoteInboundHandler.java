package com.vexsoftware.votifier.net.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vexsoftware.votifier.VoteHandler;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.net.VotifierSession;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VoteInboundHandler extends SimpleChannelInboundHandler<Vote> {

    private final VoteHandler handler;

    public VoteInboundHandler(VoteHandler handler)
    {
        this.handler = handler;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Vote vote) throws Exception {
        VotifierSession session = ctx.channel().attr(VotifierSession.KEY).get();

        handler.onVoteReceived(ctx.channel(), vote, session.getVersion());

        if (session.getVersion() == VotifierSession.ProtocolVersion.ONE) {
            ctx.close();
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("status", "ok");
            ctx.writeAndFlush(new Gson().toJson(object) + "\r\n").addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        VotifierSession session = ctx.channel().attr(VotifierSession.KEY).get();

        handler.onError(ctx.channel(), cause);

        if (session.getVersion() == VotifierSession.ProtocolVersion.TWO) {
            JsonObject object = new JsonObject();
            object.addProperty("status", "error");
            object.addProperty("cause", cause.getClass().getSimpleName());
            object.addProperty("error", cause.getMessage());
            ctx.writeAndFlush(new Gson().toJson(object) + "\r\n").addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.close();
        }
    }
}
