package kameib.localizator.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class LocNameArguments {
    public static ItemStack appendLocNameArgs(ItemStack stack, Iterable<String> tooltip) {
        NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        boolean needAppend = false;
        NBTTagCompound tag1;
        if (tag.hasKey("display", Constants.NBT.TAG_COMPOUND)) {
            tag1 = tag.getCompoundTag("display");
            if (tag.hasKey("LocNameArgs", Constants.NBT.TAG_LIST)) {
                needAppend = true;
            }
        } else {
            tag.setTag("display", new NBTTagCompound());
            tag1 = tag.getCompoundTag("display");
        }
        if (!needAppend) {
            tag1.setTag("LocNameArgs", new NBTTagList());
        }
        for (String s : tooltip) {
            tag1.getTagList("LocNameArgs", Constants.NBT.TAG_STRING).appendTag(new NBTTagString(s));
        }
        tag.setTag("display", tag1);
        ItemStack output = stack.copy();
        output.setTagCompound(tag);
        return output;
    }

    public static List<String> getLocNameArgs(ItemStack stack){
        List<String> argsList = new ArrayList<>();

        if(!stack.hasTagCompound()){
            return argsList;
        }

        NBTTagCompound mainTag = stack.getTagCompound();

        if(!mainTag.hasKey("display", Constants.NBT.TAG_COMPOUND)){
            return argsList;
        }

        NBTTagCompound displayTag = mainTag.getCompoundTag("display");

        if(!displayTag.hasKey("LocNameArgs", Constants.NBT.TAG_LIST)){
            return argsList;
        }

        NBTTagList locNameArgsTagList = displayTag.getTagList("LocNameArgs", Constants.NBT.TAG_STRING);

        for(int i = 0; i < locNameArgsTagList.tagCount(); i++){
            argsList.add(locNameArgsTagList.getStringTagAt(i));
        }

        return argsList;
    }
}
