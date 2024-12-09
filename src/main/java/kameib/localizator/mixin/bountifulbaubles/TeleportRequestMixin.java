package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.wormhole.TeleportRequest;
import kameib.localizator.data.Production;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TeleportRequest.class)
public abstract class TeleportRequestMixin {
    @ModifyArg(
            method = "makeReq(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/player/EntityPlayer;)Lcursedflames/bountifulbaubles/wormhole/TeleportRequest;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with target's name as its argument.
    // Line 41: origin.sendMessage(new TextComponentString("Teleport request sent to " + target.getName()));
    private static ITextComponent BountifulBaubles_TeleportRequest_makeReq_sendMessage(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Teleport request sent to ", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.makeReq", targetName);
    }
    
    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 75: from.sendMessage(new TextComponentString("Teleport request to " + to.getName() + " has expired."));
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage0(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Teleport request to ", "").replace(" has expired.", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqToExpired", targetName);
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 76: to.sendMessage(new TextComponentString("Teleport request from " + from.getName() + " has expired."));
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage1(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Teleport request from ", "").replace(" has expired.", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqFromExpired", targetName);
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 89: target.sendMessage(new TextComponentString("Rejected teleport request from " + player.getName() + "."));
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage2(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Rejected teleport request from ", "").replace(".", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqFromRejected", targetName);
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 90: player.sendMessage(new TextComponentString("Teleport request to " + target.getName() + " was rejected."));
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage3(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Teleport request to ", "").replace(" was rejected.", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqToRejected", targetName);
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 95: target.sendMessage(new TextComponentString("Accepted teleport request from " + player.getName() + "."));
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage4(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Accepted teleport request from ", "").replace(".", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqFromAccepted", targetName);
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 5,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 96: player.sendMessage(new TextComponentString("Teleport request to " + target.getName() + " was accepted."));
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage5(ITextComponent par1) {
        ITextComponent targetName = new TextComponentString(par1.getUnformattedComponentText().replace("Teleport request to ", "").replace(" was accepted.", ""))
                .setStyle(new Style().setColor(TextFormatting.YELLOW));
        return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqToAccepted", targetName);
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 6,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 113: target.sendMessage(message1);
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage6(ITextComponent original) {
        String msgOriginal = original.getUnformattedComponentText();
        ITextComponent targetName;

        if (msgOriginal.endsWith(" is asleep.")) {
            targetName = new TextComponentString(msgOriginal.replace("Teleport failed as ", "")
                    .replace(" is asleep.", ""))
                    .setStyle(new Style().setColor(TextFormatting.YELLOW));
            return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqToFailAsleep", targetName);
        }

        if (msgOriginal.endsWith(" is mounted.")) {
            targetName = new TextComponentString(msgOriginal.replace("Teleport failed as ", "")
                    .replace(" is mounted.", ""))
                    .setStyle(new Style().setColor(TextFormatting.YELLOW));
            return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqToFailMounted", targetName);
        }

        if (msgOriginal.endsWith(" has no wormhole potions or mirror.")) {
            targetName = new TextComponentString(msgOriginal.replace("Teleport failed as ", "")
                    .replace(" has no wormhole potions or mirror.", ""))
                    .setStyle(new Style().setColor(TextFormatting.YELLOW));
            return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqToFailNoWormhole", targetName);
        }
        return original;
    }

    @ModifyArg(
            method = "acceptReject(Lnet/minecraft/entity/player/EntityPlayer;ZLjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 7,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with a TextComponentTranslation with the player name as its argument.
    // Line 117: player.sendMessage(message2);
    private static ITextComponent BountifulBaubles_TeleportRequest_acceptReject_sendMessage7(ITextComponent original) {
        String msgOriginal = original.getUnformattedComponentText();

        switch (msgOriginal) {
            case "Teleport failed as you are asleep.":
                return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqFromFailAsleep");
            case "Teleport failed as you are mounted.":
                return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqFromFailMounted");
            case "Teleport failed as you have no wormhole potions or mirror.":
                return new TextComponentTranslation("message.bountifulbaubles.wormhole.reqFromFailWormhole");
        }
        return original;
    }
}
