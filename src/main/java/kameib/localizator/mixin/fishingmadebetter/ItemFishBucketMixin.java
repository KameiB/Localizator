package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.ItemFishBucket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemFishBucket.class)
public abstract class ItemFishBucketMixin {
    /**
     * @author KameiB
     * @reason Show the localized fish name instead of the hardcoded FishId
     */
    @SideOnly(Side.CLIENT)
    @Overwrite(remap = Production.inProduction)
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        String fishId = getFishId(itemStack);
        if (fishId == null) {
            return;
        }
        
        fishId = FMB_BetterFishUtil.fishIdToCustomLangKey(fishId);
        if (fishId == null) {
            fishId = "item.fmb.missingno.name";
        }        
        
        tooltip.add(TextFormatting.BLUE + I18n.format("item.fishingmadebetter.fish_bucket.tooltip") + ": " + TextFormatting.BOLD + I18n.format(fishId) + TextFormatting.RESET);
    }
    
    @Shadow(remap = false)
    public static String getFishId(ItemStack itemStack) {
        if (!itemStack.hasTagCompound()) {
            return null;
        } else {
            NBTTagCompound tagCompound = itemStack.getTagCompound();
            return tagCompound.hasKey("FishId") ? tagCompound.getString("FishId") : null;
        }
    }
}
