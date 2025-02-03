package kameib.localizator.mixin.mca;

import kameib.localizator.data.Production;
import mca.client.render.RenderVillagerMCA;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@SideOnly(Side.CLIENT)
@Mixin(RenderVillagerMCA.class)
public abstract class RenderVillagerMCAMixin {
    @ModifyArg(
            method = "renderName(Lmca/entity/EntityVillagerMCA;DDD)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmca/client/render/RenderVillagerMCA;renderEntityName(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;D)V",
                    remap = Production.inProduction
            ),
            index = 4,
            remap = false
    )
    // Translate current activity
    // Line 57: this.renderEntityName(entity, x, y - (double)0.25F + (double)modY, z, "(" + entity.getCurrentActivity() + ")", d0);
    private String MCA_RenderVillagerMCA_renderName_renderEntityName_localize(String par5) {
        String langKey = par5.replace("(", "").replace(")", "");
        return "(" + I18n.format(langKey) + ")";
    }
}
