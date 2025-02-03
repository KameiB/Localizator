package kameib.localizator.mixin.mca;

import mca.items.ItemStaffOfLife;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mixin(ItemStaffOfLife.class)
public abstract class ItemStaffOfLifeMixin extends Item {
    /**
     * @author KameiB
     * @reason Localize all tooltip texts
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.mca.staff_of_life.usesLeft", (itemStack.getMaxDamage() - itemStack.getItemDamage() + 1)));
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(I18n.format("item.mca.staff_of_life.desc.1"));
            tooltip.add(I18n.format("item.mca.staff_of_life.desc.2"));
            tooltip.add(I18n.format("item.mca.staff_of_life.desc.3"));
        } else {
            tooltip.add(I18n.format("item.mca.staff_of_life.shift"));
        }

    }
}
