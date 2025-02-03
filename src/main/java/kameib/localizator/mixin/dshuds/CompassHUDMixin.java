package kameib.localizator.mixin.dshuds;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.orecruncher.dshuds.Environment;
import org.orecruncher.dshuds.hud.CompassHUD;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;

@Mixin(CompassHUD.class)
@SideOnly(Side.CLIENT)
public abstract class CompassHUDMixin {
    /**
     * @author KameiB
     * @reason Get the biome name and treat it as a lang key to translate it.
     * I really don't like this approach, but it'll work with virtual biomes...
     */
    @Overwrite(remap = false)
    @Nonnull
    protected String getBiomeName() {
        return TextFormatting.GOLD + I18n.format(Environment.getBiomeName());
    }
}
