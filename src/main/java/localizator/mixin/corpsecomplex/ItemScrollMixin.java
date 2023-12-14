package localizator.mixin.corpsecomplex;

import c4.corpsecomplex.common.modules.spawning.ItemScroll;
import localizator.data.Production;
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

@Mixin(ItemScroll.class)
public abstract class ItemScrollMixin {
    /**
     * @author KameiB
     * @reason Grave scroll description was hardcoded
     */
    @Overwrite(remap = Production.inProduction) //FALSE ONLY FOR DEBUGGING!
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.corpsecomplex.scroll.desc"));
    }
}
