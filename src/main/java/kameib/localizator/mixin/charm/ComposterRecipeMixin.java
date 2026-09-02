package kameib.localizator.mixin.charm;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import svenhjol.charm.base.integration.jei.ComposterRecipe;

@Mixin(ComposterRecipe.class)
public abstract class ComposterRecipeMixin {
    @ModifyVariable(
            method = "drawInfo(Lnet/minecraft/client/Minecraft;IIII)V",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            remap = false,
            name = "numItems")
    // Modify local variable 'numItems' to use lang keys
    // Line 38: String numItems = max > 1 ? "1-" + max + " items" : "1 item";
    @SideOnly(Side.CLIENT)
    private String localizator_Charm_ComposterRecipe_drawInfo_numItems(String numItems, @Local(name = "max") int max) {
        return max > 1
                ? I18n.format("charm.jei.recipe.composter.many_items", max)
                : I18n.format("charm.jei.recipe.composter.1_item");
    }
    
    @ModifyArg(
            method = "drawInfo(Lnet/minecraft/client/Minecraft;IIII)V",
            at = @At(
                    value = "INVOKE", 
                    target = "Lsvenhjol/charm/base/integration/jei/ComposterRecipe;drawStringCentered(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;II)V", 
                    ordinal = 0, 
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Replace hardcoded text (second argument) with lang key
    // Line 39: this.drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + "Compost chance: " + Math.round(this.chance * 100.0F) + "%", 81, 1);
    private String localizator_Charm_ComposterRecipe_drawInfo_drawStringCentered0_compostChance(String original) {
        return TextFormatting.DARK_GRAY + I18n.format("charm.jei.recipe.composter.compost_chance", Math.round(this.chance * 100.0F));
    }

    @ModifyArg(
            method = "drawInfo(Lnet/minecraft/client/Minecraft;IIII)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lsvenhjol/charm/base/integration/jei/ComposterRecipe;drawStringCentered(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;II)V",
                    ordinal = 1,
                    remap = false
            ),
            index = 1,
            remap = false
    )
    // Modify second argument so it becomes a translatable text
    // Line 40: this.drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + "Outputs " + numItems, 81, 87);
    @SideOnly(Side.CLIENT)
    private String localizator_Charm_ComposterRecipe_drawInfo_drawStringCentered1_outputs(String original, @Local(name = "numItems") String numItems) {
        return TextFormatting.DARK_GRAY + I18n.format("charm.jei.recipe.composter.outputs", numItems);
    }

    @Shadow(remap = false)
    private float chance;
}
