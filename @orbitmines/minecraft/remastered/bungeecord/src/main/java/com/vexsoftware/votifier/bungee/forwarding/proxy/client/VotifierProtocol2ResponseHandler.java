package com.vexsoftware.votifier.bungee.forwarding.proxy.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VotifierProtocol2ResponseHandler extends SimpleChannelInboundHandler<String> {

    private final VotifierResponseHandler responseHandler;

    public VotifierProtocol2ResponseHandler(VotifierResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        JsonObject object = new JsonParser().parse(msg).getAsJsonObject();
        String status = object.get("status").getAsString();
        if (status.equals("ok")) {
            responseHandler.onSuccess();
        } else {
            responseHandler.onFailure(new Exception("Remote server error: " + object.get("cause").getAsString() + ": " + object.get("error").getAsString()));
        }
        ctx.close();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        responseHandler.onFailure(cause);
        ctx.close();
    }
}
