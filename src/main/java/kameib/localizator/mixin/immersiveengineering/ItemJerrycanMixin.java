package kameib.localizator.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.items.ItemJerrycan;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemJerrycan.class)
public abstract class ItemJerrycanMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "mB"),
            remap = Production.inProduction
    )
    // Make "mB" text translatable (for whatever reason)
    // Line 44: list.add(rarity + fs.getLocalizedName() + TextFormatting.GRAY + ": " + fs.amount + "/" + 10000 + "mB");
    @SideOnly(Side.CLIENT)
    private String ImmersiveEngineering_ItemJerrycan_addInformation_mB(String str) {
        return I18n.format("desc.immersiveengineering.info.mB");
    }
}
