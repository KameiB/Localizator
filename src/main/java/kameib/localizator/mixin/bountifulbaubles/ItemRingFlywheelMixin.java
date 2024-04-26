package kameib.localizator.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemRingFlywheel;
import cursedflames.bountifulbaubles.item.base.AGenericItemBauble;
import kameib.localizator.data.Production;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemRingFlywheel.class)
public abstract class ItemRingFlywheelMixin extends AGenericItemBauble {
    public ItemRingFlywheelMixin(String name) {
        super(name);
    }

    /**
     * @author KameiB
     * @reason Replace the hardcoded "§4", etc. With TextFormatting colors,
     * so the colors get applied properly instead of showing "�4".
     */
    @SideOnly(Side.CLIENT)
    @Overwrite(remap = Production.inProduction)
    // Line 64: public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        IEnergyStorage e = (IEnergyStorage)stack.getCapability(ENERGY_STORAGE_CAPABILITY, (EnumFacing)null);
        if (e != null) {
            int energy = e.getEnergyStored();
            int max = e.getMaxEnergyStored();
            String color = energy == 0 ? "" + TextFormatting.DARK_RED : (energy < max / 4 ? "" + TextFormatting.RED : (energy < max / 2 ? "" + TextFormatting.YELLOW : "" + TextFormatting.GREEN));
            tooltip.add(color + String.valueOf(energy) + "/" + max + " RF");
        }
    }
    
    @Shadow(remap = false)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY;
}
