package localizator.mixin.battletowers;

import atomicstryker.battletowers.common.AS_BattleTowersCore;
import atomicstryker.battletowers.common.AS_TowerDestroyer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(AS_BattleTowersCore.class)
public abstract class AS_BattleTowersCoreMixin {    
    @Shadow(remap = false)
    private Set<AS_TowerDestroyer> towerDestroyers;
    
    /**
     * @author KameiB
     * @reason Send a lang key instead of a hardcoded text
     */
    @Overwrite(remap = false)
    // Line 173
    public static synchronized void onBattleTowerDestroyed(AS_TowerDestroyer td) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayers(new SPacketChat(new TextComponentTranslation("notif.battletowers.golem_defeated")));
        AS_BattleTowersCore.getTowerDestroyers().add(td);
    }
}
