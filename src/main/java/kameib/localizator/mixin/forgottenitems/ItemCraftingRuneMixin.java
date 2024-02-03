package kameib.localizator.mixin.forgottenitems;

import kameib.localizator.data.Production;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tschipp.forgottenitems.items.ItemCraftingRune;
import tschipp.forgottenitems.util.FIConfig;
import tschipp.forgottenitems.util.FIHelper;

import java.util.List;

@Mixin(ItemCraftingRune.class)
public abstract class ItemCraftingRuneMixin {
    /**
     * @author KameiB
     * @reason Localize item description
     */
    @Overwrite(remap = Production.inProduction)
    @SideOnly(Side.CLIENT)
    // Line 77
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(player != null && (FIConfig.showRecipeOutput || (FIConfig.showRecipeOutputCreative && player.isCreative())))
        {
            if(stack.hasTagCompound() && stack.getTagCompound().hasKey("id"))
            {
                if(stack.getMetadata() == 0)
                {
                    ItemStack outputStack = new ItemStack(FIHelper.getOutputItem(stack.getTagCompound().getInteger("id"))) ;                    

                    if(!outputStack.isEmpty()) {
                        tooltip.add(I18n.format("tooltip.forgottenitems.crafting_rune.output") + " " + I18n.format(outputStack.getTranslationKey() + ".name"));
                    }
					else {                        
                        tooltip.add(I18n.format("tooltip.forgottenitems.crafting_rune.output") + " " + I18n.format("tooltip.forgottenitems.crafting_rune.output_none"));
                    }
                }
                else
                {
                    ItemStack outputStack = new ItemStack(FIHelper.getOutputItemCustom(stack.getTagCompound().getInteger("id")));

                    if(!outputStack.isEmpty()) {
                        tooltip.add(I18n.format("tooltip.forgottenitems.crafting_rune.output") + " " + I18n.format(outputStack.getTranslationKey() + ".name"));
                    }
					else {
                        tooltip.add(I18n.format("tooltip.forgottenitems.crafting_rune.output") + " " + I18n.format("tooltip.forgottenitems.crafting_rune.output_none"));
                    }

                }
            }
        }
    }
}
