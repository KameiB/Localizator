package kameib.localizator.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWoodenBarrel;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TileEntityWoodenBarrel.class)
public abstract class TileEntityWoodenBarrelMixin {
    @ModifyConstant(
            method = "getOverlayText(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;Z)[Ljava/lang/String;",
            constant = @Constant(stringValue = "mB"),
            remap = false
    )
    // Make "mB" text translatable (for whatever reason)
    // Line 84: s = this.tank.getFluid().getLocalizedName() + ": " + this.tank.getFluidAmount() + "mB";
    @SideOnly(Side.CLIENT)
    private String ImmersiveEngineering_TileEntityWoodenBarrel_getOverlayText_mB(String str) {
        return I18n.format("desc.immersiveengineering.info.mB");
    }
}
