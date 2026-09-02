package kameib.localizator.mixin.fishingmadebetter;

import joptsimple.internal.Strings;
import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.ItemBaitBucket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemBaitBucket.class)
public abstract class ItemBaitBucketMixin {
    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/common/item/ItemBaitBucket;getBaitDisplayName(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
                    remap = false
            ),
            remap = Production.inProduction
    )
    // Return the translated name of the bait instead of its "displayName".
    // Also, when using getBaitDisplayName, the bait name, when it's a fish, it's shown in white (TextFormatting.RESET)
    // Line 43: tooltip.add(TextFormatting.BLUE + I18n.format("item.fishingmadebetter.bait_bucket.tooltip.contains", new Object[0]) + ": " + TextFormatting.BOLD + getBaitCount(itemStack) + " " + getBaitDisplayName(itemStack) + TextFormatting.RESET);
    @SideOnly(Side.CLIENT)
    private String localizator_FMB_ItemBaitBucket_addInformation_getBaitDisplayName(ItemStack itemStack) {
        String baitLangKey = FMB_BetterFishUtil.getBaitLangKey(getBaitId(itemStack), getBaitMetadata(itemStack));
        return Strings.isNullOrEmpty(baitLangKey) ? getBaitDisplayName(itemStack) : I18n.format(baitLangKey);
    }
    
    @Shadow(remap = false)
    public static String getBaitDisplayName(ItemStack itemStack) { return null;}
    
    @Shadow(remap = false)
    public static int getBaitMetadata(ItemStack itemStack) {
        return 0;
    }

    @Shadow(remap = false)
    public static String getBaitId(ItemStack itemStack) {
        return null;
    }
}
