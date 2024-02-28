package kameib.localizator.mixin.battletowers;

import atomicstryker.battletowers.common.AS_BattleTowersCore;
import kameib.localizator.data.Production;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AS_BattleTowersCore.class)
public abstract class AS_BattleTowersCoreMixin {    
    @ModifyArg(
            method = "onBattleTowerDestroyed(Latomicstryker/battletowers/common/AS_TowerDestroyer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/management/PlayerList;sendPacketToAllPlayers(Lnet/minecraft/network/Packet;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Send a lang key instead of a hardcoded text
    // Line 174: FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayers(new SPacketChat(new TextComponentTranslation("A Battletower's Guardian has fallen! Without it's power, the Tower will collapse...", new Object[0])));
    private static net.minecraft.network.Packet<?> BattleTowers_AS_BattleTowers_onBattleTowerDestroyed_sendTextComponentTranslation(Packet<?> packetIn) {
        return new SPacketChat(new TextComponentTranslation("notif.battletowers.golem_defeated"));
    }
}
