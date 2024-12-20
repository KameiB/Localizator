package kameib.localizator.util;

import kameib.localizator.handlers.ForgeConfigHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocLoreUtil {
    // If I don't receive locLoreArgList or is null, it means there's no need to add it
    public static ItemStack appendLocLore(ItemStack stack, List<String> locLoreList, @Nullable List<String> locLoreArgList) {
        if (locLoreArgList != null) {
            // locLoreArgList must be the same size as locLoreList
            for ( int i = locLoreArgList.size() ; i < locLoreList.size() ; i++) {
                locLoreArgList.add("");
            }
        }        
        
        NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        boolean needAppend1 = false;
        boolean needAppend2 = false;
        NBTTagCompound tag1;
        if (tag.hasKey("display", Constants.NBT.TAG_COMPOUND)) {
            tag1 = tag.getCompoundTag("display");
            if (tag.hasKey("LocLore", Constants.NBT.TAG_LIST)) {
                needAppend1 = true;
            }
            // If item has "LocLoreArg", but locLoreArgList is null, then I want to remove "LocLoreArg" 
            if (tag.hasKey("LocLoreArg", Constants.NBT.TAG_LIST) && locLoreArgList != null) {
                needAppend2 = true;
            }
        } else {
            tag.setTag("display", new NBTTagCompound());
            tag1 = tag.getCompoundTag("display");
        }

        // If item doesn't have "LocLore", create it and add the String list
        if (!needAppend1) {
            tag1.setTag("LocLore", new NBTTagList());
        }
        for (String s : locLoreList) {
            tag1.getTagList("LocLore", Constants.NBT.TAG_STRING).appendTag(new NBTTagString(s));
        }
        
        // If item doesn't have "LocLoreArg" but I received a valid locLoreArgList, then I want to add "LocLoreArg"
        if (!needAppend2 && locLoreArgList != null) {
            tag1.setTag("LocLoreArg", new NBTTagList());
        }
        if (tag1.hasKey("LocLoreArg", Constants.NBT.TAG_LIST) && locLoreArgList != null) {
            for (String s : locLoreArgList) {
                tag1.getTagList("LocLoreArg", Constants.NBT.TAG_STRING).appendTag(new NBTTagString(s));
            }
        }

        tag.setTag("display", tag1);
        ItemStack output = stack.copy();
        output.setTagCompound(tag);
        return output;
    }

    public static List<String> getLocLore(ItemStack stack) {
        List<String> locLoreList = new ArrayList<>();
        if (!stack.hasTagCompound()) {
            return locLoreList;
        }
        
        NBTTagCompound mainTag = stack.getTagCompound();
        if(!mainTag.hasKey("display", Constants.NBT.TAG_COMPOUND)){
            return locLoreList;
        }

        NBTTagCompound displayTag = mainTag.getCompoundTag("display");
        if(!displayTag.hasKey("LocLore", Constants.NBT.TAG_LIST)){
            return locLoreList;
        }

        NBTTagList locLoreTagList = displayTag.getTagList("LocLore", Constants.NBT.TAG_STRING);
        for(int i = 0; i < locLoreTagList.tagCount(); i++){
            locLoreList.add(locLoreTagList.getStringTagAt(i));
        }

        return locLoreList;
    }

    public static List<String> getLocLoreArg(ItemStack stack) {
        List<String> locLoreArgList = new ArrayList<>();
        if (!stack.hasTagCompound()) {
            return locLoreArgList;
        }

        NBTTagCompound mainTag = stack.getTagCompound();
        if(!mainTag.hasKey("display", Constants.NBT.TAG_COMPOUND)){
            return locLoreArgList;
        }

        NBTTagCompound displayTag = mainTag.getCompoundTag("display");
        if(!displayTag.hasKey("LocLoreArg", Constants.NBT.TAG_LIST)){
            return locLoreArgList;
        }

        NBTTagList locLoreArgTagList = displayTag.getTagList("LocLoreArg", Constants.NBT.TAG_STRING);
        for(int i = 0; i < locLoreArgTagList.tagCount(); i++){
            locLoreArgList.add(locLoreArgTagList.getStringTagAt(i));
        }

        return locLoreArgList;
    }

    public static TextFormatting getCustomColor() {
        switch (ForgeConfigHandler.clientConfig.minecraftDefaultLocLoreColor) {
            case "white": return TextFormatting.WHITE;
            case "black": return TextFormatting.BLACK;
            case "dark_blue": return TextFormatting.DARK_BLUE;
            case "dark_green": return TextFormatting.DARK_GREEN;
            case "dark_aqua": return TextFormatting.DARK_AQUA;
            case "dark_red": return TextFormatting.DARK_RED;
            case "dark_purple": return TextFormatting.DARK_PURPLE;
            case "gold": return TextFormatting.GOLD;
            case "gray": return TextFormatting.GRAY;
            case "dark_gray": return TextFormatting.DARK_GRAY;
            case "blue": return TextFormatting.BLUE;
            case "green": return TextFormatting.GREEN;
            case "aqua": return TextFormatting.AQUA;
            case "red": return TextFormatting.RED;
            case "light_purple": return TextFormatting.LIGHT_PURPLE;
            case "yellow": return TextFormatting.YELLOW;
        }
        return TextFormatting.WHITE;
    }

    public static String getCustomFormat() {
        switch (ForgeConfigHandler.clientConfig.minecraftDefaultLocLoreFormat) {
            case "none": return "";
            case "obfuscated": return TextFormatting.OBFUSCATED.toString();
            case "bold": return TextFormatting.BOLD.toString();
            case "strikethrough": return TextFormatting.STRIKETHROUGH.toString();
            case "underline": return TextFormatting.UNDERLINE.toString();
            case "italic": return TextFormatting.ITALIC.toString();
        }
        return "";
    }
    
    public static final List<String> validConfigColors = Arrays.asList(
            "white", "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold",
            "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow"
    );

    public static final List<String> validConfigFormats = Arrays.asList(
            "none", "obfuscated", "bold", "strikethrough", "underline", "italic"
    );
}
