package kameib.localizator.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.items.ItemChemthrower;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemChemthrower.class)
public abstract class ItemChemthrowerMixin {
    @ModifyConstant(
            method = "formatFluidStack(Lnet/minecraftforge/fluids/FluidStack;I)Ljava/lang/String;",
            constant = @Constant(stringValue = "mB"),
            remap = false
    )
    // Make "mB" text translatable (for whatever reason)
    // Line 69: return rarity + fs.getLocalizedName() + TextFormatting.GRAY + ": " + fs.amount + "/" + capacity + "mB";
    @SideOnly(Side.CLIENT)
    private String ImmersiveEngineering_ItemChemthrower_formatFluidStack_mB(String str) {
        return I18n.format("desc.immersiveengineering.info.mB");
    }
}
