package kameib.localizator.mixin.spartanweaponry;

import com.oblivioussp.spartanweaponry.item.ItemThrowingWeapon;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemThrowingWeapon.class)
public abstract class ItemThrowingWeaponMixin {
    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 7,
                    remap = false
            ),
            index = 0,
            remap = Production.inProduction
    )
    // Localize hardcoded "Material Bonus:" tooltip text
    // Line 532: tooltip.add(TextFormatting.DARK_AQUA + "Material Bonus:");
    @SideOnly(Side.CLIENT)
    private Object SpartanWeaponry_ItemThrowingWeapon_addInformation_materialBonus(Object materialBonus) {
        return TextFormatting.DARK_AQUA + I18n.format("tooltip.spartanweaponry.material_bonus");
    }
}
