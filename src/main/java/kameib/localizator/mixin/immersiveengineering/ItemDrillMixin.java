package kameib.localizator.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.items.ItemDrill;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemDrill.class)
public abstract class ItemDrillMixin {
    @ModifyConstant(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            constant = @Constant(stringValue = "mB"),
            remap = Production.inProduction
    )
    // Make "mB" text translatable (for whatever reason)
    // Line 121: list.add(I18n.format("desc.immersiveengineering.flavour.drill.fuel", new Object[0]) + " " + fs.amount + "/" + this.getCapacity(stack, 2000) + "mB");
    @SideOnly(Side.CLIENT)
    private String ImmersiveEngineering_ItemDrill_addInformation_mB(String str) {
        return I18n.format("desc.immersiveengineering.info.mB");
    }
}