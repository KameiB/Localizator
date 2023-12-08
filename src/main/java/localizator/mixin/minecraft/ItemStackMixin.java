package localizator.mixin.minecraft;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
        implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound>{
    /**
     * @author KameiB
     * @reason Adds "LocLore" NBT tag functionality.
     * The existing "LocName" tag allows users to replace an item's name with a custom, translatable one.
     * But Vanilla doesn't have that support for the Lore. That's where my "LocLore" tag comes in handy!
     */
    @Inject( method = "getTooltip(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/util/ITooltipFlag;)Ljava/util/List;",
            at = @At("RETURN") 
    )
    // Line 890: return list;
    // I decided to do this at the very end instead of Overwritting, just to be sure it doesn't break with another mixin.
    // Would love to Overwrite, so when "LocLore" exists, then don't show "Lore" contents (Line 746), but well...
    public void lozalizator_vanillaItemStack_getTooltip(EntityPlayer playerIn, ITooltipFlag advanced, CallbackInfoReturnable<List<String>> cir) {
        List<String> list = cir.getReturnValue();
        ItemStack me = (ItemStack)((Object)this);

        // Checks if the item has the LocLore tag (inside "display") and adds the translated lang keys to the tooltip.
        if (me.hasTagCompound()){
            NBTTagCompound myTagCompound = me.getTagCompound();
            if (myTagCompound.hasKey("display", 10)){
                NBTTagCompound nbttagcompound1 = myTagCompound.getCompoundTag("display");
                if (nbttagcompound1.getTagId("LocLore") == 9){
                    NBTTagList nbttaglist4 = nbttagcompound1.getTagList("LocLore", 8);
                    if (!nbttaglist4.isEmpty()){
                        for (int l2 = 0; l2 < nbttaglist4.tagCount(); ++l2){
                            // Add the localized Lore at the beginning of the tooltip (just below the name), similar to addInformation
                            // I decided to format the Lore white in case the user didn't add formatting inside the lang file.
                            list.add(l2+1, TextFormatting.WHITE + I18n.format(nbttaglist4.getStringTagAt(l2)));
                        }
                    }
                }
            }
        }
    }
}