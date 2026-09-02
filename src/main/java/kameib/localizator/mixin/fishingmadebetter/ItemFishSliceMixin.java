package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.fishslice.ItemFishSlice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemFishSlice.class)
public abstract class ItemFishSliceMixin {
    @ModifyVariable(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("STORE"),
            remap = Production.inProduction,
            name = "fishDisplayName")
    @SideOnly(Side.CLIENT)
    // Once we got the Fish Id (FishDisplayName), obtain its corresponding lang key
    // Line 33: String fishDisplayName = this.getFishDisplayName(stack);
    private String localizator_FMB_ItemFishSlice_addInformation_fishDisplayName_langKey(String fishDisplayName) {
        localizator$fishDisplayNameTranslatable = FMB_BetterFishUtil.fishIdToCustomLangKey(fishDisplayName);
        return localizator$fishDisplayNameTranslatable;
    }
    
    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            remap = Production.inProduction
    )
    // And now that we have the lang key, let's use our own!
    // Replace the hardcoded word order and hardcoded FishId, with a lang key that takes an argument
    // Line 35: tooltip.add(I18n.format("item.fishingmadebetter.fish_slice_raw.tooltip", new Object[0]) + " " + fishDisplayName);
    private Object localizator_FMB_ItemFishSlice_addInformation_tooltipAdd(Object original) {
        return I18n.format("tooltip.fishingmadebetter.fish_slice_raw", I18n.format(localizator$fishDisplayNameTranslatable));
    }
    
    @Unique
    private String localizator$fishDisplayNameTranslatable;
}
