package kameib.localizator.mixin.minecraft;

import com.google.common.base.Strings;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin 
        extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<Biome> {
    @Final @Mutable
    @Shadow(remap = Production.inProduction)
    private String biomeName;
    
    @Inject(
            method = "getBiomeName()Ljava/lang/String;",
            at = @At("RETURN"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Return a localized version of the Biome Name
    // Line 381: return this.biomeName;
    private void Minecraft_Biome_getBiomeName(CallbackInfoReturnable<String> cir) {
        ResourceLocation rl = getRegistryName();
        if (rl == null) {
            cir.setReturnValue(biomeName);
        }
        else {
            String biomeRes = rl.toString();
            if (Strings.isNullOrEmpty(biomeRes)) {
                cir.setReturnValue(biomeName);                
            }
            else {
                biomeRes = "biome." + biomeRes + ".name";
                try {
                    cir.setReturnValue(I18n.hasKey(biomeRes) ?
                            I18n.format(biomeRes) : biomeName);
                } catch(Exception e) {
                    // Looks like Shaders don't like translated biome names ¬¬ 
                    cir.setReturnValue(biomeName);
                }
            }
        }
    }    
}
