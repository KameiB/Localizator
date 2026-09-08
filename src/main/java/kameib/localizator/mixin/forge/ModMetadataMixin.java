package kameib.localizator.mixin.forge;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModMetadata.class)
public abstract class ModMetadataMixin {
    @Redirect(
            method = "getChildModCountString()Ljava/lang/String;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    remap = false
            ),
            remap = false
    )
    @SideOnly(Side.CLIENT)
    // Replace hardcoded string with a lang key that receives parameter
    // Line 41: return String.format("%d child mod%s", this.childMods.size(), this.childMods.size() != 1 ? "s" : "");
    private static String localizator_FML_ModMetadata_getChildModCountString_childModsTextBuilder(String s, Object[] params) {
        int nChildMods = (int) params[0];
        if(nChildMods == 1) {
            return I18n.format("fml.mod.metadata.child", nChildMods);
        }
        else {
            return I18n.format("fml.mod.metadata.childs", nChildMods);
        }
    }
}
