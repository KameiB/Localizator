package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.network.SRPCommandRoot;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SRPCommandRoot.class)
public abstract class SRPCommandRootMixin {
    @ModifyArg(
            method = "func_184881_a(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/ICommandSender;func_145747_a(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = false
            ),
            remap = false
    )
    // Replace TextComponentString with TextComponentTranslation in all sendMessage calls
    // Lines 66, 71, 81, 88, 95, 102, 113, 120
    private ITextComponent SRParasites_SRPCommandRoot_execute_sendMessage(ITextComponent message) {
        String strMessage = message.getUnformattedComponentText();
        
        if (strMessage.contentEquals("Configutarion files were read successfully \n NOTE: Does not work for all options, such as registry or client-side options")) {
            return new TextComponentTranslation("message.srparasites.readconfigurationfile_success");
        }
        
        if (strMessage.contentEquals("There was a problem while reading configuration file, check inputs \n NOTE: Does not work for all options, such as registry or client-side options")) {
            return new TextComponentTranslation("message.srparasites.readconfigurationfile_error");
        }
        
        if (strMessage.startsWith("Current doTileDrop value is ")) {
            return new TextComponentTranslation("message.srparasites.toggle_dotiledrops",
                    strMessage.replace("Current doTileDrop value is ", ""));
        }

        if (strMessage.startsWith("Current doMobEvolution value is ")) {
            return new TextComponentTranslation("message.srparasites.toggle_domobevolution",
                    strMessage.replace("Current doMobEvolution value is ", ""));
        }

        if (strMessage.startsWith("Current number of Beckons in this dimension spawned by RS: ")) {
            return new TextComponentTranslation("message.srparasites.getbeckonlimit",
                    strMessage.replace("Current number of Beckons in this dimension spawned by RS: ", ""));
        }
        
        if (strMessage.contentEquals("Data file of this dimension has been reset")) {
            return new TextComponentTranslation("message.srparasites.resetdatafile");
        }

        if (strMessage.contentEquals("Invalid arg")) {
            return new TextComponentTranslation("message.srparasites.invalidarg");
        }

        if (strMessage.startsWith("Current number of Beckons in this dimension spawned by RS was set to ")) {
            return new TextComponentTranslation("message.srparasites.setbeckonlimit",
                    strMessage.replace("Current number of Beckons in this dimension spawned by RS was set to ", ""));
        }
        
        return message;
    }
}
