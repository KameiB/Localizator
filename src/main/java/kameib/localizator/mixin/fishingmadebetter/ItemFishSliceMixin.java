package kameib.localizator.mixin.fishingmadebetter;

import joptsimple.internal.Strings;
import kameib.localizator.data.Production;
import kameib.localizator.util.FMB_BetterFishUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.fishslice.ItemFishSlice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemFishSlice.class)
public abstract class ItemFishSliceMixin {
    /**
     * @author KameiB
     * @reason Show the localized fish name
     */
    @SideOnly(Side.CLIENT)
    @Overwrite(remap = Production.inProduction)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        String fishDisplayName = FMB_BetterFishUtil.fishIdToCustomLangKey(getFishDisplayName(stack));

        if(!Strings.isNullOrEmpty(fishDisplayName)) {
            tooltip.add(I18n.format("tooltip.fishingmadebetter.fish_slice_raw", I18n.format(fishDisplayName)));
        }
    }
    
    @Shadow(remap = false)
    @Nullable
    private String getFishDisplayName(ItemStack itemStack) {
        if (!itemStack.hasTagCompound()) {
            return null;
        } else {
            return !itemStack.getTagCompound().hasKey("FishDisplayName") ? null : itemStack.getTagCompound().getString("FishDisplayName");
        }
    }
}
