package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.spawner.Spawner;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Spawner.class)
public abstract class SpawnerMixin {
    @Redirect(
            method = "trigger(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/spawner/trigger/SpawnTrigger;Lnet/minecraft/util/math/BlockPos;III)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/lycanitesmobs/client/localisation/LanguageManager;translate(Ljava/lang/String;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    // Don't translate de lang key here, will do it on command send
    // Line 327: String message = LanguageManager.translate((String)this.triggerCountMessages.get(currentCount));
    private String Lycanites_Spawner_trigger_getCurrentCount(String key) {
        return key;
    }
    
    @ModifyArg(
            method = "trigger(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lcom/lycanitesmobs/core/spawner/trigger/SpawnTrigger;Lnet/minecraft/util/math/BlockPos;III)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Send a TextComponentTranslation instead of a TextComponentString
    // Line 328: player.sendStatusMessage(new TextComponentString(message), true);
    private ITextComponent Lycanites_Spawner_trigger_SendTranslatableStatusMessage(ITextComponent message) {
        return new TextComponentTranslation(message.getUnformattedComponentText());
    }
    
}
