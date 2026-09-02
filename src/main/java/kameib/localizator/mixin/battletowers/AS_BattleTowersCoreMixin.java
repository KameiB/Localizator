package kameib.localizator.mixin.battletowers;

import atomicstryker.battletowers.common.AS_BattleTowersCore;
import kameib.localizator.data.Production;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AS_BattleTowersCore.class)
public abstract class AS_BattleTowersCoreMixin {    
    @ModifyArg(
            method = "onBattleTowerDestroyed(Latomicstryker/battletowers/common/AS_TowerDestroyer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/text/TextComponentTranslation;<init>(Ljava/lang/String;[Ljava/lang/Object;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Send a lang key instead of a hardcoded text as an argument to the already created TextComponentTranslation object
    // Line 174: FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayers(new SPacketChat(new TextComponentTranslation("A Battletower's Guardian has fallen! Without it's power, the Tower will collapse...", new Object[0])));
    private static String localizator_BattleTowers_AS_BattleTowers_onBattleTowerDestroyed_sendTextComponentTranslation(String message) {
        return "notif.battletowers.golem_defeated";
    }
}
