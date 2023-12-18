package localizator.mixin.sereneseasons;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rustic.common.items.ItemStakeCropSeed;
import sereneseasons.config.FertilityConfig;
import sereneseasons.init.ModFertility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Mixin(ModFertility.class)
public abstract class ModFertilityMixin {
    @Shadow(remap = false) private static HashMap<String, Integer> seedSeasons = new HashMap<>();

    /**
     * @author KameiB
     * @reason Fertile seasons texts were hardcoded (not localizable)
     */
    @Overwrite(remap = false)
    @SideOnly(Side.CLIENT)
    // Line 116
    public static void setupTooltips(ItemTooltipEvent event) {
        if (FertilityConfig.general_category.crop_tooltips && FertilityConfig.general_category.seasonal_crops) {
            String name = event.getItemStack().getItem().getRegistryName().toString();            
            if (seedSeasons.containsKey(name)) {
                int mask = (Integer)seedSeasons.get(name);
                List<String> tooltip = event.getToolTip();
                tooltip.add(I18n.format("desc.sereneseasons.fertile_seasons"));
                if ((mask & 1) != 0 && (mask & 2) != 0 && (mask & 4) != 0 && (mask & 8) != 0) {
                    tooltip.add(TextFormatting.LIGHT_PURPLE + I18n.format("desc.sereneseasons.year_round"));
                } else {
                    if ((mask & 1) != 0) {
                        tooltip.add(TextFormatting.GREEN + I18n.format("desc.sereneseasons.spring"));
                    }

                    if ((mask & 2) != 0) {
                        tooltip.add(TextFormatting.YELLOW + I18n.format("desc.sereneseasons.summer"));
                    }

                    if ((mask & 4) != 0) {
                        tooltip.add(TextFormatting.GOLD + I18n.format("desc.sereneseasons.autumn"));
                    }

                    if ((mask & 8) != 0) {
                        tooltip.add(TextFormatting.AQUA + I18n.format("desc.sereneseasons.winter"));
                    }
                }
            }
        }
    }

    /** 
     * @author KameiB
     * @reason Rustic's "rustic:chili_pepper_seeds" and "rustic:tomato_seeds" are not instances of IPlantable, but ItemStakeCropSeed.
     * Therefore, I had to add that special case, so those seeds show their fertile seasons on tooltip.
     */
    @Inject(
            method = "initSeasonCrops([Ljava/lang/String;Ljava/util/HashSet;I)V",
            at = @At("TAIL"),
            remap = false
    )
    // Line 112
    private static void localizator_SereneSeasons_ModFertility_initSeasonCrops(String[] seeds, HashSet<String> cropSet, int bitmask, CallbackInfo ci) {
        Item item;
        if (Loader.isModLoaded("rustic")) {
            for (String seed : seeds) {
                item = (Item) ForgeRegistries.ITEMS.getValue(new ResourceLocation(seed));
                if (item instanceof ItemStakeCropSeed) {
                    if (bitmask != 0) {
                        // Their plants are already added to allListedPlants, so no need to add those here
                        if (seedSeasons.containsKey(seed)) {
                            int seasons = (Integer)seedSeasons.get(seed);
                            seedSeasons.put(seed, seasons | bitmask);
                        } else {
                            seedSeasons.put(seed, bitmask);
                        }
                    }
                }
            }
        }        
    }
}
