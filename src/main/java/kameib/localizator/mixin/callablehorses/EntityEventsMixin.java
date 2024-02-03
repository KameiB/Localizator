package kameib.localizator.mixin.callablehorses;

import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tschipp.callablehorses.common.events.EntityEvents;

@Mixin(EntityEvents.class)
public abstract class EntityEventsMixin {    
    @ModifyArg(
            method = "onLivingDeath(Lnet/minecraftforge/event/entity/living/LivingDeathEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 187: owner.sendMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.alert.death")));
    private static ITextComponent CallableHorses_EntityEvents_onLivingDeath_alertDeath(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.alert.death").setStyle(new Style().setColor(TextFormatting.RED));
    }
    @ModifyArg(
            method = "onJoinWorld(Lnet/minecraftforge/event/entity/EntityJoinWorldEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    // Translate message on client side
    // Line 217: player.sendMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.alert.offlinedeath")));
    private static ITextComponent CallableHorses_EntityEvents_onJoinWorld_alertOfflineDeath(ITextComponent message) {
        return new TextComponentTranslation("callablehorses.alert.offlinedeath").setStyle(new Style().setColor(TextFormatting.RED));
    }
}
