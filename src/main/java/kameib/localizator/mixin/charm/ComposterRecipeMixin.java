package kameib.localizator.mixin.charm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import svenhjol.charm.base.integration.jei.ComposterRecipe;
import svenhjol.charm.crafting.feature.Composter;

@Mixin(ComposterRecipe.class)
public abstract class ComposterRecipeMixin {
    @SideOnly(Side.CLIENT)
    @Inject(
            method = "drawInfo(Lnet/minecraft/client/Minecraft;IIII)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Composter recipe GUI texts were hardcoded
    // Line 36
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY, CallbackInfo ci) {
        int max = Composter.maxOutput;
        String numItems = max > 1 ? I18n.format("charm.jei.recipe.composter.many_items", max) : I18n.format("charm.jei.recipe.composter.1_item");        
        this.drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + I18n.format("charm.jei.recipe.composter.compost_chance") + " " + Math.round(this.chance * 100.0F) + "%", 81, 1);
        this.drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + I18n.format("charm.jei.recipe.composter.outputs", numItems), 81, 87);
        ci.cancel();
    }

    @Shadow(remap = false)
    private float chance;
    @Shadow(remap = false)
    @SideOnly(Side.CLIENT)
    private void drawStringCentered(FontRenderer fontRenderer, String text, int x, int y) {}
}
