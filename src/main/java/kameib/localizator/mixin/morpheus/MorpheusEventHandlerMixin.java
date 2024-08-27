package kameib.localizator.mixin.morpheus;

import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.quetzi.morpheus.helpers.MorpheusEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MorpheusEventHandler.class)
public abstract class MorpheusEventHandlerMixin {
    @ModifyArg(
            method = "bedClicked(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Replace the hardcoded message with a lang key
    // Line 102: player.sendMessage(new TextComponentString("New spawnpoint has been set!"));
    private ITextComponent Morpheus_MorpheusEventHandler_bedClicked_locMessage(ITextComponent par1) {
        return new TextComponentTranslation("notif.morpheus.SpawnSet");
    }
}
