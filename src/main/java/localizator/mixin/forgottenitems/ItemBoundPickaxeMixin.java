package localizator.mixin.forgottenitems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tschipp.forgottenitems.items.ItemBoundPickaxe;

import java.util.List;

@Mixin(ItemBoundPickaxe.class)
public abstract class ItemBoundPickaxeMixin {   
    /**
     * @author KameiB
     * @reason Localize bounding texts
     */
    @Overwrite
    @SideOnly(Side.CLIENT)
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
