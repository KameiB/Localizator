package localizator.mixin.itemphysic;

import com.creativemd.itemphysic.EventHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @Shadow public abstract void renderTick(TickEvent.RenderTickEvent event);

    @ModifyArg(
            method = "Lcom/creativemd/itemphysic/EventHandler;renderTickFull()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V"),
            index = 0,
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // String text = "Power: " + renderPower;
    // Line 306: mc.player.sendStatusMessage(new TextComponentString(text), true);
    private static ITextComponent localizator_ItemPhysic_EventHandler_PowerStatusMessage(ITextComponent textComponent) {
        String renderPower = textComponent.getUnformattedText().substring(textComponent.getUnformattedText().indexOf("Power: ") + 6);
        return new TextComponentTranslation("notif.itemphysic.throw_power");
        //).appendText(renderPower);
    }
}
