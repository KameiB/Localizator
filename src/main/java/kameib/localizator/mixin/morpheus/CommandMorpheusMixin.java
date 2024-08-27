package kameib.localizator.mixin.morpheus;

import kameib.localizator.data.Production;
import net.minecraft.command.CommandBase;
import net.minecraft.util.text.*;
import net.quetzi.morpheus.Morpheus;
import net.quetzi.morpheus.commands.CommandMorpheus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CommandMorpheus.class)
public abstract class CommandMorpheusMixin extends CommandBase {
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 49: sender.sendMessage((new TextComponentString("Usage: /morpheus <alert : version> | /morpheus percent <percentage> | /morpheus disable <dimension number>")).setStyle((new Style()).setColor(TextFormatting.RED)));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_Usage(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.Usage", new TextComponentTranslation("command.morpheus.Example")).setStyle((new Style()).setColor(TextFormatting.RED));
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 1,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 54: sender.sendMessage(new TextComponentString("Text alerts turned off"));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_AlertsOff(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.AlertsOff");
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 2,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 57: sender.sendMessage(new TextComponentString("Text alerts turned on"));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_AlertsOn(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.AlertsOn");
    }
    
    @ModifyVariable(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            name = "setPercent",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            remap = Production.inProduction
    )
    // Catch setPercent for later use
    private boolean Morpheus_CommandMorpheus_execute_setPercent_catch(boolean value) {
        localizator$mySetPercent = value;
        return value;
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 4,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 73: sender.sendMessage(new TextComponentString("Sleep vote percentage set to " + Morpheus.perc + "%"));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_SleeperPercOP(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.OP.PercentSet", Morpheus.perc).setStyle(new Style().setColor(TextFormatting.GREEN));
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 5,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 75: sender.sendMessage(new TextComponentString("Invalid percentage value, round numbers between 0 and 100 are acceptable."));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_SleeperPercInvalid(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.OP.PercentInvalid").setStyle(new Style().setColor(TextFormatting.RED));
    }

    @ModifyVariable(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            name = "newPercent",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            remap = Production.inProduction
    )
    // Catch setPercent for later use
    private int Morpheus_CommandMorpheus_execute_newPercent_catch(int value) {
        localizator$myDimension = value;
        return value;
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 6,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 81: sender.sendMessage(new TextComponentString("Disabled sleep vote checks in dimension " + newPercent));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_DisableSet(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.OP.DisableSet", localizator$myDimension);
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 7,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 83: sender.sendMessage(new TextComponentString("Sleep vote checks are already disabled in dimension " + newPercent));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_DisableAlready(ITextComponent component) {
        return new TextComponentTranslation("command.morpheus.OP.DisableAlready", localizator$myDimension);
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 8,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 87: sender.sendMessage(new TextComponentString("You must be opped to " + (setPercent ? "set the sleep vote percentage." : "disable dimensions.")));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_notOP(ITextComponent component) {
        return new TextComponentTranslation(localizator$mySetPercent ? "command.morpheus.notOP.Percent" : "command.morpheus.notOP.Disable");
    }

    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 9,
                    remap = Production.inProduction
            ),
            remap = Production.inProduction
    )
    // Replace the TextComponentString with a TextComponentTranslation
    // Line 90: sender.sendMessage((new TextComponentString(setPercent ? "Usage: /morpheus percent <percentage>" : "Usage: /morpheus disable <dimension number>")).setStyle((new Style()).setColor(TextFormatting.RED)));
    private ITextComponent Morpheus_CommandMorpheus_execute_sendMessage_UsageShort(ITextComponent component) {
        return new TextComponentTranslation(localizator$mySetPercent ? "command.morpheus.usage.Percent" : "command.morpheus.usage.Disable").setStyle((new Style()).setColor(TextFormatting.RED));
    }
    
    @Unique
    private boolean localizator$mySetPercent;
    @Unique
    private int localizator$myDimension;
}
