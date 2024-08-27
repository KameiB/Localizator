package kameib.localizator.mixin.morpheus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.quetzi.morpheus.Morpheus;
import net.quetzi.morpheus.helpers.DateHandler;
import net.quetzi.morpheus.helpers.SleepChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SleepChecker.class)
public abstract class SleepCheckerMixin {
    @ModifyVariable(
            method = "updatePlayerStates(Lnet/minecraft/world/World;)V",
            name = "player",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            remap = false
    )
    // Catch current EntityPlayer
    // Line 32: String username = player.getGameProfile().getName();
    private EntityPlayer Morpheus_SleepChecker_updatePlayerStates_catchPlayer(EntityPlayer value) {
        localizator$myPlayer = value;
        return value;
    }
    
    @Redirect(
            method = "updatePlayerStates(Lnet/minecraft/world/World;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/quetzi/morpheus/helpers/SleepChecker;alertPlayers(Lnet/minecraft/util/text/TextComponentString;Lnet/minecraft/world/World;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Use locAlertPlayers instead
    // Line 35: this.alertPlayers(this.createAlert(player.dimension, player.getDisplayNameString(), Morpheus.onSleepText), world);
    private void Morpheus_SleepChecker_updatePlayerStates_alertPlayers_redirect0(SleepChecker instance, TextComponentString iTextComponents, World world) {
        localizator$locAlertPlayers(localizator$createLocAlert(localizator$myPlayer.dimension, localizator$myPlayer.getDisplayNameString(), "notif.morpheus.OnSleepText", iTextComponents), world);
    }

    @Redirect(
            method = "updatePlayerStates(Lnet/minecraft/world/World;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/quetzi/morpheus/helpers/SleepChecker;alertPlayers(Lnet/minecraft/util/text/TextComponentString;Lnet/minecraft/world/World;)V",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Use locAlertPlayers instead
    // Line 39: this.alertPlayers(this.createAlert(player.dimension, player.getDisplayNameString(), Morpheus.onWakeText), world);
    private void Morpheus_SleepChecker_updatePlayerStates_alertPlayers_redirect1(SleepChecker instance, TextComponentString iTextComponents, World world) {
        localizator$locAlertPlayers(localizator$createLocAlert(localizator$myPlayer.dimension, localizator$myPlayer.getDisplayNameString(), "notif.morpheus.OnWakeText", iTextComponents), world);
    }
    
    @Redirect(
            method = "advanceToMorning(Lnet/minecraft/world/World;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/quetzi/morpheus/helpers/SleepChecker;alertPlayers(Lnet/minecraft/util/text/TextComponentString;Lnet/minecraft/world/World;)V",
                    remap = false
            ),
            remap = false
    )
    // Use locAlertPlayers instead
    // Line 84: this.alertPlayers(new TextComponentString(DateHandler.getMorningText()), world);
    private void Morpheus_SleepChecker_advanceToMorning_alertPlayers_redirect(SleepChecker instance, TextComponentString iTextComponents, World world) {
        localizator$locAlertPlayers(new TextComponentTranslation(DateHandler.getMorningText()), world);
    }
    
    // Utility methods
    @Unique
    // Replace createAlert with a language-aware version
    private ITextComponent localizator$createLocAlert(int dimension, String username, String langKey, TextComponentString logMessage) {
        String toLog = logMessage.getUnformattedComponentText();
        Morpheus.mLog.info(toLog);
        
        return new TextComponentTranslation(langKey, username, Morpheus.playerSleepStatus.get(dimension).toString());
    }
    
    @Unique
    // Replace alertPlayers with a language-aware version
    private void localizator$locAlertPlayers(ITextComponent alert, World world) {
        if ((alert != null) && (Morpheus.isAlertEnabled()))
        {
            for (EntityPlayer player : world.playerEntities)
            {
                player.sendMessage(alert);
            }
        }
    }
    
    /**
     * @author KameiB
     * @reason Stop logging here, because it will be logged on createLocAlert
     */
    @Overwrite(remap = false)
    private TextComponentString createAlert(int dimension, String username, String text) {
        //Morpheus.mLog.info(String.format("%s %s %s", username, text, ((WorldSleepState)Morpheus.playerSleepStatus.get(dimension)).toString()));
        //return new TextComponentString(String.format("%s%s%s %s %s", TextFormatting.WHITE, username, TextFormatting.GOLD, text, ((WorldSleepState)Morpheus.playerSleepStatus.get(dimension)).toString()));
        return new TextComponentString( "[Test] " + username + " " + text + " " + Morpheus.playerSleepStatus.get(dimension).toString());
    }
    
    @Unique
    private EntityPlayer localizator$myPlayer;
}
