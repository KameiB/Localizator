package localizator.mixin.charm;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import svenhjol.charm.base.integration.jei.ComposterCategory;

@Mixin(ComposterCategory.class)
public abstract class ComposterCategoryMixin {
    /**
     * @author KameiB
     * @reason Composter JEI recipe GUI title is hardcoded
     * Note: I tried just changing the return, but Mixin said getTitle is not cancellable :c
     */
    @Overwrite(remap = false)
    @SideOnly(Side.CLIENT)
    public String getTitle() {
        return I18n.format("charm.jei.recipe.composter");
    }
}
