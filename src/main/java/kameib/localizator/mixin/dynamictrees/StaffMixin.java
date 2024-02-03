package kameib.localizator.mixin.dynamictrees;

import com.ferreusveritas.dynamictrees.items.Staff;
import com.ferreusveritas.dynamictrees.trees.Species;
import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Staff.class)
public abstract class StaffMixin {    
    /**
     * @author KameiB
     * @reason I just HAD to remove that weird "Â" symbol from the tooltip... 
     */
    @Inject(
            method = "addInformation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = Production.inProduction
    )
    @SideOnly(Side.CLIENT)
    // Line 272
    private void DynamicTrees_Staff_addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn, CallbackInfo ci) {
        Species species = this.getSpecies(stack);
        tooltip.add(I18n.format("tooltip.woodland_staff.tree") + " " + (species != null ? TextFormatting.GREEN + species.getLocalizedName() : I18n.format("tooltip.woodland_staff.tree_not_set")));
        tooltip.add("JoCode: " + TextFormatting.GOLD + this.getCode(stack));
        ci.cancel();
    }
    
    @Shadow(remap = false)
    public Species getSpecies(ItemStack itemStack) { return null; }
    
    @Shadow(remap = false)
    public String getCode(ItemStack itemStack) { return null; }
}
