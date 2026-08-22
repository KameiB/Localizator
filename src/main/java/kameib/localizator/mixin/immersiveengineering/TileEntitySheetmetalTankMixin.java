package kameib.localizator.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntitySheetmetalTank;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TileEntitySheetmetalTank.class)
public abstract class TileEntitySheetmetalTankMixin {
    @ModifyConstant(
            method = "getOverlayText(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;Z)[Ljava/lang/String;",
            constant = @Constant(stringValue = "mB"),
            remap = false
    )
    // Make "mB" text translatable (for whatever reason)
    // Line 51: s = fs.getLocalizedName() + ": " + fs.amount + "mB";
    @SideOnly(Side.CLIENT)
    private String ImmersiveEngineering_TileEntitySheetmetalTank_getOverlayText_mB(String str) {
        return I18n.format("desc.immersiveengineering.info.mB");
    }
}
