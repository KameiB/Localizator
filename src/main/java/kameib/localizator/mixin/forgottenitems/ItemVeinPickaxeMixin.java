package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemVeinPickaxe;

@Mixin(ItemVeinPickaxe.class)
public abstract class ItemVeinPickaxeMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Vein Mines the block broken"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Vein Mines the block broken" with a translated lang key.
    // Line 126: tooltip.add("Vein Mines the block broken");
    private static String localizator_ForgottenItems_ItemVeinPickaxe_addInformation(String original) {
        return I18n.format("item.vein_pickaxe.desc");
    }
}
