package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemHastyPickaxe;

@Mixin(ItemHastyPickaxe.class)
public abstract class ItemHastyPickaxeMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "More advanced than gold."),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "More advanced than gold." with a translated lang key.
    // Line 40: tooltip.add("More advanced than gold.");
    private static String localizator_ForgottenItems_ItemHastyPickaxe_addInformation(String original) {
        return I18n.format("item.hasty_pickaxe.desc");
    }
}
