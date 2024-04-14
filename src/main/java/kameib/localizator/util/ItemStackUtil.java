package kameib.localizator.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class ItemStackUtil {
    public static boolean hasTranslatableName(ItemStack stack) {
        NBTTagCompound nbttagcompound = stack.getSubCompound("display");
        return nbttagcompound != null && nbttagcompound.hasKey("LocName", Constants.NBT.TAG_STRING);
    }
}
