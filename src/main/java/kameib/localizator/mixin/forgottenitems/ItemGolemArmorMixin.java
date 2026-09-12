package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemGolemArmor;

@Mixin(ItemGolemArmor.class)
public abstract class ItemGolemArmorMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Hard like a Rock"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Hard like a Rock" with a translated lang key.
    // Line 62: tooltip.add("Hard like a Rock");
    private static String localizator_ForgottenItems_ItemGolemArmor_addInformation(String original) {
        return I18n.format("tooltip.forgottenitems.golem_armor.desc");
    }
}
