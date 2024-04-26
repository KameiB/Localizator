package kameib.localizator.mixin.sereneseasons;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rustic.common.items.ItemStakeCropSeed;
import sereneseasons.init.ModFertility;

import java.util.HashMap;
import java.util.HashSet;

@Mixin(ModFertility.class)
public abstract class ModFertilityMixin {
    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "setupTooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    // Fertile seasons texts were hardcoded (not localizable)
    // Line 121: event.getToolTip().add("Fertile Seasons:");
    private static Object SereneSeasons_ModFertility_setupTooltips_addFertileSeasons(Object text) {
        return I18n.format("desc.sereneseasons.fertile_seasons");
    }

    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "setupTooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    // Fertile seasons texts were hardcoded (not localizable)
    // Line 123: event.getToolTip().add(TextFormatting.LIGHT_PURPLE + " Year-Round");
    private static Object SereneSeasons_ModFertility_setupTooltips_addYearRound(Object text) {
        return TextFormatting.LIGHT_PURPLE + I18n.format("desc.sereneseasons.year_round");
    }

    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "setupTooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 2,
                    remap = false
            ),
            remap = false
    )
    // Fertile seasons texts were hardcoded (not localizable)
    // Line 126: event.getToolTip().add(TextFormatting.GREEN + " Spring");
    private static Object SereneSeasons_ModFertility_setupTooltips_addSpring(Object text) {
        return TextFormatting.GREEN + I18n.format("desc.sereneseasons.spring");
    }

    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "setupTooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 3,
                    remap = false
            ),
            remap = false
    )
    // Fertile seasons texts were hardcoded (not localizable)
    // Line 130: event.getToolTip().add(TextFormatting.YELLOW + " Summer");
    private static Object SereneSeasons_ModFertility_setupTooltips_addSummer(Object text) {
        return TextFormatting.YELLOW + I18n.format("desc.sereneseasons.summer");
    }

    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "setupTooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 4,
                    remap = false
            ),
            remap = false
    )
    // Fertile seasons texts were hardcoded (not localizable)
    // Line 134: event.getToolTip().add(TextFormatting.GOLD + " Autumn");
    private static Object SereneSeasons_ModFertility_setupTooltips_addAutumn(Object text) {
        return TextFormatting.GOLD + I18n.format("desc.sereneseasons.autumn");
    }

    @SideOnly(Side.CLIENT)
    @ModifyArg(
            method = "setupTooltips(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 5,
                    remap = false
            ),
            remap = false
    )
    // Fertile seasons texts were hardcoded (not localizable)
    // Line 138: event.getToolTip().add(TextFormatting.AQUA + " Winter");
    private static Object SereneSeasons_ModFertility_setupTooltips_addWinter(Object text) {
        return TextFormatting.AQUA + I18n.format("desc.sereneseasons.winter");
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
        if (Loader.isModLoaded("rustic")) {
            Item item;
            int seasons;
            for (String seed : seeds) {
                item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(seed));
                if (item instanceof ItemStakeCropSeed) {
                    if (bitmask != 0) {
                        // Their plants are already added to allListedPlants, so no need to add those here
                        if (seedSeasons.containsKey(seed)) {
                            seasons = seedSeasons.get(seed);
                            seedSeasons.put(seed, seasons | bitmask);
                        } else {
                            seedSeasons.put(seed, bitmask);
                        }
                    }
                }
            }
        }        
    }

    @Shadow(remap = false)
    private static HashMap<String, Integer> seedSeasons = new HashMap<>();
}
