package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemCushionedBoots;

@Mixin(ItemCushionedBoots.class)
public abstract class ItemCushionedBootsMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Soften your fall"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Soften your fall" text with a lang key.
    // Line 55: tooltip.add("Soften your fall");
    private String localizator_ItemCushionedBoots_addInformation_desc(String original) {
        return I18n.format("item.cushioned_boots.desc");
    }
}
