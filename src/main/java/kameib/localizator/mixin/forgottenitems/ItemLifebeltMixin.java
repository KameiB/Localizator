package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemLifebelt;

@Mixin(ItemLifebelt.class)
public abstract class ItemLifebeltMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Float in water"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Float in water" with a translated lang key.
    // Line 54: tooltip.add("Float in water");
    private String localizator_ForgottenItems_ItemLifebelt_addInformation(String original) {
        return I18n.format("item.lifebelt.desc");
    }
}
