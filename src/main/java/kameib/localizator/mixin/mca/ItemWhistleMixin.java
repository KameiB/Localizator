package kameib.localizator.mixin.mca;

import mca.items.ItemWhistle;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mixin(ItemWhistle.class)
public abstract class ItemWhistleMixin extends Item {
    /**
     * @author KameiB
     * @reason Call I18n instead of hardcoding plain text
     */
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    @Overwrite
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.mca.whistle.desc"));
    }
}
