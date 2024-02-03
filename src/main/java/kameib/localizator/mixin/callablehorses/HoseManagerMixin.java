package kameib.localizator.mixin.callablehorses;

import kameib.localizator.data.Production;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tschipp.callablehorses.common.HorseManager;

@Mixin(HorseManager.class)
public abstract class HoseManagerMixin {
    @ModifyArg(
            method = "callHorse(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 54: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.nohorse")), true);
    private static ITextComponent CallableHorses_HorseManager_callHorseMessage(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.nohorse").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "setHorse(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 105: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.notriding")), true);
    private static ITextComponent CallableHorses_HorseManager_setHorseMessage_notriding(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.notriding").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "setHorse(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 120: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.alreadyowned")), true);
    private static ITextComponent CallableHorses_HorseManager_setHorseMessage_alreadyowned(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.alreadyowned").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "setHorse(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 125: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.alreadypersonal")), true);
    private static ITextComponent CallableHorses_HorseManager_setHorseMessage_alreadypersonal(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.alreadypersonal").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "setHorse(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 146: player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("callablehorses.success")), true);
    private static ITextComponent CallableHorses_HorseManager_setHorseMessage_success(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.success");
    }
    @ModifyArg(
            method = "showHorseStats(Lnet/minecraft/entity/player/EntityPlayerMP;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayerMP;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 155: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.nohorse")), true);
    private static ITextComponent CallableHorses_HorseManager_showHorseStats_alreadypersonal(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.nohorse").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "canCallHorse(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 218: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.area")), true);
    private static ITextComponent CallableHorses_HorseManager_canCallHorse_errorArea(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.area").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "canCallHorse(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 221: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.riding")), true);
    private static ITextComponent CallableHorses_HorseManager_canCallHorse_errorRiding(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.riding").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "canCallHorse(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 242: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.nospace")), true);
    private static ITextComponent CallableHorses_HorseManager_canCallHorse_errorNoSpace(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.nospace").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "canCallHorse(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 3,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 260: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.dim")), true);
    private static ITextComponent CallableHorses_HorseManager_canCallHorse_errorDim(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.dim").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "canCallHorse(Lnet/minecraft/entity/player/EntityPlayer;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 285: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.range")), true);
    private static ITextComponent CallableHorses_HorseManager_canCallHorse_errorRange(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.range").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "canSetHorse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 298: player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.error.setarea")), true);
    private static ITextComponent CallableHorses_HorseManager_canSetHorse_setArea(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.error.setarea").setStyle(new Style().setColor(TextFormatting.RED));
    }
}
