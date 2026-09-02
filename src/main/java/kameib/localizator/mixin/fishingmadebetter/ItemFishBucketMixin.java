package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.ItemFishBucket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemFishBucket.class)
public abstract class ItemFishBucketMixin {
    @Redirect(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/theawesomegem/fishingmadebetter/common/item/ItemFishBucket;getFishId(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;",
                    remap = false
            ),
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Return the translated fish name instead of just the Fish Id
    // Line 139: String fishId = getFishId(itemStack);
    private String localizator_FMB_ItemFishBucket_addInformation_getFishId(ItemStack itemStack) {
        String fishId = getFishId(itemStack);
        if (fishId == null) {
            return null;
        }

        String fishLangKey = FMB_BetterFishUtil.fishIdToCustomLangKey(fishId);
        if (fishLangKey == null) {
            return I18n.format("item.fmb.missingno.name");
        }
        return I18n.format(fishLangKey);
    }
    
    @Shadow(remap = false)
    public static String getFishId(ItemStack itemStack) {
        return null;
    }
}
