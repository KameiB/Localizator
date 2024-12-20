package kameib.localizator.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class ItemStackUtil {
    public static boolean hasTranslatableName(ItemStack stack) {
        NBTTagCompound nbttagcompound = stack.getSubCompound("display");
        return nbttagcompound != null && nbttagcompound.hasKey("LocName", Constants.NBT.TAG_STRING);
    }
    
    public static boolean isNovelty(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (!stack.hasTagCompound()) {
            return false;
        }

        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null && tag.hasKey("display")) {
            NBTTagCompound displayTag = tag.getCompoundTag("display");

            // Check if it has name and lore, in whatever form
            // This might help another modpack creator
            return ((displayTag.hasKey("Name") || displayTag.hasKey("LocName")) &&
                    (displayTag.hasKey("Lore", Constants.NBT.TAG_LIST) || displayTag.hasKey("LocLore", Constants.NBT.TAG_LIST) || displayTag.hasKey("rldLocLore", Constants.NBT.TAG_LIST)));
        }
        return false;
    }
}
