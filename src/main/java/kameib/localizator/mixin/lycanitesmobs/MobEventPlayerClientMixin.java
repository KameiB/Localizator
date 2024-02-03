package kameib.localizator.mixin.lycanitesmobs;

import com.lycanitesmobs.core.mobevent.MobEvent;
import com.lycanitesmobs.core.mobevent.MobEventPlayerClient;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MobEventPlayerClient.class)
public abstract class MobEventPlayerClientMixin {
    @Shadow(remap = false)
    public boolean extended;
    @Shadow(remap = false)
    public MobEvent mobEvent;
    
    @ModifyArg(
            method = "onStart(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate the message at client side
    // Line 47: player.sendMessage(new TextComponentString(eventMessage));
    private ITextComponent localizator_Lycanites_MobEvent_onStart_sendMessage(ITextComponent textComponent) {
        return new TextComponentTranslation("event." + (this.extended ? "extended_" : "started_"),
                new TextComponentTranslation("mobevent." + this.mobEvent.title + ".name"));
    }

    @ModifyArg(
            method = "onFinish(Lnet/minecraft/entity/player/EntityPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V",
                    remap = Production.inProduction
            ),
            remap = false
    )
    // Translate the message at client side
    // Line 65: player.sendMessage(new TextComponentString(eventMessage));
    private ITextComponent localizator_Lycanites_MobEvent_onFinish_sendMessage(ITextComponent textComponent) {
        return new TextComponentTranslation("event.finished_",
                new TextComponentTranslation("mobevent." + this.mobEvent.title + ".name"));
    }
}
