package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.fishingrod.ItemBetterFishingRod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ItemBetterFishingRod.class)
public abstract class ItemBetterFishingRodMixin {
    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/common/item/fishingrod/ItemBetterFishingRod;getBaitDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
                    remap = false
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Replace getBaitDisplayName and initialize baitDisplayName with the bait's lang key
    // Lines 230 and 240: String baitDisplayName = getBaitDisplayName(stack);
    private String localizator_FMB_ItemBetterFishingRod_addInformation_getBaitDisplayName_toLangKey(ItemStack stack) {
        return FMB_BetterFishUtil.getBaitLangKey(getBaitItem(stack), getBaitMetadata(stack));
    } 
    
    @ModifyVariable(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "LOAD",
                    ordinal = 2
            ),
            remap = Production.inProduction,
            name = "baitDisplayName")
    @SideOnly(Side.CLIENT)
    // After we replaced the baitDisplayName with a lang key, we just need to translate it
    // Line 231: tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.bait", new Object[0]) + ": " + TextFormatting.RESET + "" + TextFormatting.GRAY + (baitDisplayName != null && baitDisplayName.length() > 0 ? baitDisplayName : I18n.format("tooltip.fishingmadebetter.fishing_rod.bait_none", new Object[0])) + TextFormatting.RESET);
    private String localizator_FMB_ItemBetterFishingRod_addInformation_baitDisplayName_localize0(String baitDisplayName) {
        return I18n.format(baitDisplayName);
    }

    @ModifyVariable(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "LOAD",
                    ordinal = 5
            ),
            remap = Production.inProduction,
            name = "baitDisplayName")
    @SideOnly(Side.CLIENT)
    // After we replaced the baitDisplayName with a lang key, we just need to translate it
    // Line 241: tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("tooltip.fishingmadebetter.fishing_rod.bait", new Object[0]) + ": " + TextFormatting.RESET + "" + TextFormatting.GRAY + (baitDisplayName != null && baitDisplayName.length() > 0 ? baitDisplayName : I18n.format("tooltip.fishingmadebetter.fishing_rod.bait_none", new Object[0])) + TextFormatting.RESET);
    private String localizator_FMB_ItemBetterFishingRod_addInformation_baitDisplayName_localize1(String baitDisplayName) {
        return I18n.format(baitDisplayName);
    }
    
    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/theawesomegem/fishingmadebetter/common/item/attachment/hook/ItemHook;getTreasureModifier()I",
                            remap = false
                    )
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Add a "%" at the end so the Hook modifier description is consistent with the Hook item's description
    // Line 219: tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.treasure_chance", new Object[0]) + ": +" + hook.getTreasureModifier());
    private Object localizator_FMB_ItemBetterFishingRod_addInformation_getTreasureModifier_plusPercentage(Object text) {
        if(text instanceof String) {
            if(!((String) text).endsWith("%")) {
                return text + "%";
            }
        }
        return text;
    }

    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/theawesomegem/fishingmadebetter/common/item/attachment/hook/ItemHook;getBiteRateModifier()I",
                            remap = false
                    )
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Add a "%" at the end so the Hook modifier description is consistent with the Hook item's description
    // Line 223: tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.bite_rate", new Object[0]) + ": +" + hook.getBiteRateModifier());
    private Object localizator_FMB_ItemBetterFishingRod_addInformation_getBiteRateModifier_plusPercentage(Object text) {
        if(text instanceof String) {
            if(!((String) text).endsWith("%")) {
                return text + "%";
            }
        }
        return text;
    }

    @ModifyArg(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/theawesomegem/fishingmadebetter/common/item/attachment/hook/ItemHook;getWeightModifier()I",
                            remap = false
                    )
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Add a "%" at the end so the Hook modifier description is consistent with the Hook item's description
    // Line 227: tooltip.add("  " + I18n.format("tooltip.fishingmadebetter.fishing_rod.hook.weight", new Object[0]) + ": +" + hook.getWeightModifier());
    private Object localizator_FMB_ItemBetterFishingRod_addInformation_getWeightModifier_plusPercentage(Object text) {
        if(text instanceof String) {
            if(!((String) text).endsWith("%")) {
                return text + "%";
            }
        }
        return text;
    }
    
    @Shadow(remap = false)
    public static String getBaitItem(ItemStack itemStack) {
        return null;
    }
    @Shadow(remap = false)
    public static int getBaitMetadata(ItemStack itemStack) {
        return 0;
    }
}
