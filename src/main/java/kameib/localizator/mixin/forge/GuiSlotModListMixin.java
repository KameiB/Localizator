package kameib.localizator.mixin.forge;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.GuiSlotModList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiSlotModList.class)
public abstract class GuiSlotModListMixin {
    @ModifyConstant(
            method = "drawSlot(IIIILnet/minecraft/client/renderer/Tessellator;)V",
            constant = @Constant(stringValue = "DISABLED"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "DISABLED" constant.
    // Line 65: font.drawString(font.trimStringToWidth("DISABLED", this.listWidth - 10), this.left + 3, top + 22, 16720418);
    private static String localizator_FML_GuiSlotModList_drawSlot_disabled(String original) {
        return I18n.format("fml.mod.status.disabled");
    }
    
    
    @ModifyConstant(
            method = "drawSlot(IIIILnet/minecraft/client/renderer/Tessellator;)V",
            constant = @Constant(stringValue = "Metadata not found"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Localize hardcoded "Metadata not found" constant.
    // Line 69: font.drawString(font.trimStringToWidth(mc.getMetadata() != null ? mc.getMetadata().getChildModCountString() : "Metadata not found", this.listWidth - 10), this.left + 3, top + 22, 13421772);
    private static String localizator_FML_GuiSlotModList_drawSlot_metadataNotFound(String original) {
        return I18n.format("fml.mod.metadata.notfound");
    }
    
}
