package localizator.mixin.dynamictrees;

import com.ferreusveritas.dynamictrees.items.Staff;
import com.ferreusveritas.dynamictrees.trees.Species;
import localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Staff.class)
public abstract class StaffMixin {    
    /**
     * @author KameiB
     * @reason I just HAD to remove that weird "Â" symbol from the tooltip... 
     */
    @Overwrite(remap = Production.inProduction) // FALSE ONLY FOR DEBUGGING!
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
        Species species = this.getSpecies(stack);
        tooltip.add(I18n.format("tooltip.woodland_staff.tree") + " " + (species != null ? TextFormatting.GREEN + species.getLocalizedName() : I18n.format("tooltip.woodland_staff.tree_not_set")));        
        tooltip.add("JoCode: " + TextFormatting.GOLD + this.getCode(stack));
    }

    @Shadow(remap = false)
    public Species getSpecies(ItemStack itemStack) { return null; }
    
    @Shadow(remap = false)
    public String getCode(ItemStack itemStack) { return null; }
}
