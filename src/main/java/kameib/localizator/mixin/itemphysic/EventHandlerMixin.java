package kameib.localizator.mixin.itemphysic;

import com.creativemd.itemphysic.EventHandler;
import kameib.localizator.data.Production;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {
    @ModifyArg(
            method = "renderTickFull()V",
            at = @At(
                    value = "INVOKE", 
                    target = "Lnet/minecraft/client/entity/EntityPlayerSP;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V",
                    remap = Production.inProduction
            ),
            index = 0,
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // String text = "Power: " + renderPower;
    // Line 306: mc.player.sendStatusMessage(new TextComponentString(text), true);
    private ITextComponent ItemPhysic_EventHandler_PowerStatusMessage(ITextComponent textComponent) {
        String power = textComponent.getUnformattedText().substring(textComponent.getUnformattedText().indexOf("Power: ") + 6);
        return new TextComponentTranslation("notif.itemphysic.throw_power", power);        
    }
}
