package kameib.localizator.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.util.SRPReference;
import kameib.localizator.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(SRPReference.class)
public abstract class SRPReferenceMixin {
    @ModifyArg(
            method = "setSimPlayerName(Lcom/dhanantry/scapeandrunparasites/entity/ai/misc/EntityParasiteBase;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/dhanantry/scapeandrunparasites/entity/ai/misc/EntityParasiteBase;func_96094_a(Ljava/lang/String;)V",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Add extra custom names for Adventurers
    // Line 147: in.func_96094_a(mans[rand.nextInt(mans.length)]);
    private static String SRParasites_SRPReference_setSimPlayerName_setCustomNameTag(String name) {
        String finalName = name;

        if(ForgeConfigHandler.serverConfig.srparasitesCustomNamesList != null && ForgeConfigHandler.serverConfig.srparasitesCustomNamesList.length > 0) {
            Random rand = new Random();
            if (rand.nextInt(18) == 0) {
                finalName = ForgeConfigHandler.serverConfig.srparasitesCustomNamesList[rand.nextInt(ForgeConfigHandler.serverConfig.srparasitesCustomNamesList.length)];
            }
        }
        return finalName;
    }
}
