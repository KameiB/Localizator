package localizator.mixin.charm;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.*;
import svenhjol.charm.Charm;
import svenhjol.charm.brewing.item.ItemFlavoredCake;
import svenhjol.meson.iface.IMesonBlock;

@Mixin(ItemFlavoredCake.class)
public class ItemFlavoredCakeMixin extends ItemBlock {
    
    public ItemFlavoredCakeMixin(Block block) {
        super(block);
    }

    /**
     * @author KameiB
     * @reason Cake variation name were hardcoded texts
     */
    @Overwrite
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        String name = ((IMesonBlock)this.block).getName();
        if (name == null) {
            return super.getItemStackDisplayName(stack);
        } else {
            return I18n.format("tile." + Charm.MOD_ID + "." + name + ".name");
        }
    }
}
