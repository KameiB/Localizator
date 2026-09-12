package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemBarkifiedAxe;

@Mixin(ItemBarkifiedAxe.class)
public abstract class ItemBarkifiedAxeMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Automatically barkifies logs when breaking them"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Localize hardcoded description
    // Line 39: tooltip.add("Automatically barkifies logs when breaking them");
    private String localizator_ForgottenItems_ItemBarkifiedAxe_addInformation(String original) {
        return I18n.format("item.barkified_axe.desc");
    }
}
