package kameib.localizator.mixin.charm;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import svenhjol.meson.MesonGuiContainer;

@SideOnly(Side.CLIENT)
@Mixin(MesonGuiContainer.class)
public abstract class MesonGuiContainerMixin {
    @ModifyArg(
            method = "drawGuiContainerForegroundLayer(II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I",
                    ordinal = 0,
                    remap = Production.inProduction
            ),
            index = 0,
            remap = Production.inProduction
    )
    // Apply I18n.format to displayName
    // Line 47: this.fontRenderer.drawString(this.displayName, 8, 6, 4210752);
    private String Charm_MesonGuiContainer_drawGuiContainerForegroundLayer_translateDisplayName(String name) {
        return I18n.hasKey(name) ? I18n.format(name) : name;
    }
}
