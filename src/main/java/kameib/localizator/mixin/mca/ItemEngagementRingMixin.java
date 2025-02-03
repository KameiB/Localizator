package kameib.localizator.mixin.mca;
import mca.items.ItemEngagementRing;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemEngagementRing.class)
public abstract class ItemEngagementRingMixin {
    /**
     * @author KameiB
     * @reason Call I18n instead of hardcoding plain text
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.mca.engagement_ring.desc"));
    }
}
