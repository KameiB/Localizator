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
import tschipp.forgottenitems.items.ItemBoundAxe;

import java.util.List;

@Mixin(ItemBoundAxe.class)
public abstract class ItemBoundAxeMixin {   
    /**
     * @author KameiB
     * @reason Localize bounding texts
     */
    @Overwrite
    @SideOnly(Side.CLIENT)
    // Line 100
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("owner")) {
            tooltip.add(I18n.format("tooltip.forgottenitems.bound_tools.bound_to") + " " + stack.getTagCompound().getString("owner"));
        } else {
            tooltip.add(I18n.format("tooltip.forgottenitems.bound_tools.unbound"));
        }

        tooltip.add(I18n.format("tooltip.forgottenitems.bound_tools.desc"));
    }
}
