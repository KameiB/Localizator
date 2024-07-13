package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.network.SRPCommandEvolution;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SRPCommandEvolution.class)
public abstract class SRPCommandEvolutionMixin {
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
    // Lines 50, 55, 65, 73, 91, 99, 112, 122, 128, 133, 140,
    // 145, 151, 160, 167, 172, 182, 190, 195, 209, 213
    private ITextComponent SRParasites_SRPCommandEvolution_execute_message(ITextComponent message) {
        String strMessage = message.getUnformattedComponentText();
        
        if (strMessage.contentEquals("Evolution phases is not active")) {
            return new TextComponentTranslation("message.srparasites.phasesnotactive");
        }
        
        if (strMessage.contentEquals("Invalid argument")) {
            return new TextComponentTranslation("message.srparasites.invalidargument");
        }
        
        if (strMessage.startsWith("Current srpevolutionloss is now ")) {
            return new TextComponentTranslation("message.srparasites.toggle_evolutionloss", strMessage.replace("Current srpevolutionloss is now ", ""));
        }
        
        if (strMessage.startsWith("Current srpevolutiongaining is now ")) {
            return new TextComponentTranslation("message.srparasites.toggle_evolutiongaining", strMessage.replace("Current srpevolutiongaining is now ", ""));
        }
        
        if (strMessage.startsWith(" ======> \n -> Current Dimension: ")) {
            String strDimension, strEvolutionPhase, strTotalKills, strNeededPoints, strProgress, strCooldown, strCanGain, strCanLoss, strOption, strWorldMobCap;
            strMessage = strMessage.replace(" ======> \n -> Current Dimension: ", ""); // OK
            strDimension = strMessage.substring(0,strMessage.indexOf(" \n")); // 11 (?)
            strMessage = strMessage.replace(strDimension + " \n -> Current Evolution Phase: ", ""); // OK
            strEvolutionPhase = strMessage.substring(0,strMessage.indexOf(" \n")); // OK
            strMessage = strMessage.replace(strEvolutionPhase + " \n -> Total points: ", ""); // OK
            strTotalKills = strMessage.substring(0,strMessage.indexOf(" \n")); // OK? 
            strMessage = strMessage.replace(strTotalKills + " \n -> Points required for the next phase: ", ""); // --
            strNeededPoints = strMessage.substring(0,strMessage.indexOf(" \n"));
            strMessage = strMessage.replace(strNeededPoints + " \n -> Progress: ", ""); // ---
            strProgress = strMessage.substring(0,strMessage.indexOf("%"));
            strMessage = strMessage.replace(strProgress + "%  \n -> Phase cooldown: ", ""); // OK
            strCooldown = strMessage.substring(0,strMessage.indexOf(" second(s)"));
            strMessage = strMessage.replace(strCooldown + " second(s) remaining \n -> srpevolutiongaining: ", "");
            strCanGain = strMessage.substring(0,strMessage.indexOf(" (can gain"));
            strMessage = strMessage.replace(strCanGain + " (can gain points) \n -> srpevolutionloss: ", "");
            strCanLoss = strMessage.substring(0,strMessage.indexOf(" (cannot lose"));
            strMessage = strMessage.replace(strCanLoss + " (cannot lose points) \n -> Current Parasite Mob Cap: ", "");
            strWorldMobCap = strMessage.substring(0,strMessage.indexOf(" \n"));
            strMessage = strMessage.replace(strWorldMobCap + " \n -> Number of current parasites: ", "");
            strOption = strMessage;
            
            return new TextComponentTranslation("message.srparasites.getphase",
                    strDimension, strEvolutionPhase, strTotalKills, strNeededPoints, strProgress, strCooldown, strCanGain, strCanLoss, strWorldMobCap, strOption);
        }
        
        if (strMessage.contentEquals("The list has been reset")) {
            return new TextComponentTranslation("message.srparasites.evolutionlock_reset");
        }
        
        if (strMessage.startsWith("Current list: ")) {
            return new TextComponentTranslation("message.srparasites.evolutionlock_getlist",
                    strMessage.replace("Current list: ", ""));
        }
        
        if (strMessage.contentEquals("Invalid arg")) {
            return new TextComponentTranslation("message.srparasites.invalidarg");
        }
        
        if (strMessage.contentEquals("Current phase -2, cannot add points")) {
            return new TextComponentTranslation("message.srparasites.addpoints_2");
        }
        
        if (strMessage.contentEquals("Current dimension cannot gain points")) {
            return new TextComponentTranslation("message.srparasites.addpoints_cannotgain");
        }

        if (strMessage.contentEquals("Current dimension cannot lose points")) {
            return new TextComponentTranslation("message.srparasites.addpoints_cannotlose");
        }

        if (strMessage.contentEquals("Current phase is in cooldown, cannot add/remove points")) {
            return new TextComponentTranslation("message.srparasites.addpoints_cooldown");
        }

        if (strMessage.startsWith("Added ")) {
            String strPoints = strMessage.replace("Added ", "");
            strPoints = strPoints.substring(0, strPoints.indexOf(" point"));
            int nPoints;
            
            try {
                nPoints = Integer.parseInt(strPoints);
                return new TextComponentTranslation((nPoints > 1 ? "message.srparasites.addedpoints" : "message.srparasites.addedpoint"), strPoints);
            } catch (NumberFormatException e) {
                return new TextComponentTranslation("message.srparasites.addedpoints", strPoints);
            }
        }
        
        if (strMessage.contentEquals("Cooldown value must be positive or 0")) {
            return new TextComponentTranslation("message.srparasites.setcooldown_negative");
        }
        
        if (strMessage.startsWith("The cooldown was set to ")) {
            String strCooldown = strMessage.replace("The cooldown was set to ", "");
            strCooldown = strCooldown.substring(0, strCooldown.indexOf(" second(s)"));
            
            return new TextComponentTranslation("message.srparasites.setcooldown", strCooldown);
        }

        if (strMessage.contentEquals("Invalid argument: phase too high")) {
            return new TextComponentTranslation("message.srparasites.setphase_high");
        }

        if (strMessage.contentEquals("Invalid argument: phase too low")) {
            return new TextComponentTranslation("message.srparasites.setphase_low");
        }
        
        if (strMessage.startsWith("Changed Evolution Phase for Parasites to ")) {
            return new TextComponentTranslation("message.srparasites.setphase_success", 
                    strMessage.replace("Changed Evolution Phase for Parasites to ", ""));
        }

        if (strMessage.contentEquals("Invalid command")) {
            return new TextComponentTranslation("message.srparasites.invalidcommand");
        }
        
        return message;
    }
}
