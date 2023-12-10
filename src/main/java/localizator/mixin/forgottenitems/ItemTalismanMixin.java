package localizator.mixin.forgottenitems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tschipp.forgottenitems.items.ItemTalisman;

import java.util.List;

@Mixin(ItemTalisman.class)
public abstract class ItemTalismanMixin {
    @Shadow(remap = false)
    private String lore;
    /**
     * @author KameiB
     * @reason Localize item description
     */
    @Overwrite
    @SideOnly(Side.CLIENT)
    // Line 44
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {        
        tooltip.add(I18n.format(this.lore));
    }
}
