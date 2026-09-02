package kameib.localizator.mixin.charm;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import svenhjol.charm.base.integration.jei.ComposterCategory;

@Mixin(ComposterCategory.class)
public abstract class ComposterCategoryMixin {
    @ModifyConstant(
            method = "getTitle()Ljava/lang/String;",
            constant = @Constant(stringValue = "Composter"),
            remap = false
    )
    // Replace hardcoded "Composter" with a translated lang key
    // Line 32: return "Composter";
    @SideOnly(Side.CLIENT)
    private String localizator_Charm_ComposterCategory_getTitle(String original) {
        return I18n.format("charm.jei.recipe.composter");
    }
}
