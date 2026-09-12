package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import tschipp.forgottenitems.items.ItemGem;

@Mixin(ItemGem.class)
public abstract class ItemGemMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "It glows and sparkles"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "It glows and sparkles" with a translated lang key.
    // Line 47: tooltip.add("It glows and sparkles");
    private static String localizator_ForgottenItems_ItemGem_addInformation(String original) {
        return I18n.format("tooltip.forgottenitems.gems.desc");
    }
}
