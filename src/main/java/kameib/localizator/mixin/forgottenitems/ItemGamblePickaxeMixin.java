package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemGamblePickaxe;

@Mixin(ItemGamblePickaxe.class)
public abstract class ItemGamblePickaxeMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "When breaking a block, there's a chance to either duplicate it or destroy it"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "When breaking a block, there's a chance to either duplicate it or destroy it" with a translated lang key.
    // Line 40: tooltip.add("When breaking a block, there's a chance to either duplicate it or destroy it");
    private static String localizator_ForgottenItems_ItemGamblePickaxe_addInformation(String original) {
        return I18n.format("item.gamble_pickaxe.desc");
    }
}
