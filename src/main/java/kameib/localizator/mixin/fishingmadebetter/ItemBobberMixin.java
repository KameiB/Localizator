package kameib.localizator.mixin.fishingmadebetter;

import kameib.localizator.data.Production;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.fishingmadebetter.common.item.attachment.bobber.ItemBobber;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemBobber.class)
public abstract class ItemBobberMixin {
    /**
     * @author KameiB
     * @reason Show each available liquid with colors.
     */
    @SideOnly(Side.CLIENT)
    @Overwrite(remap = Production.inProduction)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.fishingmadebetter.bobber.can_fish_in", 
                I18n.format("tooltip.fishingmadebetter.bobber." + (this.lavaBobber ? "obsidian" : this.voidBobber ? "void" : "water"))));
        if (this.varianceModifier != 0) {
            tooltip.add(I18n.format("item.fishingmadebetter.bobber_heavy.tooltip") + ": +" + this.varianceModifier);
        }

        if (this.tensioningModifier != 0) {
            tooltip.add(I18n.format("item.fishingmadebetter.bobber_lightweight.tooltip") + ": +" + this.tensioningModifier);
        }     
    }

    @Final
    @Shadow(remap = false)
    protected  boolean lavaBobber;
    @Final
    @Shadow(remap = false)
    protected boolean voidBobber;
    @Final
    @Shadow(remap = false)
    protected int varianceModifier;
    @Final
    @Shadow(remap = false)
    protected int tensioningModifier;
}
