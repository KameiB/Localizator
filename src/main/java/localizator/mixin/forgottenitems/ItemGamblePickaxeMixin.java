package localizator.mixin.forgottenitems;

import localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import scala.Product;
import tschipp.forgottenitems.items.ItemGamblePickaxe;

import java.util.List;

@Mixin(ItemGamblePickaxe.class)
public abstract class ItemGamblePickaxeMixin {
    /**
     * @author KameiB
     * @reason Localize item description
     */
    @Overwrite(remap = Production.inProduction) // FALSE ONLY FOR DEBUGGING!
    @SideOnly(Side.CLIENT)
    // Line 39
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {        
        tooltip.add(I18n.format(((Item)((Object)this)).getUnlocalizedNameInefficiently(stack) + ".desc"));
    }
}
