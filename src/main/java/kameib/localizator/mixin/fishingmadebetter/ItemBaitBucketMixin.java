package kameib.localizator.mixin.fishingmadebetter;

import joptsimple.internal.Strings;
import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.ItemBaitBucket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemBaitBucket.class)
public abstract class ItemBaitBucketMixin {
    /**
     * @author KameiB
     * @reason The bait name, when it's a fish, it's shown in white.
     */
    @Overwrite(remap = Production.inProduction)
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.fishingmadebetter.bait_bucket.tooltip"));
        if (itemStack.isEmpty()) {
            return;
        }
        String baitLangKey = FMB_BetterFishUtil.getBaitLangKey(getBaitId(itemStack), getBaitMetadata(itemStack));
        if (Strings.isNullOrEmpty(baitLangKey)) {
            tooltip.add(TextFormatting.BLUE + I18n.format("item.fishingmadebetter.bait_bucket.tooltip.contains") + ": " + TextFormatting.BOLD + I18n.format("item.fishingmadebetter.bait_bucket.tooltip.none") + TextFormatting.RESET);
        } else {
            tooltip.add(TextFormatting.BLUE + I18n.format("item.fishingmadebetter.bait_bucket.tooltip.contains") + ": " + TextFormatting.BOLD + getBaitCount(itemStack) + " " + I18n.format(baitLangKey) + TextFormatting.RESET);
        }
    }
    
    @Shadow(remap = false)
    public static int getBaitCount(ItemStack itemStack) {
        return 0;
    }

    @Shadow(remap = false)
    public static int getBaitMetadata(ItemStack itemStack) {
        return 0;
    }

    @Shadow(remap = false)
    public static String getBaitId(ItemStack itemStack) {
        return null;
    }
}
