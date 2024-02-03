package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.GameEventListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameEventListener.class)
public abstract class GameEventListenerMixin {
    @ModifyArg(
            method = "onBlockBreak(Lnet/minecraftforge/event/world/BlockEvent$BreakEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    remap = true
            ),
            index = 0,
            remap = false
    )
    // Translate Status Message on client side
    // Line 519: event.getPlayer().sendStatusMessage(new TextComponentString(LanguageManager.translate("boss.block.protection.break")), true);
    private ITextComponent lycanites_GameEventListener_onBlockBreak_protection(ITextComponent message) {
        return new TextComponentTranslation("boss.block.protection.break");
    }
    @ModifyArg(
            method = "onBlockPlace(Lnet/minecraftforge/event/world/BlockEvent$PlaceEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    remap = true
            ),
            index = 0,
            remap = false
    )
    // Translate Status Message on client side
    // Line 549: event.getPlayer().sendStatusMessage(new TextComponentString(LanguageManager.translate("boss.block.protection.place")), true);
    private ITextComponent lycanites_GameEventListener_onBlockPlace_protection(ITextComponent message) {
        return new TextComponentTranslation("boss.block.protection.place");
    }
}
