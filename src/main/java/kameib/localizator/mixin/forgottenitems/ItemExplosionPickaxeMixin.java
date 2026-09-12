package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tschipp.forgottenitems.items.ItemExplosionPickaxe;

@Mixin(ItemExplosionPickaxe.class)
public abstract class ItemExplosionPickaxeMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "Creates an Explosion when breaking blocks"),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace the hardcoded "Creates an Explosion when breaking blocks" with a translated lang key.
    // Line 62: tooltip.add("Creates an Explosion when breaking blocks");
    private static String localizator_ItemExplosionPickaxe_addInformation(String original) {
        return I18n.format("item.explosion_pickaxe.desc");
    }
}
