package com.vexsoftware.votifier.net.protocol;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vexsoftware.votifier.VotifierPlugin;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.net.VotifierSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.List;
import java.util.UUID;

public class VotifierProtocol2Decoder extends MessageToMessageDecoder<String> {

    private static final SecureRandom RANDOM = new SecureRandom();

    public VotifierProtocol2Decoder() {}

    protected void decode(ChannelHandlerContext ctx, String s, List<Object> list) throws Exception {
        JsonObject voteMessage = new JsonParser().parse(s).getAsJsonObject();
        VotifierSession session = ctx.channel().attr(VotifierSession.KEY).get();

        JsonObject votePayload = voteMessage.get("payload").getAsJsonObject();

        if (!votePayload.get("challenge").getAsString().equals(session.getChallenge())) {
            throw new CorruptedFrameException("Challenge is not valid");
        }

        VotifierPlugin plugin = ctx.channel().attr(VotifierPlugin.KEY).get();
        Key key = plugin.getTokens().get(votePayload.get("serviceName").getAsString());

        if (key == null) {
            key = plugin.getTokens().get("default");
            if (key == null) {
                throw new RuntimeException("Unknown service '" + votePayload.get("serviceName").getAsString() + "'");
            }
        }

        String sigHash = voteMessage.get("signature").getAsString();
        byte[] sigBytes = DatatypeConverter.parseBase64Binary(sigHash);

        if (!hmacEqual(sigBytes, voteMessage.get("payload").getAsString().getBytes(StandardCharsets.UTF_8), key)) {
            throw new CorruptedFrameException("Signature is not valid (invalid token?)");
        }

        if (votePayload.has("uuid")) {
            UUID.fromString(votePayload.get("uuid").getAsString());
        }

        if (votePayload.get("username").getAsString().length() > 16) {
            throw new CorruptedFrameException("Username too long");
        }

        Vote vote = new Vote(votePayload);
        list.add(vote);

        ctx.pipeline().remove(this);
    }

    private boolean hmacEqual(byte[] sig, byte[] message, Key key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] calculatedSig = mac.doFinal(message);

        byte[] randomKey = new byte[32];
        RANDOM.nextBytes(randomKey);

        Mac mac2 = Mac.getInstance("HmacSHA256");
        mac2.init(new SecretKeySpec(randomKey, "HmacSHA256"));
        byte[] clientSig = mac2.doFinal(sig);
        mac2.reset();
        byte[] realSig = mac2.doFinal(calculatedSig);

        return MessageDigest.isEqual(clientSig, realSig);
    }
}
