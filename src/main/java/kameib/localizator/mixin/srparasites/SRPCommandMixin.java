package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.network.SRPCommand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SRPCommand.class)
public abstract class SRPCommandMixin {
    @ModifyArg(
            method = "func_184881_a(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;func_145747_a(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = false
            ),
            remap = false
    )
    // Replace hardcoded messages with lang keys
    // Lines 51, 55, 63: sender.func_145747_a
    private ITextComponent SRParasites_SRPCommand_execute_message(ITextComponent message) {
        String strMessage = message.getUnformattedComponentText();
        
        if (strMessage.contains("Invalid argument")) { // Line 51: "Invalid argument"
            return new TextComponentTranslation("message.srparasites.invalidargument");
        }
        
        if (strMessage.contains("Conjuring: [")) { // Line 55: "Conjuring: [" + argString[0] + "]"
            String temp = strMessage.substring(0, strMessage.length()-1);
            return new TextComponentTranslation("message.srparasites.conjuring",
                    temp.replace("Conjuring: [", ""));
        }
        
        if (strMessage.contains("Entity not found")) { // Line 63: "Entity not found"
            return new TextComponentTranslation("message.srparasites.entitynotfound");
        }
        
        return message;
    }
}
