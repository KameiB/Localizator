package kameib.localizator.mixin.mca;

import kameib.localizator.data.Production;
import mca.command.CommandMCA;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CommandMCA.class)
public abstract class CommandMCAMixin extends CommandBase {
    @ModifyArg(
        method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
        at = @At(
                value = "INVOKE",
                target = "Lmca/command/CommandMCA;sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)V",
                ordinal = 0,
                remap = false
        ),
        index = 1,
        remap = false
    )
    // Replace plain text with lang key
    // Line 34: this.sendMessage(commandSender, "MCA commands have been disabled by the server administrator.");
    private String MCA_CommandMCA_execute_sendMessage0(String message) {
        return "command.mca.disabled";
    }
    
    @ModifyArg(
            method = "execute(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace fixed string with a TextComponentTranslation
    // Lines 51, 59, 76: player.sendMessage(new TextComponentString("Player not found on the server."));
    private ITextComponent MCA_CommandMCA_execute_playerSendMessage_textComponentTranslation(ITextComponent par1) {
        return new TextComponentTranslation("command.mca.playerNotFound");
    }

    /**
     * @author KameiB
     * @reason Replace
     */
    @Overwrite(remap = false)
    private void sendMessage(ICommandSender commandSender, String message) {
        commandSender.sendMessage(new TextComponentString("§6[MCA] §r").appendSibling(new TextComponentTranslation(message)));
    }

    @ModifyArg(
            method = "sendMessage(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace TextComponentString with TextComponentTranslation
    // Line 101: commandSender.sendMessage(new TextComponentString(message));
    private ITextComponent MCA_CommandMCA_sendMessage2_localize(ITextComponent component) {
        return new TextComponentTranslation(component.getUnformattedComponentText());
    }

    /**
     * @author KameiB
     * @reason Replace strings with lang keys
     */
    @Overwrite(remap = false)
    private void displayHelp(ICommandSender commandSender) {
        this.sendMessage(commandSender, "command.mca.help.1", true);
        this.sendMessage(commandSender, "command.mca.help.2", true);
        this.sendMessage(commandSender, "command.mca.help.3", true);
        this.sendMessage(commandSender, "command.mca.help.4", true);
        this.sendMessage(commandSender, "command.mca.help.5", true);
        this.sendMessage(commandSender, "command.mca.help.6", true);
        this.sendMessage(commandSender, "command.mca.help.7", true);
        this.sendMessage(commandSender, "command.mca.help.8", true);
        this.sendMessage(commandSender, "command.mca.help.9", true);
    }

    @Shadow(remap = false)
    private void sendMessage(ICommandSender commandSender, String message, boolean noPrefix) {}
}
